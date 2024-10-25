package Entidades;

import java.time.LocalDate;
import java.util.List;

public interface PacienteFunciones {
    public List<Turno> verTurnos(LocalDate desde, LocalDate hasta);
}
