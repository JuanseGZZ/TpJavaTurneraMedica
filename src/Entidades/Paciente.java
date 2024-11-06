package Entidades;

import DAO.DAOIPedirTurno;
import Entidades.Interfaces.PacienteFunciones;
import GUI.LoginPanel;
import Services.DAOBajarTurno;
import Services.DAOPaciente;
import Services.DAOPedirTurno;
import Services.DAOVerTurno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Paciente implements PacienteFunciones {
    private int tipo;
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private int dni;

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


    @Override
    public List<Turno> verTurnos(int dni,LocalDate desde, LocalDate hasta) {
        DAOVerTurno db = new DAOVerTurno();
        if (desde == null && hasta==null){
            return db.verTurno(dni,0);
        } else {
            return db.verTurno(dni,0,desde,hasta);                                                                                          // ojo que alguno puede ser null y no lo contemple en la db
        }
    }


    public static int getIdPaciente(int dni) {
        DAOPaciente p = new DAOPaciente();
        return p.getId(dni);
    }

    public static List<Paciente> getPacientes(){
        DAOPaciente pdb = new DAOPaciente();
        return pdb.getPacientes();
    }


}
