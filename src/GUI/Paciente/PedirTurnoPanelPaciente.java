package GUI.Paciente;

import GUI.PanelManager;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PedirTurnoPanelPaciente extends JPanel {

    private PanelManager panelManager;
    private JComboBox<String> especialidadCombo;
    private JComboBox<String> prestacionCombo;
    private JComboBox<String> medicoCombo;
    private JComboBox<String> lugarCombo;
    private JComboBox<String> mesCombo;
    private JComboBox<String> diaCombo;
    private JComboBox<String> horarioCombo;
    private JButton btnVolver;
    private JButton btnReservar;
    private JLabel titleLabel;
    private JPanel top;
    private JPanel cen;
    private JPanel bot;



    public PedirTurnoPanelPaciente(PanelManager panelManager) {
        this.panelManager = panelManager;


        this.setLayout(new BorderLayout());
        top = new JPanel(new FlowLayout());
        cen = new JPanel(new GridLayout(8, 2, 10, 10));
        bot = new JPanel(new FlowLayout());

        titleLabel = new JLabel("Panel Paciente", SwingConstants.CENTER);
        top.add(titleLabel);

        // Crear componentes
        especialidadCombo = new JComboBox<>();
        prestacionCombo = new JComboBox<>();
        medicoCombo = new JComboBox<>();
        lugarCombo = new JComboBox<>();
        mesCombo = new JComboBox<>();
        diaCombo = new JComboBox<>();
        horarioCombo = new JComboBox<>();
        btnVolver = new JButton("Volver Atras");
        btnReservar = new JButton("Reservar");

        // Llenar los JComboBoxes
        especialidadCombo.addItem("Seleccione Especialidad");
        prestacionCombo.addItem("Seleccione Prestacion");
        medicoCombo.addItem("Seleccione Medico");
        lugarCombo.addItem("Seleccione Lugar");
        mesCombo.addItem("Seleccione Mes");
        diaCombo.addItem("Seleccione Dia");
        horarioCombo.addItem("Seleccione Horario");

        // Agregar componentes al panel
        cen.add(new JLabel("Especialidad"));
        cen.add(especialidadCombo);
        cen.add(new JLabel("Prestacion"));
        cen.add(prestacionCombo);
        cen.add(new JLabel("Medico"));
        cen.add(medicoCombo);
        cen.add(new JLabel("Lugar"));
        cen.add(lugarCombo);
        cen.add(new JLabel("Mes"));
        cen.add(mesCombo);
        cen.add(new JLabel("Dia"));
        cen.add(diaCombo);
        cen.add(new JLabel("Horario"));
        cen.add(horarioCombo);
        bot.add(btnVolver);
        bot.add(btnReservar);

        this.add(top,BorderLayout.NORTH);
        this.add(cen,BorderLayout.CENTER);
        this.add(bot,BorderLayout.SOUTH);

        // Llenar especialidades desde la base de datos
       //cargarEspecialidades();

        // Evento para cargar prestaciones cuando se seleccione una especialidad
        especialidadCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String especialidadSeleccionada = (String) especialidadCombo.getSelectedItem();
                if (especialidadSeleccionada != null && !especialidadSeleccionada.equals("Seleccione Especialidad")) {
                    ///cargarPrestaciones((String) especialidadCombo.getSelectedItem());
                }
            }
        });

        // Evento para cargar médicos cuando se seleccione una prestación
        prestacionCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prestacionSeleccionada = (String) prestacionCombo.getSelectedItem();
                if (prestacionSeleccionada != null && !prestacionSeleccionada.equals("Seleccione Prestacion")) {
                    //cargarMedicos(prestacionSeleccionada);
                }
            }
        });

        medicoCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String medicoSeleccionado = (String) medicoCombo.getSelectedItem();
                if (medicoSeleccionado != null && !medicoSeleccionado.equals("Seleccione Medico")){
                    //cargarLugares(medicoSeleccionado);
                }
            }
        });

        lugarCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lugarSeleccionado = (String) lugarCombo.getSelectedItem();
                if(lugarSeleccionado != null && !lugarSeleccionado.equals("Seleccione Lugar")){
                    //cargarMeses(lugarSeleccionado);
                }
            }
        });

        mesCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mesSeleccionado = (String) mesCombo.getSelectedItem();
                String medicoSeleccionado = (String) medicoCombo.getSelectedItem();
                String lugarSeleccionado = (String) lugarCombo.getSelectedItem();
                if (
                        mesSeleccionado != null && !mesSeleccionado.equals("Seleccione Mes")
                        && medicoSeleccionado != null && !medicoSeleccionado.equals("Seleccione Medico")
                        && lugarSeleccionado != null && !lugarSeleccionado.equals("Seleccione Lugar")
                ){
                    //cargarDias(medicoSeleccionado,lugarSeleccionado,mesSeleccionado);
                }
            }
        });

        diaCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String diaSeleccionado = (String) diaCombo.getSelectedItem();
                String mesSeleccionado = (String) mesCombo.getSelectedItem();
                String medicoSeleccionado = (String) medicoCombo.getSelectedItem();
                String lugarSeleccionado = (String) lugarCombo.getSelectedItem();
                if(
                        diaSeleccionado!=null && !diaSeleccionado.equals("Seleccione Dia")
                        && mesSeleccionado != null && !mesSeleccionado.equals("Seleccione Mes")
                                && medicoSeleccionado != null && !medicoSeleccionado.equals("Seleccione Medico")
                                && lugarSeleccionado != null && !lugarSeleccionado.equals("Seleccione Lugar")
                ){
                    //cargarHorario(diaSeleccionado,mesSeleccionado,medicoSeleccionado,lugarSeleccionado);
                }
            }
        });

        horarioCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String horarioSeleccionado = (String) horarioCombo.getSelectedItem();
                if( horarioSeleccionado!=null && !horarioSeleccionado.equals("Seleccione Horario")){
                    //Horario.horario = horarioSeleccionado;
                }
            }
        });


        // Botón de "Volver Atras"
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.showPanel("pacientePanelMenu");
            }
        });

        // Botón de "Reservar" (Aquí puedes agregar lógica para guardar el turno)
        btnReservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para reservar el turno
                //JOptionPane.showMessageDialog(null, "Turno reservado con éxito para "+Usuario.getLoged().getNombre());
                //cargarSeleccion(Horario.horario);
            }
        });

    }//..............................




    // tengo que chequear de eliminar lo de abajo si cambio algo arriba
}
