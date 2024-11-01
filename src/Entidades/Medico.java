package Entidades;

import Entidades.Interfaces.MedicoFunciones;
import GUI.LoginPanel;
import Services.DAOMedico;
import Services.DAOPedirTurno;
import Services.DAOVerTurno;

import java.time.LocalDate;
import java.util.List;

public class Medico implements MedicoFunciones {
    private int tipo;
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private String prestacion;
    private int dni;
    //private List<Disponibilidad> disponibilidad; // seran estas funciones mas que atributos ? si
    //private List<Turno> turnos;
    //private double ganancias; // calcular por medio de turnos de db y dar resultado

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPrestacion() {
        return prestacion;
    }

    public void setPrestacion(String prestacion) {
        this.prestacion = prestacion;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public static List<Medico> getMedicos(){
        DAOPedirTurno daoPedirTurno = new DAOPedirTurno();

        for (Medico m : daoPedirTurno.getMedicos()){
            System.out.println(m);
        }
        return daoPedirTurno.getMedicos();
    }

    @Override
    public String toString() {
        return
                nombre + " " + apellido + " " + prestacion + " " + " DNI: " + dni ;
    }


    @Override
    public List<Turno> verTurnos(String desde, String hasta) {
        LocalDate fechaDesde = desde.isEmpty() ? null : LocalDate.parse(desde);
        LocalDate fechaHasta = hasta.isEmpty() ? null : LocalDate.parse(hasta);

        DAOVerTurno vt = new DAOVerTurno();

        if (fechaDesde==null && fechaHasta!=null) {
            fechaDesde = LocalDate.of(2000,01,01);
        } else if (fechaDesde!=null && fechaHasta==null) {
            fechaHasta = LocalDate.of(3000,01,01);
        } else if (fechaDesde==null && fechaHasta==null) {
            fechaDesde = LocalDate.of(2000,01,01);
            fechaHasta = LocalDate.of(3000,01,01);
        }

        List<Turno> turnos;
        turnos =  vt.verTurno(
                Integer.parseInt(LoginPanel.getLoged().get(3)),//dni medico
                Integer.parseInt(LoginPanel.getLoged().get(0)),//tipo de user
                fechaDesde,
                fechaHasta
        );

        return turnos;
    }

    @Override
    public List<Turno> verGanancias(String desde, String hasta) {
        //falta en db

        LocalDate fechaDesde = desde.isEmpty() ? null : LocalDate.parse(desde);
        LocalDate fechaHasta = hasta.isEmpty() ? null : LocalDate.parse(hasta);

        DAOMedico dm = new DAOMedico();

        if (fechaDesde==null && fechaHasta!=null) {
            fechaDesde = LocalDate.of(2000,01,01);
        } else if (fechaDesde!=null && fechaHasta==null) {
            fechaHasta = LocalDate.of(3000,01,01);
        } else if (fechaDesde==null && fechaHasta==null) {
            fechaDesde = LocalDate.of(2000,01,01);
            fechaHasta = LocalDate.of(3000,01,01);
        }

        List<Turno> turnos;
        turnos =  dm.verTurno(
                Integer.parseInt(LoginPanel.getLoged().get(3)),//dni medico
                Integer.parseInt(LoginPanel.getLoged().get(0)),//tipo de user
                fechaDesde,
                fechaHasta
        );

        return turnos;
    }

    public static int getId(int dni){
        DAOMedico md = new DAOMedico();
        return md.getId(dni);
    }

}
