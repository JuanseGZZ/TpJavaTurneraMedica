package GUI.Medico;

import GUI.PanelManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuMedico extends JPanel {
    private JButton btnVerGanancias, btnMisTurnos, btnLogout;
    private PanelManager panelManager;
    public MenuMedico(PanelManager panelManager){
        this.panelManager = panelManager;
        setLayout(new BorderLayout());

        // Panel superior con el título "Panel Paciente"
        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Panel Medico", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel);

        // Panel central con los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));

        btnVerGanancias = new JButton("Ver ganancias");
        btnMisTurnos = new JButton("Mis Turnos");


        btnLogout = new JButton("Logout");

        // Estilo uniforme para los botones
        btnVerGanancias.setFont(new Font("Arial", Font.BOLD, 14));
        btnMisTurnos.setFont(new Font("Arial", Font.BOLD, 14));

        btnLogout.setFont(new Font("Arial", Font.BOLD, 14));

        // Añadir botones al panel de botones
        buttonPanel.add(btnVerGanancias);
        buttonPanel.add(btnMisTurnos);


        // bottom to com back
        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(btnLogout);

        // Añadir los paneles al panel principal
        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        // Acción de los botones
        btnVerGanancias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.showPanel("verGanancias");
            }
        });

        btnMisTurnos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.showPanel("turnoPanelMedico");
            }
        });


        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                panelManager.showPanel("loginPanel");
            }
        });
    }
}
