package DAO;

import Entidades.Turno;

import java.time.LocalDate;
import java.util.List;

public interface DAOIVerTurno {
    public List<Turno> verTurno(int dni,int tipo);// dni (medico o usuario)
    public List<Turno> verTurno(int dni, LocalDate desde, LocalDate hasta,int tipo);
    public List<Turno> verTurno(int dniMedico,int dniPaciente,LocalDate desde, LocalDate hasta);
}
