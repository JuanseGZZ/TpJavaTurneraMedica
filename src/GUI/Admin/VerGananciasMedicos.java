package GUI.Admin;

import Entidades.Medico;
import Entidades.Turno;
import GUI.PanelManager;

import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;


public class VerGananciasMedicos extends JPanel {
    private List<Turno> turnos;
    private double ganancias;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public VerGananciasMedicos(PanelManager panelManager) {
        this.setLayout(new BorderLayout());

        // Top panel con botones de calendario
        JButton desdeButton = new JButton("Seleccionar fecha desde");
        JButton hastaButton = new JButton("Seleccionar fecha hasta");

        JButton buscar = new JButton("Buscar");
        buscar.setPreferredSize(new Dimension(100, 25));

        JPanel topButtons = new JPanel(new FlowLayout());
        topButtons.add(desdeButton);
        topButtons.add(hastaButton);
        topButtons.add(buscar);

        this.add(topButtons, BorderLayout.NORTH);

        // Tabla de turnos
        DefaultTableModel center = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        center.addColumn("Paciente");
        center.addColumn("Medico");
        center.addColumn("Hora");
        center.addColumn("Dia");
        center.addColumn("Hospital");
        center.addColumn("Consultorio");

        JTable tabla = new JTable(center);
        JScrollPane scrolleable = new JScrollPane(tabla);
        this.add(scrolleable, BorderLayout.CENTER);

        // Panel inferior
        JPanel bottom = new JPanel(new BorderLayout());
        JPanel bottomR = new JPanel(new FlowLayout());

        JButton volverButton = new JButton("Volver");

        bottom.add(volverButton, BorderLayout.WEST);
        bottom.add(bottomR, BorderLayout.CENTER);

        this.add(bottom, BorderLayout.SOUTH);

        // Acción del botón Volver
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.showPanel("menuAdmin");
            }
        });

        // Botón de buscar
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LocalDate fechaDesde = desdeButton.getText().contains(":") ? LocalDate.parse(desdeButton.getText().substring(desdeButton.getText().lastIndexOf(":") + 1).trim()) : LocalDate.of(1900, 1, 1);
                    LocalDate fechaHasta = hastaButton.getText().contains(":") ? LocalDate.parse(hastaButton.getText().substring(hastaButton.getText().lastIndexOf(":") + 1).trim()) : LocalDate.of(3000, 1, 1);

                    Medico medico = new Medico();
                    turnos = medico.verGananciasTodos(fechaDesde.toString(), fechaHasta.toString());

                    center.setRowCount(0);

                    Map<String, Integer> recaudacionPorMedico = new HashMap<>();
                    for (Turno turno : turnos) {
                        center.addRow(new Object[]{
                                turno.getPaciente().getNombre(),
                                turno.getMedico().getNombre(),
                                turno.getHora(),
                                turno.getFecha(),
                                turno.getLugar(),
                                turno.getConsultorio()
                        });

                        // Obtener el nombre completo del médico
                        String nombreCompletoMedico = turno.getMedico().getNombre() + " " + turno.getMedico().getApellido();

                        // Verificar si el médico ya está en el mapa
                        recaudacionPorMedico.put(nombreCompletoMedico, recaudacionPorMedico.getOrDefault(nombreCompletoMedico, 0) + 1);
                    }

                    // Suponiendo que cada consulta cuesta 2000
                    int precioConsulta = 2000;
                    StringBuilder resultado = new StringBuilder("Ganancias por médico:\n");

                    for (Map.Entry<String, Integer> entry : recaudacionPorMedico.entrySet()) {
                        String medicoNombre = entry.getKey();
                        int cantidadTurnos = entry.getValue();
                        int ganancia = cantidadTurnos * precioConsulta; // Calcular la ganancia total

                        resultado.append(medicoNombre)
                                .append(": $")
                                .append(ganancia)
                                .append(" (")
                                .append(cantidadTurnos)
                                .append(" turnos)\n");
                    }

                    // Mostrar el resultado en un JOptionPane
                    JOptionPane.showMessageDialog(null, resultado.toString());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Coloque bien las fechas \n\n err sy:" + ex.getMessage());
                }
            }
        });

        // Funcionalidad de los botones de calendario
        desdeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCalendar(desdeButton, "Desde");
            }
        });

        hastaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCalendar(hastaButton, "Hasta");
            }
        });
    }

    private void showCalendar(JButton targetButton, String label) {
        JCalendar calendar = new JCalendar();
        JDialog dialog = new JDialog((Frame) null, "Seleccionar fecha", true);
        JButton aceptar = new JButton("Aceptar");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(calendar, BorderLayout.CENTER);
        panel.add(aceptar, BorderLayout.SOUTH);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        aceptar.addActionListener(e -> {
            Date selectedDate = calendar.getDate();
            targetButton.setText(label + ": " + dateFormat.format(selectedDate));
            dialog.dispose();
        });

        dialog.setVisible(true);
    }
}
