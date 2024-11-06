package Entidades;

import java.time.LocalDate;
import java.util.List;

public class Administ{
    private int tipo; // sera
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private int dni;
    private String area;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}
