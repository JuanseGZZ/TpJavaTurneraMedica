package Entidades.Interfaces;

import Entidades.Medico;
import Entidades.Turno;

import java.time.LocalDate;
import java.util.List;

public interface AdminFunciones {
    public List<Turno> verTurnos(LocalDate desde, LocalDate hasta, String hospital); // mostrar grilla de medicos-ganancias
    public List<Medico> verGanancias(LocalDate desde, LocalDate hasta);              // mostrar grilla turnos lugar
}
