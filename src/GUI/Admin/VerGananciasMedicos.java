package GUI.Admin;

import Entidades.Medico;
import Entidades.Turno;
import GUI.PanelManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerGananciasMedicos extends JPanel{
    private List<Turno> turnos;
    private double ganancias;

    public VerGananciasMedicos(PanelManager panelManager){
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



        bottom.add(volverButton,BorderLayout.WEST);
        bottom.add(bottomR,BorderLayout.CENTER);

        this.add(bottom,BorderLayout.SOUTH);

        // Acción del botón Volver
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.showPanel("menuAdmin");
            }
        });

        // boton de buscar
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Medico medico = new Medico();
                    turnos = medico.verGananciasTodos(desde.getText(), hasta.getText());

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
                    JOptionPane.showMessageDialog(null,"Coloque bien las fechas \n\n err sy:"+ex.getMessage());
                }
            }
        });


    }
}
