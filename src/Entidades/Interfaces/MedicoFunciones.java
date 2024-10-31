package Entidades.Interfaces;

import Entidades.Turno;

import java.time.LocalDate;
import java.util.List;

public interface MedicoFunciones {
    public List<Turno> verTurnos(String desde, String hasta);
    public List<Turno> verGanancias(String desde,String hasta);
}
