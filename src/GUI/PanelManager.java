package GUI;



import GUI.Admin.*;
import GUI.Medico.MenuMedico;
import GUI.Medico.MisTurnosMedico;
import GUI.Medico.VerGanancias;
import GUI.Paciente.MenuPaciente;
import GUI.Paciente.MisTurnosPaciente;
import GUI.Paciente.PedirTurnoPanelPaciente;

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

        // Paneles de Paciente
        mainPanel.add(new MenuPaciente(this), "pacientePanelMenu");
        mainPanel.add(new PedirTurnoPanelPaciente(this), "turnoPanel");
        mainPanel.add(new MisTurnosPaciente(this),"MisTurnosPanel");

        // paneles de medico
        mainPanel.add(new MenuMedico(this),"medicoPanelMenu");
        mainPanel.add(new MisTurnosMedico(this),"turnoPanelMedico");
        mainPanel.add(new VerGanancias(this),"verGanancias");

        // paneles de admin
        mainPanel.add(new MenuAdmin(this),"menuAdmin");
        mainPanel.add(new PerdirTurnoAdmin(this),"pedirTurnoAdmin");
        mainPanel.add(new VerGananciasMedicos(this),"verGananciasMedicos");
        mainPanel.add(new PacientesLista(this),"PacientesLista");
        mainPanel.add(new AsignarHorarios(this),"AsignarDispo");



        // Mostrar el panel de login inicialmente
        cardLayout.show(mainPanel, "loginPanel");

    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

}
