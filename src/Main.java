import Entidades.Hospital;
import Entidades.Medico;
import Services.DAOPedirTurno;

import java.time.LocalDate;
import java.util.List;

public class Main {// Es el Front llamar a la Entidad y la entidad al DAOIMP o sea el Service
    public static void main(String[] args) {

        DAOPedirTurno dpt = new DAOPedirTurno();

        //mostrar prestaciones
        for(Medico m :dpt.getMedicos()){
            System.out.println(m.getPrestacion());
        }

        List<Hospital> hospitales = dpt.getHospitalesFecha(123456789).get(0);
        List<LocalDate> fechas = dpt.getHospitalesFecha(123456789).get(1);
        for(Hospital h : hospitales){
            System.out.println(h);
        }
        for (LocalDate f : fechas){
            System.out.println(f);
        }

        List<LocalDate> dias = dpt.getDias(123456789,hospitales.get(0).getDireccion(),fechas.get(0));
        System.out.println("Dias disponibles--");
        for (LocalDate dia : dias){
            System.out.println(dia);
        }

        List<List> turnos = dpt.getHorario(123456789,hospitales.get(0).getDireccion(),dias.get(0));
        System.out.println("imprimiendo horarios disponibles -- ");
        for(List turno : turnos){
            System.out.println("--");
            for(Object dato : turno){
                System.out.println(dato);
            }
        }
    }
}
