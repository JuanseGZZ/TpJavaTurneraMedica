package Entidades.Interfaces;

import Entidades.Turno;

import java.time.LocalDate;
import java.util.List;

public interface PacienteFunciones {
    public List<Turno> verTurnos(int dni, LocalDate desde, LocalDate hasta);
}
