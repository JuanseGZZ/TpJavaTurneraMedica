package Entidades;

import Services.DAOPedirTurno;

import java.time.LocalDate;
import java.util.*;

public class Hospital {
    private String nombre;
    private String direccion;
    private Administ administrador;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    public static Set<String> getHospitales(String medicoSeleccionado) {
        DAOPedirTurno pt = new DAOPedirTurno();
        List<Medico> medicos = pt.getMedicos();
        List<List> hospitalFecha;
        Set<String> hospitales = new HashSet<>();

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
        }

        return hospitales;
    }

    public static Set<String> getMeses(String lugar,String medico){
        DAOPedirTurno pt = new DAOPedirTurno();
        List<Medico> medicos = pt.getMedicos();
        int medicoDNI = 0;
        Set<String> meses = new HashSet<>();


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

        return meses;
    }

    public static Set<String> getDias(String medicoSeleccionado, String lugarSeleccionado, String mesSeleccionado){
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
        if (!dias.isEmpty()) {
            //System.out.println("entra");
            for (LocalDate dia : dias) {
                //System.out.println(dia.toString());
                diasSet.add(dia.toString());
            }
        }
        return diasSet;
    }

    public static List<List> getTurnos(String diaSeleccionado,String mesSeleccionado,String medicoSeleccionado,String lugarSeleccionado,int dniPaciente){
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
                                                turnosDisponibles = pt.getHorario(dni,hospital.getDireccion(), LocalDate.parse(diaSeleccionado),dniPaciente);
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
        return turnosDisponibles;
    }

}
