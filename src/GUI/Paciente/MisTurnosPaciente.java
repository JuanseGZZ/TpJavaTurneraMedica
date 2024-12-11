package GUI.Paciente;

import Entidades.Turno;
import GUI.LoginPanel;
import GUI.PanelManager;
import Services.DAOPaciente;
import Services.DAOVerTurno;

import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MisTurnosPaciente extends JPanel {
    private List<Turno> turnos;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public MisTurnosPaciente(PanelManager panelManager) {
        this.setLayout(new BorderLayout());

        // Top panel con filtros
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

        center.addColumn("ID-turno");
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
        volverButton.setPreferredSize(new Dimension(100, 25));
        JLabel cancelar = new JLabel("Cancelar turno    >|");
        JLabel idTn = new JLabel("Turno ID:");
        JTextField turno = new JTextField();
        turno.setPreferredSize(new Dimension(150, 25));
        JButton cancelarT = new JButton("Cancelar turno");

        bottomR.add(cancelar);
        bottomR.add(idTn);
        bottomR.add(turno);
        bottomR.add(cancelarT);

        bottom.add(volverButton, BorderLayout.WEST);
        bottom.add(bottomR, BorderLayout.CENTER);

        this.add(bottom, BorderLayout.SOUTH);

        // Acción del botón Volver
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.showPanel("pacientePanelMenu");
            }
        });

        // Botón de buscar
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LocalDate fechaDesde = desdeButton.getText().contains(":") ? LocalDate.parse(desdeButton.getText().substring(desdeButton.getText().lastIndexOf(":") + 1).trim()) : LocalDate.of(1900, 1, 1);
                    LocalDate fechaHasta = hastaButton.getText().contains(":") ? LocalDate.parse(hastaButton.getText().substring(hastaButton.getText().lastIndexOf(":") + 1).trim()) : LocalDate.of(3000, 1, 1);

                    DAOVerTurno vt = new DAOVerTurno();

                    turnos = vt.verTurno(
                            Integer.parseInt(LoginPanel.getLoged().get(3)), // dni usuario
                            Integer.parseInt(LoginPanel.getLoged().get(0)), // tipo de user
                            fechaDesde,
                            fechaHasta
                    );

                    center.setRowCount(0);

                    int iterador = 1;
                    for (Turno turno : turnos) {
                        center.addRow(new Object[]{
                                iterador++,
                                turno.getPaciente().getNombre(),
                                turno.getMedico().getNombre(),
                                turno.getHora(),
                                turno.getFecha(),
                                turno.getLugar(),
                                turno.getConsultorio()
                        });
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Coloque bien las fechas \n\n err sy:" + ex.getMessage());
                }
            }
        });

        // Botón de cancelar turno
        cancelarT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numero = turno.getText();
                if (turnos.size() < Integer.parseInt(numero) || Integer.parseInt(numero) < 1) {
                    JOptionPane.showMessageDialog(null, "Coloque turno válido");
                } else {
                    System.out.println("Turno a bajar: " + turnos.get((Integer.parseInt(numero)) - 1));
                    Turno.bajarTurno(turnos.get((Integer.parseInt(numero)) - 1));

                    turnos.remove((Integer.parseInt(numero) - 1));

                    center.setRowCount(0);

                    int iterador = 1;
                    for (Turno turno : turnos) {
                        center.addRow(new Object[]{
                                iterador++,
                                turno.getPaciente().getNombre(),
                                turno.getMedico().getNombre(),
                                turno.getHora(),
                                turno.getFecha(),
                                turno.getLugar(),
                                turno.getConsultorio()
                        });
                    }
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
