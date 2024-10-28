package GUI.Paciente;

import Entidades.Hospital;
import Entidades.Medico;
import GUI.PanelManager;
import Services.DAOPedirTurno;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class PedirTurnoPanelPaciente extends JPanel {

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



    public PedirTurnoPanelPaciente(PanelManager panelManager) {
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
        DAOPedirTurno pd = new DAOPedirTurno();
         cargarPrestaciones(pd.getMedicos());

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
                panelManager.showPanel("pacientePanelMenu");
            }
        });

        // Botón de "Reservar"
        btnReservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
        DAOPedirTurno pt = new DAOPedirTurno();
        List<Medico> medicos = pt.getMedicos();
        List<List> hospitalFecha;
        Set<String> hospitales = new HashSet<>();

        lugarCombo.removeAllItems();
        lugarCombo.addItem("Seleccione Lugar");
        mesCombo.removeAllItems();
        mesCombo.addItem("Seleccione Mes");
        diaCombo.removeAllItems();
        diaCombo.addItem("Seleccione Dia");
        horarioCombo.removeAllItems();
        horarioCombo.addItem("Seleccione Horario");

        if (!medicos.isEmpty()) {
            for (Medico medico : medicos) {
                if ((medico.getNombre() + " " + medico.getApellido()).equals(medicoSeleccionado)) {
                    //System.out.println(medico.getDni());
                    hospitalFecha = pt.getHospitalesFecha(medico.getDni());

                    // Verificamos si la lista de hospitalFecha no es nula y tiene elementos
                    if (hospitalFecha != null && !hospitalFecha.isEmpty()) {
                        // La primera lista debe ser de hospitales
                        List<Hospital> listaHospitales = (List<Hospital>) hospitalFecha.get(0);
                        if (listaHospitales != null && !listaHospitales.isEmpty()) {
                            for (Hospital hospital : listaHospitales) {
                                hospitales.add(hospital.getNombre());
                            }
                        }
                    } else {
                        System.out.println("hospitalFecha está vacía");
                    }
                }
            }

            // Llenamos el JComboBox con los hospitales
            if (!hospitales.isEmpty()) {
                for (String hospital : hospitales) {
                    lugarCombo.addItem(hospital);
                }
            }
        } else {
            System.out.println("medicos está vacía");
        }
    }

    private void cargarMeses(String lugar,String medico){
        DAOPedirTurno pt = new DAOPedirTurno();
        List<Medico> medicos = pt.getMedicos();
        int medicoDNI = 0;
        Set<String> meses = new HashSet<>();

        mesCombo.removeAllItems();
        mesCombo.addItem("Seleccione Mes");
        diaCombo.removeAllItems();
        diaCombo.addItem("Seleccione Dia");
        horarioCombo.removeAllItems();
        horarioCombo.addItem("Seleccione Horario");

        if(!medicos.isEmpty()){
            for(Medico m : medicos){
                if((m.getNombre() + " " + m.getApellido()).equals(medico)){
                    medicoDNI = m.getDni();
                }
            }
            if(medicoDNI != 0){
                List<Hospital> hospitales = (List<Hospital>) pt.getHospitalesFecha(medicoDNI).get(0);
                List<LocalDate> fechas = (List<LocalDate>) pt.getHospitalesFecha(medicoDNI).get(1);
                if(!hospitales.isEmpty() && !fechas.isEmpty()){
                    for (Hospital hospital : hospitales){
                        if (hospital.getNombre().equals(lugar)){
                            for (LocalDate fecha : fechas){
                                meses.add(String.valueOf(fecha));
                            }
                        }
                    }
                }
            }
        }

        if (!meses.isEmpty()){
            for (String mes : meses){
                mesCombo.addItem(mes);
            }
        }

    }

    private void cargarDias(String medicoSeleccionado, String lugarSeleccionado, String mesSeleccionado) {
        DAOPedirTurno pt = new DAOPedirTurno();
        List<Medico> medicos = pt.getMedicos();
        int dni = 0;
        List<LocalDate> dias = new ArrayList<>();
        Set<String> diasSet = new TreeSet<>();

        if (!medicos.isEmpty()) {
            for (Medico m : medicos) {
                if ((m.getNombre() + " " + m.getApellido()).equals(medicoSeleccionado)) {
                    dni = m.getDni();
                }
            }
            if (dni != 0) {
                List<Hospital> hospitales = (List<Hospital>) pt.getHospitalesFecha(dni).get(0);
                List<LocalDate> fechas = (List<LocalDate>) pt.getHospitalesFecha(dni).get(1);
                if (!hospitales.isEmpty() && !fechas.isEmpty()) {
                    for (Hospital hospital : hospitales) {
                        if (hospital.getNombre().equals(lugarSeleccionado)) {
                            for (LocalDate fecha : fechas) {
                                // Parseamos mesSeleccionado a LocalDate para compararlo con la fecha
                                LocalDate mesSeleccionadoDate = LocalDate.parse(mesSeleccionado);

                                if (fecha.equals(mesSeleccionadoDate)) {
                                    //System.out.println("entra");
                                    dias = pt.getDias(dni, hospital.getDireccion(), fecha);
                                } else {
                                    //System.out.println("Fecha no es igual");
                                }
                            }
                        } else {
                            System.out.println("Lugar no es igual");
                        }
                    }
                }
            }
        }

        diaCombo.removeAllItems();
        diaCombo.addItem("Seleccione Dia");
        horarioCombo.removeAllItems();
        horarioCombo.addItem("Seleccione Horario");

        if (!dias.isEmpty()) {
            //System.out.println("entra");
            for (LocalDate dia : dias) {
                //System.out.println(dia.toString());
                diasSet.add(dia.toString());
            }
            for (String diaa : diasSet) {
                //System.out.println(diaa);
                diaCombo.addItem(diaa);
            }
        }

    }

    private void cargarHorario(String diaSeleccionado,String mesSeleccionado,String medicoSeleccionado,String lugarSeleccionado){
        DAOPedirTurno pt = new DAOPedirTurno();
        List<Medico> medicos = pt.getMedicos();
        int dni = 0;
        List<LocalDate> dias = new ArrayList<>();
        List<List> turnosDisponibles = new ArrayList<>();

        if (!medicos.isEmpty()) {
            for (Medico m : medicos) {
                if ((m.getNombre() + " " + m.getApellido()).equals(medicoSeleccionado)) {
                    dni = m.getDni();
                }
            }
            if (dni != 0) {
                List<Hospital> hospitales = (List<Hospital>) pt.getHospitalesFecha(dni).get(0);
                List<LocalDate> fechas = (List<LocalDate>) pt.getHospitalesFecha(dni).get(1);
                if (!hospitales.isEmpty() && !fechas.isEmpty()) {
                    for (Hospital hospital : hospitales) {
                        if (hospital.getNombre().equals(lugarSeleccionado)) {
                            for (LocalDate fecha : fechas) {
                                // Parseamos mesSeleccionado a LocalDate para compararlo con la fecha
                                LocalDate mesSeleccionadoDate = LocalDate.parse(mesSeleccionado);

                                if (fecha.equals(mesSeleccionadoDate)) {
                                    //System.out.println("entra");
                                    dias = pt.getDias(dni, hospital.getDireccion(), fecha);
                                    if(!dias.isEmpty()){
                                        for (LocalDate dia : dias){
                                            if(dia.toString().equals(diaSeleccionado)){
                                                turnosDisponibles = pt.getHorario(dni,hospital.getDireccion(), LocalDate.parse(diaSeleccionado));
                                            }
                                        }
                                    }else {
                                        //el dia no es igual
                                    }
                                } else {
                                    //System.out.println("Fecha no es igual");
                                }
                            }
                        } else {
                            System.out.println("Lugar no es igual");
                        }
                    }
                }
            }
        }

        horarioCombo.removeAllItems();
        horarioCombo.addItem("Seleccione Horario");

        for (List<String> turno : turnosDisponibles){
            //System.out.println(turno);
            horarioCombo.addItem(turno.get(1));
        }

    }


    // tengo que chequear de eliminar lo de abajo si cambio algo arriba
}
