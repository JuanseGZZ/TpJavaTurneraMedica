package GUI;

import Services.DAOLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginPanel extends JPanel {
    private JTextField userField;
    private JPasswordField passwordField; // Usamos JPasswordField para ocultar la contraseña
    private PanelManager panelManager;
    private static List<String> loged;

    public LoginPanel(PanelManager panelManager) {
        this.panelManager = panelManager;
        setLayout(new GridBagLayout()); // Usamos GridBagLayout para mejor control

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Márgenes para darle espacio a los componentes

        // Panel principal con un borde
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Login", 0, 0, new Font("Arial", Font.BOLD, 16)));

        // Label y campo de texto para "User"
        JLabel userLabel = new JLabel("User:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END; // Alineamos las etiquetas a la derecha
        add(userLabel, gbc);

        userField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START; // Alineamos los campos a la izquierda
        add(userField, gbc);

        // Label y campo de texto para "Password"
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(15); // Usamos JPasswordField
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(passwordField, gbc);

        // Panel para los botones de "Login" y "Register" en la misma fila
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Centrar los botones
        add(buttonPanel, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DAOLogin login = new DAOLogin();
                List<String> rsp = new ArrayList<>();
                try {
                    rsp = login.login(userField.getText(), new String(passwordField.getPassword()));
                    if(rsp.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Error en usuario o contraseña");
                    }else {
                        if (rsp.get(0).equals("0")){
                            // poner el panel de paciente
                            panelManager.showPanel("pacientePanelMenu");
                            LoginPanel.setLoged(rsp);
                        } else if (rsp.get(0).equals("1")) {
                            // poner el panel de admin
                            LoginPanel.setLoged(rsp);
                        } else if (rsp.get(0).equals("2")) {
                            // poner el panel de medico
                            System.out.println("entro");
                            panelManager.showPanel("medicoPanelMenu");
                            LoginPanel.setLoged(rsp);
                        }else {
                            for (String data : rsp){
                                System.out.println(data);
                            }
                            JOptionPane.showMessageDialog(null, "Error en tipo de usuario");
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });


    }

    public static List<String> getLoged() {
        return loged;
    }

    public static void setLoged(List<String> loged) {
        LoginPanel.loged = loged;
    }
}
