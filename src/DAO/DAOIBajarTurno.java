package DAO;

import java.time.LocalDate;
import java.time.LocalTime;

public interface DAOIBajarTurno {
    public void darBaja(int medico, int paciente, String lugar, LocalDate fecha, LocalTime hora,int consultorio);
}
