package DAO;

import Entidades.Medico;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DAOIPedirTurno {
    public List<Medico> getMedicos();
    public List<List> getHospitalesFecha(int dni);
    public List<LocalDate> getDias(int dni, String direccion, LocalDate fecha);
    public List<List> getHorario(int dni, String direccion, LocalDate dia,int dniPaciente);
    public void setTurno(int dniMedico, int dniPaciente , String direccion, LocalDate fecha, LocalTime hora, int consultorio);
}
