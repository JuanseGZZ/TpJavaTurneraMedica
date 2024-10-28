package GUI.Paciente;
import Entidades.Turno;
import GUI.LoginPanel;
import GUI.PanelManager;
import Services.DAOPaciente;
import Services.DAOVerTurno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;


public class MisTurnosPaciente extends JPanel {
    private List<Turno> turnos;

    public MisTurnosPaciente(PanelManager panelManager) {
    this.setLayout(new BorderLayout());

    // top
    JLabel fec = new JLabel("YYYY-MM-DD    |");
    JLabel des = new JLabel("Desde:");
    JTextField desde = new JTextField();
    desde.setPreferredSize(new Dimension(150,25));
    JLabel hst = new JLabel("Hasta:");
    JTextField hasta = new JTextField();
    hasta.setPreferredSize(new Dimension(150,25));
    JButton buscar = new JButton("Buscar");
    buscar.setPreferredSize(new Dimension(100,25));

    JPanel topButtons = new JPanel(new FlowLayout());

    topButtons.add(fec);
    topButtons.add(des);
    topButtons.add(desde);
    topButtons.add(hst);
    topButtons.add(hasta);
    topButtons.add(buscar);

    this.add(topButtons,BorderLayout.NORTH);

    // Center

        DefaultTableModel center= new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que todas las celdas no sean editables
                return false;
                //return column == 1 || column == 2;
            }
        };

        center.addColumn("ID-turno"); // idturno
        center.addColumn("Paciente"); // nombre
        center.addColumn("Medico"); // medico nombre
        center.addColumn("Hora"); // desde
        center.addColumn("Dia"); // fecha
        center.addColumn("Hospital"); // nombre
        center.addColumn("Consultorio"); // numero

        JTable tabla = new JTable(center);
        JScrollPane scrolleable = new JScrollPane(tabla);
        this.add(scrolleable,BorderLayout.CENTER);

        // bottom

        JPanel bottom = new JPanel(new BorderLayout());
        JPanel bottomR = new JPanel(new FlowLayout());


        JButton volverButton = new JButton("Volver");


        volverButton.setPreferredSize(new Dimension(100,25));
        JLabel cancelar = new JLabel("Cancelar turno    >|");
        JLabel idTn = new JLabel("Turno ID:");
        JTextField turno = new JTextField();
        turno.setPreferredSize(new Dimension(150,25));
        JButton cancelarT = new JButton("Cancelar truno");


        bottomR.add(cancelar);
        bottomR.add(idTn);
        bottomR.add(turno);
        bottomR.add(cancelarT);

        bottom.add(volverButton,BorderLayout.WEST);
        bottom.add(bottomR,BorderLayout.CENTER);

        this.add(bottom,BorderLayout.SOUTH);

        // Acción del botón Volver
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.showPanel("pacientePanelMenu");
            }
        });

        // boton de buscar
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    LocalDate fechaDesde = desde.getText().isEmpty() ? null : LocalDate.parse(desde.getText());
                    LocalDate fechaHasta = hasta.getText().isEmpty() ? null : LocalDate.parse(hasta.getText());

                    DAOVerTurno vt = new DAOVerTurno();

                    if (fechaDesde==null && fechaHasta!=null) {
                        fechaDesde = LocalDate.of(2000,01,01);
                    } else if (fechaDesde!=null && fechaHasta==null) {
                        fechaHasta = LocalDate.of(3000,01,01);
                    } else if (fechaDesde==null && fechaHasta==null) {
                        fechaDesde = LocalDate.of(2000,01,01);
                        fechaHasta = LocalDate.of(3000,01,01);
                    }

                    turnos =  vt.verTurno(
                            Integer.parseInt(LoginPanel.getLoged().get(3)),//dni usuario
                            Integer.parseInt(LoginPanel.getLoged().get(0)),//tipo de user
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
                    JOptionPane.showMessageDialog(null,"Coloque bien las fechas \n\n err sy:"+ex.getMessage());
                }
            }
        });

        cancelarT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numero = turno.getText();
                if (turnos.size() < Integer.parseInt( numero) || Integer.parseInt( numero) < 1 ){
                    JOptionPane.showMessageDialog(null,"coloque turno valido");
                }else {
                    System.out.println("Turno a bajar: "+turnos.get( (Integer.parseInt(numero)) - 1) );
                    Turno.bajarTurno(turnos.get( (Integer.parseInt(numero)) - 1) );

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


    }

}

