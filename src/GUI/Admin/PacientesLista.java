package GUI.Admin;

import Entidades.Paciente;
import Entidades.Turno;
import GUI.LoginPanel;
import GUI.PanelManager;
import Services.DAOVerTurno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PacientesLista extends JPanel {

    public PacientesLista(PanelManager panelManager) {
        this.setLayout(new BorderLayout());

        // top
        JPanel topButtons = new JPanel(new FlowLayout());
        JButton volverButton = new JButton("Volver");

        volverButton.setPreferredSize(new Dimension(100,25));
        topButtons.add(volverButton);


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

        center.addColumn("Nombre"); //
        center.addColumn("Apellido"); //
        center.addColumn("DNI"); //

        JTable tabla = new JTable(center);
        JScrollPane scrolleable = new JScrollPane(tabla);
        this.add(scrolleable,BorderLayout.CENTER);

        center.setRowCount(0);

        List<Paciente> pacientes = Paciente.getPacientes();
        for (Paciente p : pacientes){
            center.addRow(new Object[]{
                    p.getNombre(),
                    p.getApellido(),
                    p.getDni()
            });
        }

        // Acción del botón Volver
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.showPanel("pedirTurnoAdmin");
            }
        });


    }
}
