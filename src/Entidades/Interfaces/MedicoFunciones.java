package Entidades.Interfaces;

import Entidades.Turno;

import java.time.LocalDate;
import java.util.List;

public interface MedicoFunciones {
    public List<Turno> verTurnos(LocalDate desde, LocalDate hasta);
    public double verGanancias(LocalDate desde,LocalDate hasta);
}
