package GUI.Admin;

import Entidades.Hospital;
import Entidades.Medico;
import Entidades.Turno;
import GUI.LoginPanel;
import GUI.PanelManager;
import Services.DAOPedirTurno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PerdirTurnoAdmin extends JPanel{
    private static List<List> turnos;
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



    public PerdirTurnoAdmin(PanelManager panelManager) {
        this.panelManager = panelManager;



        this.setLayout(new BorderLayout());
        top = new JPanel(new FlowLayout());
        cen = new JPanel(new GridLayout(8, 2, 10, 10));
        bot = new JPanel(new FlowLayout());

        titleLabel = new JLabel("Panel Paciente", SwingConstants.CENTER);
        top.add(titleLabel);

        // Crear componentes
        prestacionCombo = new JComboBox<>();
        medicoCombo = new JComboBox<>();
        lugarCombo = new JComboBox<>();
        mesCombo = new JComboBox<>();
        diaCombo = new JComboBox<>();
        horarioCombo = new JComboBox<>();
        btnVolver = new JButton("Volver Atras");
        btnReservar = new JButton("Reservar");

        // Llenar los JComboBoxes
        medicoCombo.addItem("Seleccione Medico");
        lugarCombo.addItem("Seleccione Lugar");
        mesCombo.addItem("Seleccione Mes");
        diaCombo.addItem("Seleccione Dia");
        horarioCombo.addItem("Seleccione Horario");

        // Agregar componentes al panel
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
        cargarPrestaciones(Medico.getMedicos());

        // Evento para cargar médicos cuando se seleccione una prestación
        prestacionCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prestacionSeleccionada = (String) prestacionCombo.getSelectedItem();
                if (prestacionSeleccionada != null && !prestacionSeleccionada.equals("Seleccione Prestacion")) {
                    cargarMedicos(prestacionSeleccionada);
                }
            }
        });

        medicoCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String medicoSeleccionado = (String) medicoCombo.getSelectedItem();
                if (medicoSeleccionado != null && medicoSeleccionado.compareTo("Seleccione Medico")!= 0){
                    cargarLugares(medicoSeleccionado);
                }
            }
        });

        lugarCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String medicoSeleccionado = (String) medicoCombo.getSelectedItem();
                String lugarSeleccionado = (String) lugarCombo.getSelectedItem();
                if(lugarSeleccionado != null && !lugarSeleccionado.equals("Seleccione Lugar")){
                    cargarMeses(lugarSeleccionado,medicoSeleccionado);
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
                    cargarDias(medicoSeleccionado,lugarSeleccionado,mesSeleccionado);
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
                    cargarHorario(diaSeleccionado,mesSeleccionado,medicoSeleccionado,lugarSeleccionado);
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
                panelManager.showPanel("menuAdmin");
            }
        });

        // Botón de "Reservar"
        btnReservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String diaSeleccionado = (String) diaCombo.getSelectedItem();
                String mesSeleccionado = (String) mesCombo.getSelectedItem();
                String medicoSeleccionado = (String) medicoCombo.getSelectedItem();
                String lugarSeleccionado = (String) lugarCombo.getSelectedItem();
                String horarioSeleccionado = (String) horarioCombo.getSelectedItem();
                if (diaSeleccionado!=null && !diaSeleccionado.equals("Seleccione Dia")
                        && mesSeleccionado != null && !mesSeleccionado.equals("Seleccione Mes")
                        && medicoSeleccionado != null && !medicoSeleccionado.equals("Seleccione Medico")
                        && lugarSeleccionado != null && !lugarSeleccionado.equals("Seleccione Lugar")
                        && horarioSeleccionado != null && !horarioSeleccionado.equals("Seleccione Horario")
                ){
                    for(List<String> turno : turnos){ // la unica diferencia con el admin es que le tengo que pasar un dni de usuario
                        if (turno.get(1).equals(horarioSeleccionado)){

                            System.out.println("Vamos a registrar este turno:");
                            System.out.println(turno);

                            //Turno.subirTurno(
                            //        Integer.parseInt(turno.get(3)),
                            //        Integer.parseInt( LoginPanel.getLoged().get(3) ), // dni de la persona logeada
                            //        turno.get(2),
                            //        LocalDate.parse(turno.get(0)),
                            //        LocalTime.parse(turno.get(1)),
                            //        Integer.parseInt(turno.get(4)));

                            PerdirTurnoAdmin.turnos = null;
                            medicoCombo.removeAllItems();
                            medicoCombo.addItem("Seleccione Medico");
                            lugarCombo.removeAllItems();
                            lugarCombo.addItem("Seleccione Lugar");
                            mesCombo.removeAllItems();
                            mesCombo.addItem("Seleccione Mes");
                            diaCombo.removeAllItems();
                            diaCombo.addItem("Seleccione Dia");
                            horarioCombo.removeAllItems();
                            horarioCombo.addItem("Seleccione Horario");

                        }
                    }
                }

            }
        });

    }//..............................

    // se carga una vez al inicio del panel
    private void cargarPrestaciones(List<Medico> medicos){
        Set<String> setPrestaciones = new HashSet<>();

        for(Medico medico : medicos){
            setPrestaciones.add(medico.getPrestacion());
        }
        for (String prestacion : setPrestaciones){
            prestacionCombo.addItem(prestacion);
        }
    }

    // se carga dinamicamente
    private void cargarMedicos(String prestacion){
        DAOPedirTurno pd = new DAOPedirTurno();
        List<Medico> medicos = pd.getMedicos();

        medicoCombo.removeAllItems();
        medicoCombo.addItem("Seleccione Medico");
        lugarCombo.removeAllItems();
        lugarCombo.addItem("Seleccione Lugar");
        mesCombo.removeAllItems();
        mesCombo.addItem("Seleccione Mes");
        diaCombo.removeAllItems();
        diaCombo.addItem("Seleccione Dia");
        horarioCombo.removeAllItems();
        horarioCombo.addItem("Seleccione Horario");

        for (Medico medico : medicos){
            if (medico.getPrestacion().equals(prestacion)){
                medicoCombo.addItem(medico.getNombre()+" "+medico.getApellido());
            }
        }
    }

    private void cargarLugares(String medicoSeleccionado) {

        Set<String> hospitales = Hospital.getHospitales(medicoSeleccionado);

        lugarCombo.removeAllItems();
        lugarCombo.addItem("Seleccione Lugar");
        mesCombo.removeAllItems();
        mesCombo.addItem("Seleccione Mes");
        diaCombo.removeAllItems();
        diaCombo.addItem("Seleccione Dia");
        horarioCombo.removeAllItems();
        horarioCombo.addItem("Seleccione Horario");


        // Llenamos el JComboBox con los hospitales
        if (!hospitales.isEmpty()) {
            for (String hospital : hospitales) {
                lugarCombo.addItem(hospital);
            }

        } else {
            System.out.println("medicos está vacía");
        }

    }

    private void cargarMeses(String lugar,String medico){

        Set<String> meses = Hospital.getMeses(lugar,medico);

        mesCombo.removeAllItems();
        mesCombo.addItem("Seleccione Mes");
        diaCombo.removeAllItems();
        diaCombo.addItem("Seleccione Dia");
        horarioCombo.removeAllItems();
        horarioCombo.addItem("Seleccione Horario");


        if (!meses.isEmpty()){
            for (String mes : meses){
                mesCombo.addItem(mes);
            }
        }

    }

    private void cargarDias(String medicoSeleccionado, String lugarSeleccionado, String mesSeleccionado) {
        Set<String> diasSet =Hospital.getDias(medicoSeleccionado,lugarSeleccionado,mesSeleccionado);

        diaCombo.removeAllItems();
        diaCombo.addItem("Seleccione Dia");
        horarioCombo.removeAllItems();
        horarioCombo.addItem("Seleccione Horario");

        if (!diasSet.isEmpty()){
            for (String diaa : diasSet) {
                //System.out.println(diaa);
                diaCombo.addItem(diaa);
            }
        }

    }

    private void cargarHorario(String diaSeleccionado,String mesSeleccionado,String medicoSeleccionado,String lugarSeleccionado){
        //List<List> turnosDisponibles = Hospital.getTurnos(diaSeleccionado,mesSeleccionado,medicoSeleccionado,lugarSeleccionado);

        //horarioCombo.removeAllItems();
        //horarioCombo.addItem("Seleccione Horario");

        //for (List<String> turno : turnosDisponibles){

            //System.out.println(turno);
            //horarioCombo.addItem(turno.get(1));
        //}

        //PerdirTurno.turnos = turnosDisponibles;

    }

}
