package GUI;



import GUI.Paciente.PacienteMenu;

import javax.swing.*;
import java.awt.*;

public class PanelManager {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;



    public PanelManager() {
        // carga default
        this.frame = new JFrame("Turnera medica");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setVisible(true);


        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        this.frame.add(mainPanel);

        JTextArea mostrador = new JTextArea();
        mainPanel.add(mostrador);

        // Paneles asosiados
        mainPanel.add(new LoginPanel(this), "loginPanel");

        // Paneles Paciente
        mainPanel.add(new PacienteMenu(this),"pacienteMenu");



        // Mostrar el panel de login inicialmente
        cardLayout.show(mainPanel, "loginPanel");

    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

}
