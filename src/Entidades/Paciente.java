package Entidades;

import java.util.List;

public class Paciente{
    private int tipo;
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private int dni;
    //private List<Turno> trunos; funcion

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

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " DNI: " + dni ;
    }
}
