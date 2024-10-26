import Entidades.Hospital;
import Entidades.Medico;
import Entidades.Turno;
import Services.DAOPedirTurno;
import Services.DAOVerTurno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {// Es el Front llamar a la Entidad y la entidad al DAOIMP o sea el Service
    public static void main(String[] args) {

        DAOVerTurno verturno =  new DAOVerTurno();
        List<Turno> turnosTom = verturno.verTurno(123456789,2);
        for (Turno t : turnosTom){
            System.out.println(t);
        }

    }
}
