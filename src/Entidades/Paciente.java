package Entidades;

import DAO.DAOIPedirTurno;
import Entidades.Interfaces.PacienteFunciones;
import Services.DAOPedirTurno;
import Services.DAOVerTurno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Paciente implements DAOIPedirTurno, PacienteFunciones {
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
    public List<Medico> getMedicos() {
        DAOPedirTurno db = new DAOPedirTurno();
        return db.getMedicos();
    }

    @Override
    public List<List> getHospitalesFecha(int dni) {
        DAOPedirTurno db = new DAOPedirTurno();
        return db.getHospitalesFecha(dni);
    }

    @Override
    public List<LocalDate> getDias(int dni, String direccion, LocalDate fecha) {
        DAOPedirTurno db = new DAOPedirTurno();
        return db.getDias(dni,direccion,fecha);
    }

    @Override
    public List<List> getHorario(int dni, String direccion, LocalDate dia) {
        DAOPedirTurno db = new DAOPedirTurno();
        return db.getHorario(dni,direccion,dia);
    }

    @Override
    public void setTurno(int dniMedico, int dniPaciente, String direccion, LocalDate fecha, LocalTime hora, int consultorio) {
        DAOPedirTurno db = new DAOPedirTurno();
        db.setTurno(dniMedico,dniPaciente,direccion,fecha,hora,consultorio);
    }

    @Override
    public List<Turno> verTurnos(int dni,LocalDate desde, LocalDate hasta) {
        DAOVerTurno db = new DAOVerTurno();
        if (desde == null && hasta==null){
            return db.verTurno(dni,0);
        } else {
            return db.verTurno(dni,0,desde,hasta);// ojo que alguno puede ser null y no lo contemple en la db
        }
    }
}
