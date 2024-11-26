package GUI.Admin;

import Entidades.Disponibilidad;
import GUI.PanelManager;
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AsignarHorarios extends JPanel {
    private JPanel dynamicPanel, topBar, actionPanel;
    private JButton btnMes, btnSemana, btnDia, btnCustom, btnVolver, btnConsolidar;

    private JComboBox<String> cbHospitalMes, cbConsultorioMes, cbMedicoMes;
    private JComboBox<String> cbHospitalSemana, cbConsultorioSemana, cbMedicoSemana;
    private JComboBox<String> cbHospitalDia, cbConsultorioDia, cbMedicoDia;
    private JComboBox<String> cbHospitalCustom, cbConsultorioCustom, cbMedicoCustom;

    private JSpinner spHoraDesdeMes, spHoraHastaMes;
    private JSpinner spHoraDesdeSemana, spHoraHastaSemana;
    private JSpinner spHoraDesdeDia, spHoraHastaDia;
    private JSpinner spHoraDesdeCustom, spHoraHastaCustom;

    private JButton btnSeleccionarDia, btnSeleccionarDesde, btnSeleccionarHasta;
    private JSpinner spAnioMes, spMesMes, spAnioSemana, spMesSemana;

    private LocalDate fechaSeleccionadaDia, fechaDesdeCustom, fechaHastaCustom;

    private static Map<String, String> hospitales = new HashMap<>();
    private static Map<String, String> medicos = new HashMap<>();
    private static Map<String, String> consultorios = new HashMap<>();

    private String activePanel = "Mes"; // Panel activo

    public AsignarHorarios(PanelManager panelManager) {
        setLayout(new BorderLayout());

        cargarHospitales();
        cargarMedicos();

        // Barra superior con botones
        topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnMes = createTopButton("Mes");
        btnSemana = createTopButton("Semana");
        btnDia = createTopButton("Día");
        btnCustom = createTopButton("Custom");
        topBar.add(btnMes);
        topBar.add(btnSemana);
        topBar.add(btnDia);
        topBar.add(btnCustom);
        add(topBar, BorderLayout.NORTH);

        // Panel dinámico
        dynamicPanel = new JPanel(new CardLayout());
        dynamicPanel.add(createMesPanel(), "Mes");
        dynamicPanel.add(createSemanaPanel(), "Semana");
        dynamicPanel.add(createDiaPanel(), "Día");
        dynamicPanel.add(createCustomPanel(), "Custom");
        add(dynamicPanel, BorderLayout.CENTER);

        // Panel inferior con botones
        actionPanel = new JPanel(new BorderLayout());
        btnVolver = new JButton("Volver");
        btnConsolidar = new JButton("Consolidar");
        actionPanel.add(btnVolver, BorderLayout.WEST);
        actionPanel.add(btnConsolidar, BorderLayout.EAST);
        add(actionPanel, BorderLayout.SOUTH);

        // Listeners para cambiar de panel
        btnMes.addActionListener(e -> switchPanel("Mes", btnMes));
        btnSemana.addActionListener(e -> switchPanel("Semana", btnSemana));
        btnDia.addActionListener(e -> switchPanel("Día", btnDia));
        btnCustom.addActionListener(e -> switchPanel("Custom", btnCustom));

        btnVolver.addActionListener(e -> panelManager.showPanel("menuAdmin"));
        btnConsolidar.addActionListener(e -> consolidarAction());
    }

    private void consolidarAction() {
        try {
            String hospital = null;
            String direccion = null;
            int consultorio = -1;
            int dniMedico = -1;
            LocalDate desde = null;
            LocalDate hasta = null;
            LocalTime horadesde = null;
            LocalTime horahasta = null;

            int resultado = 0;

            switch (activePanel) {
                case "Mes":
                    hospital = (String) cbHospitalMes.getSelectedItem();
                    direccion = hospitales.get(hospital);
                    consultorio = Integer.parseInt((String) cbConsultorioMes.getSelectedItem());
                    dniMedico = Integer.parseInt(medicos.get(cbMedicoMes.getSelectedItem()));
                    int anioMes = (int) spAnioMes.getValue();
                    int mesMes = (int) spMesMes.getValue();
                    desde = LocalDate.of(anioMes, mesMes, 1);
                    hasta = desde.withDayOfMonth(desde.lengthOfMonth());
                    horadesde = obtenerHoraDesdeSpinner(spHoraDesdeMes);
                    horahasta = obtenerHoraDesdeSpinner(spHoraHastaMes);
                    resultado = Disponibilidad.consolidar(dniMedico, desde, hasta, direccion, consultorio, horadesde, horahasta);
                    break;
                case "Semana":
                    hospital = (String) cbHospitalSemana.getSelectedItem();
                    direccion = hospitales.get(hospital);
                    consultorio = Integer.parseInt((String) cbConsultorioSemana.getSelectedItem());
                    dniMedico = Integer.parseInt(medicos.get(cbMedicoSemana.getSelectedItem()));
                    int anioSemana = (int) spAnioSemana.getValue();
                    int mesSemana = (int) spMesSemana.getValue();
                    desde = LocalDate.of(anioSemana, mesSemana, 1);
                    hasta = desde.plusDays(6);
                    horadesde = obtenerHoraDesdeSpinner(spHoraDesdeSemana);
                    horahasta = obtenerHoraDesdeSpinner(spHoraHastaSemana);
                    resultado = Disponibilidad.consolidar(dniMedico, desde, hasta, direccion, consultorio, horadesde, horahasta);
                    break;
                case "Día":
                    hospital = (String) cbHospitalDia.getSelectedItem();
                    direccion = hospitales.get(hospital);
                    consultorio = Integer.parseInt((String) cbConsultorioDia.getSelectedItem());
                    dniMedico = Integer.parseInt(medicos.get(cbMedicoDia.getSelectedItem()));
                    desde = fechaSeleccionadaDia != null ? fechaSeleccionadaDia : LocalDate.now();
                    hasta = desde;
                    horadesde = obtenerHoraDesdeSpinner(spHoraDesdeDia);
                    horahasta = obtenerHoraDesdeSpinner(spHoraHastaDia);
                    resultado = Disponibilidad.consolidar(dniMedico, desde, hasta, direccion, consultorio, horadesde, horahasta);
                    break;
                case "Custom":
                    hospital = (String) cbHospitalCustom.getSelectedItem();
                    direccion = hospitales.get(hospital);
                    consultorio = Integer.parseInt((String) cbConsultorioCustom.getSelectedItem());
                    dniMedico = Integer.parseInt(medicos.get(cbMedicoCustom.getSelectedItem()));
                    desde = fechaDesdeCustom != null ? fechaDesdeCustom : LocalDate.now();
                    hasta = fechaHastaCustom != null ? fechaHastaCustom : LocalDate.now();
                    horadesde = obtenerHoraDesdeSpinner(spHoraDesdeCustom);
                    horahasta = obtenerHoraDesdeSpinner(spHoraHastaCustom);
                    resultado = Disponibilidad.consolidar(dniMedico, desde, hasta, direccion, consultorio, horadesde, horahasta);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Panel no identificado.");
                    return;
            }



            if (resultado == 0) {
                JOptionPane.showMessageDialog(this, "Intervalo consolidado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error: Solapamiento o datos incorrectos.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al consolidar: " + ex.getMessage());
        }
    }

    private JPanel createMesPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        cbHospitalMes = new JComboBox<>(hospitales.keySet().toArray(new String[0]));
        cbConsultorioMes = new JComboBox<>();
        cbMedicoMes = new JComboBox<>(medicos.keySet().toArray(new String[0]));

        spAnioMes = new JSpinner(new SpinnerNumberModel(2024, 2000, 2100, 1));
        spMesMes = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));

        cbHospitalMes.addActionListener(e -> actualizarConsultorios(cbHospitalMes, cbConsultorioMes));

        spHoraDesdeMes = new JSpinner(new SpinnerDateModel());
        spHoraHastaMes = new JSpinner(new SpinnerDateModel());
        configurarHoraSpinner(spHoraDesdeMes);
        configurarHoraSpinner(spHoraHastaMes);

        panel.add(new JLabel("Año:"));
        panel.add(spAnioMes);
        panel.add(new JLabel("Mes:"));
        panel.add(spMesMes);
        panel.add(new JLabel("Hospital:"));
        panel.add(cbHospitalMes);
        panel.add(new JLabel("Consultorio:"));
        panel.add(cbConsultorioMes);
        panel.add(new JLabel("Médico:"));
        panel.add(cbMedicoMes);
        panel.add(new JLabel("Hora Desde:"));
        panel.add(spHoraDesdeMes);
        panel.add(new JLabel("Hora Hasta:"));
        panel.add(spHoraHastaMes);

        return wrapPanel(panel);
    }

    private JPanel createSemanaPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        cbHospitalSemana = new JComboBox<>(hospitales.keySet().toArray(new String[0]));
        cbConsultorioSemana = new JComboBox<>();
        cbMedicoSemana = new JComboBox<>(medicos.keySet().toArray(new String[0]));

        spAnioSemana = new JSpinner(new SpinnerNumberModel(2024, 2000, 2100, 1));
        spMesSemana = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));

        cbHospitalSemana.addActionListener(e -> actualizarConsultorios(cbHospitalSemana, cbConsultorioSemana));

        spHoraDesdeSemana = new JSpinner(new SpinnerDateModel());
        spHoraHastaSemana = new JSpinner(new SpinnerDateModel());
        configurarHoraSpinner(spHoraDesdeSemana);
        configurarHoraSpinner(spHoraHastaSemana);

        panel.add(new JLabel("Año:"));
        panel.add(spAnioSemana);
        panel.add(new JLabel("Mes:"));
        panel.add(spMesSemana);
        panel.add(new JLabel("Hospital:"));
        panel.add(cbHospitalSemana);
        panel.add(new JLabel("Consultorio:"));
        panel.add(cbConsultorioSemana);
        panel.add(new JLabel("Médico:"));
        panel.add(cbMedicoSemana);
        panel.add(new JLabel("Hora Desde:"));
        panel.add(spHoraDesdeSemana);
        panel.add(new JLabel("Hora Hasta:"));
        panel.add(spHoraHastaSemana);

        return wrapPanel(panel);
    }

    private JPanel createDiaPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        cbHospitalDia = new JComboBox<>(hospitales.keySet().toArray(new String[0]));
        cbConsultorioDia = new JComboBox<>();
        cbMedicoDia = new JComboBox<>(medicos.keySet().toArray(new String[0]));

        cbHospitalDia.addActionListener(e -> actualizarConsultorios(cbHospitalDia, cbConsultorioDia));

        btnSeleccionarDia = new JButton("Seleccionar Día");
        btnSeleccionarDia.addActionListener(e -> mostrarCalendario("Seleccione un día", fecha -> fechaSeleccionadaDia = fecha));

        spHoraDesdeDia = new JSpinner(new SpinnerDateModel());
        spHoraHastaDia = new JSpinner(new SpinnerDateModel());
        configurarHoraSpinner(spHoraDesdeDia);
        configurarHoraSpinner(spHoraHastaDia);

        panel.add(new JLabel("Hospital:"));
        panel.add(cbHospitalDia);
        panel.add(new JLabel("Consultorio:"));
        panel.add(cbConsultorioDia);
        panel.add(new JLabel("Médico:"));
        panel.add(cbMedicoDia);
        panel.add(new JLabel("Día:"));
        panel.add(btnSeleccionarDia);
        panel.add(new JLabel("Hora Desde:"));
        panel.add(spHoraDesdeDia);
        panel.add(new JLabel("Hora Hasta:"));
        panel.add(spHoraHastaDia);

        return wrapPanel(panel);
    }

    private JPanel createCustomPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        cbHospitalCustom = new JComboBox<>(hospitales.keySet().toArray(new String[0]));
        cbConsultorioCustom = new JComboBox<>();
        cbMedicoCustom = new JComboBox<>(medicos.keySet().toArray(new String[0]));

        cbHospitalCustom.addActionListener(e -> actualizarConsultorios(cbHospitalCustom, cbConsultorioCustom));

        btnSeleccionarDesde = new JButton("Seleccionar Desde");
        btnSeleccionarDesde.addActionListener(e -> mostrarCalendario("Seleccione una fecha inicial", fecha -> fechaDesdeCustom = fecha));

        btnSeleccionarHasta = new JButton("Seleccionar Hasta");
        btnSeleccionarHasta.addActionListener(e -> mostrarCalendario("Seleccione una fecha final", fecha -> fechaHastaCustom = fecha));

        spHoraDesdeCustom = new JSpinner(new SpinnerDateModel());
        spHoraHastaCustom = new JSpinner(new SpinnerDateModel());
        configurarHoraSpinner(spHoraDesdeCustom);
        configurarHoraSpinner(spHoraHastaCustom);

        panel.add(new JLabel("Hospital:"));
        panel.add(cbHospitalCustom);
        panel.add(new JLabel("Consultorio:"));
        panel.add(cbConsultorioCustom);
        panel.add(new JLabel("Médico:"));
        panel.add(cbMedicoCustom);
        panel.add(new JLabel("Desde:"));
        panel.add(btnSeleccionarDesde);
        panel.add(new JLabel("Hasta:"));
        panel.add(btnSeleccionarHasta);
        panel.add(new JLabel("Hora Desde:"));
        panel.add(spHoraDesdeCustom);
        panel.add(new JLabel("Hora Hasta:"));
        panel.add(spHoraHastaCustom);

        return wrapPanel(panel);
    }

    private void mostrarCalendario(String titulo, java.util.function.Consumer<LocalDate> callback) {
        JDialog dialog = new JDialog();
        dialog.setTitle(titulo);
        dialog.setModal(true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JCalendar calendar = new JCalendar();
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(e -> {
            Date date = calendar.getDate();
            callback.accept(convertirFecha(date));
            dialog.dispose();
        });

        dialog.setLayout(new BorderLayout());
        dialog.add(calendar, BorderLayout.CENTER);
        dialog.add(btnAceptar, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private LocalDate convertirFecha(Date date) {
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    private void configurarHoraSpinner(JSpinner spinner) {
        spinner.setEditor(new JSpinner.DateEditor(spinner, "HH:mm"));
    }

    private LocalTime obtenerHoraDesdeSpinner(JSpinner spinner) {
        Date date = (Date) spinner.getValue();
        return LocalTime.of(date.getHours(), date.getMinutes());
    }

    private void actualizarConsultorios(JComboBox<String> cbHospital, JComboBox<String> cbConsultorio) {
        String hospitalSeleccionado = (String) cbHospital.getSelectedItem();
        if (hospitalSeleccionado != null) {
            String direccion = hospitales.get(hospitalSeleccionado);
            consultorios = Disponibilidad.getConsultorios(direccion);
            cbConsultorio.removeAllItems();
            consultorios.keySet().forEach(cbConsultorio::addItem);
        }
    }

    private JPanel wrapPanel(JPanel panel) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(panel);
        return wrapper;
    }

    private JButton createTopButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(null);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        return button;
    }

    private void switchPanel(String panelName, JButton activeButton) {
        CardLayout cl = (CardLayout) dynamicPanel.getLayout();
        cl.show(dynamicPanel, panelName);
        activePanel = panelName;
        resetTopButtons();
        activeButton.setBackground(Color.CYAN);
    }

    private void resetTopButtons() {
        btnMes.setBackground(null);
        btnSemana.setBackground(null);
        btnDia.setBackground(null);
        btnCustom.setBackground(null);
    }

    private void cargarHospitales() {
        hospitales = Disponibilidad.getHospitales();
    }

    private void cargarMedicos() {
        medicos = Disponibilidad.getMedicos();
    }
}
