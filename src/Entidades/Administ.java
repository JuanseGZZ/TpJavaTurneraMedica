package Entidades;

import DAO.DAOIPedirTurno;
import Entidades.Interfaces.AdminFunciones;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Administ implements DAOIPedirTurno, AdminFunciones {
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




    @Override
    public List<Medico> getMedicos() {
        return List.of();
    }

    @Override
    public List<List> getHospitalesFecha(int dni) {
        return List.of();
    }

    @Override
    public List<LocalDate> getDias(int dni, String direccion, LocalDate fecha) {
        return List.of();
    }

    @Override
    public List<List> getHorario(int dni, String direccion, LocalDate dia) {
        return List.of();
    }

    @Override
    public void setTurno(int dniMedico, int dniPaciente, String direccion, LocalDate fecha, LocalTime hora, int consultorio) {

    }

    @Override
    public List<Turno> verTurnos(LocalDate desde, LocalDate hasta, String hospital) {
        return List.of();
    }

    @Override
    public List<Medico> verGanancias(LocalDate desde, LocalDate hasta) {
        return List.of();
    }
}
