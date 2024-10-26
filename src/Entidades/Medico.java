package Entidades;

import Services.DAOPedirTurno;

import java.util.List;

public class Medico{
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


    public List<Medico> getMedicos(){
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


}
