package Services;

import DAO.DAOIPedirTurno;
import Entidades.Hospital;
import Entidades.Medico;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DAOPedirTurno extends DAOconecction implements DAOIPedirTurno {

    public List<Medico> getMedicos(){
        try {
            this.ps = connection.prepareStatement("SELECT * FROM MEDICO");
            ResultSet rsp = ps.executeQuery();
            List<Medico> medicos = new ArrayList<>();
            while(rsp.next()){
                Medico m = new Medico();
                m.setNombre(rsp.getString("NOMBRE"));
                m.setDni(rsp.getInt("dni"));
                m.setApellido(rsp.getString("apellido"));
                m.setTipo(rsp.getInt("tipo"));
                m.setPrestacion(rsp.getString("prestacion"));
                m.setUsername(rsp.getString("username"));
                m.setPassword(rsp.getString("password"));
                medicos.add(m);
            }
            return medicos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<List> getHospitalesFecha(int dni){
        // querys a utilizar
        String getIdMedico = "SELECT * FROM MEDICO m where m.dni = ?";

        String getDisponibilidad =  "SELECT \n" +
                                    "    d.*, \n" +
                                    "    h.*, \n" +
                                    "    TIMESTAMPDIFF(MINUTE, d.horaDesde, d.horaHasta) / 30 AS cantidad_turnos\n" +
                                    "FROM DISPONIBILIDAD d\n" +
                                    "JOIN HOSPITAL h ON (h.ID = d.HOSPITAL)\n" +
                                    "WHERE d.hasta > CURRENT_DATE\n" +
                                    "AND d.MEDICO = ?;";

        String turnosSerch =    "SELECT COUNT(*) AS turnos_ocupados " +
                                "FROM turnos t " +
                                "WHERE t.fecha = ? AND t.id = ?" +
                                        "and t.estado > 0";
        // variables a utilizar

        int turnosIterar=0; // turnos posibles (para comparar con los tomados en el dia visto para ver si hay alguno disponible)
        int diasIterar=0;   // dias que vamos a iterar en cada intervalo
        LocalDate desde;    // fecha desde que se inicia la iteracion
        List<Hospital> hospitales = new ArrayList<>();
        List<LocalDate> fechas = new ArrayList<>();

        try{
            // recolectamos el id del medico con el dni mandado
            this.ps = connection.prepareStatement(getIdMedico);
            ps.setInt(1,dni);
            ResultSet rs = ps.executeQuery();
            int idmedico = 0;
            if(rs.next()){
                idmedico = rs.getInt("id");
            }else {
                throw new Exception("No se encontro medico con ese DNI");
            }
            System.out.println(idmedico);

            // recolectamos todos los intervalos de disponibilidad a iterar
            this.ps = connection.prepareStatement(getDisponibilidad);
            ps.setInt(1,idmedico);
            ResultSet rss = ps.executeQuery();

            while (rss.next()){ // recorremos todos los intervalos
                System.out.println("entro");
                // si el intervalo esta en el mismo mes en el que estamos iteramos la diferencia de dias hasta que termine
                if(LocalDate.now().getMonthValue() == rss.getDate("hasta").toLocalDate().getMonthValue()){


                    // recolectamos todo lo necesario del intervalo para la query
                    diasIterar = (int)rss.getDate("hasta").toLocalDate().getDayOfMonth() - LocalDate.now().getDayOfMonth();
                    desde = LocalDate.now();
                    turnosIterar = rss.getInt("cantidad_turnos");


                    for (int i = 0; i < diasIterar;i++) { // iteramos dia a dia en ese intervalo preguntando si hay turno

                        this.ps = connection.prepareStatement(turnosSerch);
                        ps.setDate(1,java.sql.Date.valueOf(desde.plusDays(i)));
                        ps.setInt(2,idmedico);
                        ResultSet rsp = ps.executeQuery();
                        if(rsp.next()){ // si en el dia visto hay n turnos tomados y son igual a m turnos posibles
                            // significa que no hay turnos disponibles, y si no, significa que si hay
                            if(rsp.getInt("turnos_ocupados")<turnosIterar){// hay disponibilidad
                                // si almenos hay 1 dia disponible y guardamos el lugar
                                Hospital h = new Hospital();
                                h.setNombre(rss.getString("nombre"));
                                h.setDireccion(rss.getString("direccion"));
                                hospitales.add(h);
                                fechas.add(rss.getDate("desde").toLocalDate());
                                //System.out.println("entro");
                                break;
                            }
                        }

                    }
                }else {// sino iteramos el mes completo

                    // recolectamos todo lo necesario del intervalo para la query
                    diasIterar = (int)rss.getDate("hasta").toLocalDate().getDayOfMonth() - ((int)rss.getDate("desde").toLocalDate().getDayOfMonth())+1;
                    desde = LocalDate.now();
                    turnosIterar = rss.getInt("cantidad_turnos");


                    for (int i = 0; i < diasIterar;i++) { // iteramos dia a dia en ese intervalo preguntando si hay turno

                        this.ps = connection.prepareStatement(turnosSerch);
                        ps.setDate(1,java.sql.Date.valueOf(desde.plusDays(i)));
                        ps.setInt(2,idmedico);
                        ResultSet rsp = ps.executeQuery();
                        if(rsp.next()){ // si en el dia visto hay n turnos tomados y son igual a m turnos posibles
                            // significa que no hay turnos disponibles, y si no, significa que si hay
                            if(rsp.getInt("turnos_ocupados")<turnosIterar){// hay disponibilidad
                                // si almenos hay 1 dia disponible y guardamos el lugar
                                Hospital h = new Hospital();
                                h.setNombre(rss.getString("nombre"));
                                h.setDireccion(rss.getString("direccion"));
                                hospitales.add(h);
                                fechas.add(rss.getDate("desde").toLocalDate());
                                break;
                            }
                        }

                    }
                }
            }
            List<List> hospitalesFechas = new ArrayList<>();
            hospitalesFechas.add(hospitales);
            hospitalesFechas.add(fechas);

        return hospitalesFechas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // vamos a buscar los dias disponibles del mes iterando en el iterbalo del {medico,hospital,mes} seleccionado
    // y viendo dia a dia si la cantidad de turnos disponibles es diferente a la cantidad de turnos tomados
    public List<LocalDate> getDias(int dni,String direccion,LocalDate fecha){
        String getIdMedico = "SELECT * FROM MEDICO m where m.dni = ?";
        String getIdHospital = "SELECT * FROM Hospital h where h.direccion like ?;";
        String getItervalo =    "SELECT \n" +
                                "    d.*, \n" +
                                "    h.*, \n" +
                                "    TIMESTAMPDIFF(MINUTE, d.horaDesde, d.horaHasta) / 30 AS cantidad_turnos,\n" +
                                "    DATEDIFF(DAY, d.desde, d.hasta) + 1 AS dias_entre\n" +
                                "FROM disponibilidad d \n" +
                                "JOIN HOSPITAL h ON h.ID = d.HOSPITAL \n" +
                                "WHERE d.hasta > CURRENT_DATE \n" +
                                "  AND YEAR(d.hasta) = ? \n" +
                                "  AND MONTH(d.hasta) = ? \n" +
                                "  AND d.MEDICO = ? \n" +
                                "  AND d.HOSPITAL = ?;";
        String getTurnosOcupados =  "SELECT \n" +
                                    "    count(*) AS turnos_ocupados \n" +
                                    "FROM turnos t\n" +
                                    "WHERE t.fecha = ?\n" +
                                    "  AND t.HOSPITAL = ?\n" +
                                    "  AND t.MEDICO = ?\n" +
                                    "  AND t.HORA BETWEEN ? AND ?"
                                    +" and t.estado > 0 ;";
        // variables a usar
        int idmedico = 0;
        int idhospital = 0;

        List<LocalDate> dias = new ArrayList<>();

        try {
            // busquemos el id de medico
            this.ps = connection.prepareStatement(getIdMedico);
            ps.setInt(1,dni);
            ResultSet mrs = ps.executeQuery();
            if (mrs.next()){
                idmedico = mrs.getInt("id");
            }else{
                throw new Exception("No se encontro medico con ese dni");
            }

            // busquemos el id de hospital
            this.ps = connection.prepareStatement(getIdHospital);
            ps.setString(1,direccion);
            ResultSet hrs = ps.executeQuery();
            if (hrs.next()){
                idhospital = hrs.getInt("id");
            }else {
                throw new Exception("No se encontro hospital con esa direccion");
            }

            // busquemos el intervalo pedido
            this.ps = connection.prepareStatement(getItervalo);
            ps.setInt(1, fecha.getYear());
            ps.setInt(2, fecha.getMonthValue());
            ps.setInt(3, idmedico);
            ps.setInt(4, idhospital);
            ResultSet irs = ps.executeQuery();

            while (irs.next()){
                // verificamos que estamos trabajando en el mes actual o en un proximo, asi no iteramos todo el mes si es este
                LocalDate fechaHasta = irs.getDate("hasta").toLocalDate();
                LocalDate fechaActual = LocalDate.now();
                int turnosIterar = irs.getInt("CANTIDAD_TURNOS");

                // Verificamos si la fecha está en el mismo mes y año actual
                if (fechaHasta.getYear() == fechaActual.getYear() && fechaHasta.getMonthValue() == fechaActual.getMonthValue()) {
                    // Resta de días si estamos en el mismo mes y año
                    long diasAIterar = ChronoUnit.DAYS.between(fechaActual, fechaHasta);

                    // Iterar días si es necesario
                    for (int i = 0; i < diasAIterar; i++) {
                        LocalDate diaIterado = fechaActual.plusDays(i);
                        // Lógica para cada día en el intervalo
                        this.ps = connection.prepareStatement(getTurnosOcupados);
                        ps.setDate(1, Date.valueOf(diaIterado));
                        ps.setString(2,direccion);
                        ps.setInt(3,idmedico);
                        ps.setTime(4,irs.getTime("horadesde"));
                        ps.setTime(5,irs.getTime("horahasta"));
                        ResultSet trs = ps.executeQuery();
                        if(trs.next()){
                            // si los turnos ocupados son menor a los posibles es que hay turnos disponibles ese dia
                            if(trs.getInt("TURNOS_OCUPADOS") < turnosIterar){
                                dias.add(diaIterado);
                            }
                        }
                    }


                } else {// Lógica para el caso de un mes futuro

                    int diasIterar = irs.getInt("DIAS_ENTRE");
                    LocalDate fechaDesde = irs.getDate("DESDE").toLocalDate();

                    // Iterar días si es necesario
                    for (int i = 0; i < diasIterar; i++) {
                        LocalDate diaIterado = fechaDesde.plusDays(i);
                        // Lógica para cada día en el intervalo
                        this.ps = connection.prepareStatement(getTurnosOcupados);
                        ps.setDate(1, Date.valueOf(diaIterado));
                        ps.setString(2,direccion);
                        ps.setInt(3,idmedico);
                        ps.setTime(4,irs.getTime("horadesde"));
                        ps.setTime(5,irs.getTime("horahasta"));
                        ResultSet trs = ps.executeQuery();
                        if(trs.next()){
                            // si los turnos ocupados son menor a los posibles es que hay turnos disponibles ese dia
                            if(trs.getInt("TURNOS_OCUPADOS") < turnosIterar){
                                dias.add(diaIterado);
                            }
                        }
                    }

                }
            }

            return dias;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public List<List> getHorario(int dni, String direccion, LocalDate dia){
        String getIdMedico = "SELECT * FROM MEDICO m where m.dni = ?";
        String getIdHospital = "SELECT * FROM Hospital h where h.direccion like ?;";

        String getIntervalo =   "SELECT *, \n" +
                                "    TIMESTAMPDIFF(MINUTE, d.horaDesde, d.horaHasta) / 30 AS cantidad_turnos\n" +
                                "FROM DISPONIBILIDAD d\n" +
                                "WHERE d.MEDICO = ?\n" +
                                "  AND d.HOSPITAL = ?\n" +
                                "  AND d.desde <= ?\n" +
                                "  AND d.hasta >= ?;";

        String getHoraTurno =   "SELECT *\n" +
                                "FROM turnos t\n" +
                                "WHERE t.fecha = ?\n" +
                                "  AND t.hora = ?" +
                                "  and t.estado > 0;";

        // variables a usar
        int idmedico = 0;
        int idhospital = 0;
        List<List> turnos = new ArrayList<>();

        try {
            // busquemos el id de medico
            this.ps = connection.prepareStatement(getIdMedico);
            ps.setInt(1,dni);
            ResultSet mrs = ps.executeQuery();
            if (mrs.next()){
                idmedico = mrs.getInt("id");
            }else{
                throw new Exception("No se encontro medico con ese dni");
            }

            // busquemos el id de hospital
            this.ps = connection.prepareStatement(getIdHospital);
            ps.setString(1,direccion);
            ResultSet hrs = ps.executeQuery();
            if (hrs.next()){
                idhospital = hrs.getInt("id");
            }else {
                throw new Exception("No se encontro hospital con esa direccion");
            }


            // buscamos el intervalo/s que contengan al dia ese
            this.ps = connection.prepareStatement(getIntervalo);
            ps.setInt(1,idmedico);
            ps.setInt(2,idhospital);
            ps.setDate(3, Date.valueOf(dia));
            ps.setDate(4, Date.valueOf(dia));
            ResultSet irs = ps.executeQuery();
            while (irs.next()){ // verificamos intervalo a intervalo los horarios que nos da y nos fijamos disponibilidad de ellos
                for(int i = 0; i < irs.getInt("cantidad_turnos");i++){ // recorremos n cantidad de turnos del intervalo
                    ps = connection.prepareStatement(getHoraTurno);
                    ps.setDate(1, Date.valueOf(dia));
                    ps.setTime(2,
                            // buscamos en la hora + kmins*turno 30 * turnoActual
                            // o sea vamos viendo horario a horario sumandole a la hora desde 30 minutos por turno
                            Time.valueOf( irs.getTime("horadesde").toLocalTime().plusMinutes(30*i) )
                            );
                    ResultSet trs = ps.executeQuery();
                    if (trs.next()){
                        // turno ocupado porque encontro un turno en ese horario
                    }else {
                        List<String> turno = new ArrayList<>(); // lo guardamos como string (simulamos json)
                        turno.add(dia.toString()); // el dia del turno
                        turno.add(irs.getTime("horadesde").toLocalTime().plusMinutes(30*i).toString()); // la hora del turno
                        turno.add(direccion); // la direccion
                        turno.add(dni+"");    // el dni del doctor
                        turno.add(String.valueOf(irs.getInt("consultorio"))); // consultorio
                        turnos.add(turno);
                    }
                }

            }

            return turnos;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void setTurno(int dniMedico,int dniPaciente , String direccion, LocalDate fecha, LocalTime hora,int consultorio){
        // consolidemos datos



        // querys

        String getIdMedico = "SELECT * FROM MEDICO m where m.dni = ?";
        String getIdHospital = "SELECT * FROM Hospital h where h.direccion like ?;";
        String getIdPaciente = "SELECT * FROM PACIENTE where dni = ?;";
        String verifyTurno = "SELECT * FROM TURNOS \n" +
                "where medico = ? and paciente = ? and hospital = ? and fecha = ? and hora = ? and consultorio = ? and estado > 0;";
        String dobleHoraTurno = "SELECT * FROM TURNOS \n" +
                "where paciente = ? and hora = ? and estado > 0;";
        String insertTurno = "INSERT INTO TURNOS (MEDICO, PACIENTE, HOSPITAL, FECHA, HORA, CONSULTORIO,estado)\n" +
                "VALUES (?, ?, ?, ?, ?, ?,1);";

        // variables a usar
        int idmedico = 0;
        int idhospital = 0;
        int idpaciente = 0;

        try {
            // busquemos el id de medico
            this.ps = connection.prepareStatement(getIdMedico);
            ps.setInt(1,dniMedico);
            ResultSet mrs = ps.executeQuery();
            if (mrs.next()){
                idmedico = mrs.getInt("id");
            }else{
                throw new Exception("No se encontro medico con ese dni");
            }

            // busquemos el id de hospital
            this.ps = connection.prepareStatement(getIdHospital);
            ps.setString(1,direccion);
            ResultSet hrs = ps.executeQuery();
            if (hrs.next()){
                idhospital = hrs.getInt("id");
            }else {
                throw new Exception("No se encontro hospital con esa direccion");
            }

            // busquemos el id de paciente
            this.ps = connection.prepareStatement(getIdPaciente);
            ps.setInt(1,dniPaciente);
            ResultSet prs = ps.executeQuery();
            if (prs.next()){
                idpaciente = prs.getInt("id");
            }else{
                throw new Exception("No se encontro medico con ese dni");
            }

            // verificamos que no este ese turno tomado

            this.ps = connection.prepareStatement(verifyTurno);
            ps.setInt(1,idmedico);
            ps.setInt(2,idpaciente);
            ps.setString(3,direccion);
            ps.setDate(4, Date.valueOf(fecha));
            ps.setTime(5, Time.valueOf(hora));
            ps.setInt(6,consultorio);
            ResultSet ttrs = ps.executeQuery();
            if (ttrs.next()){
                // esta tomado el turno
            }else {
                // no esta tomado y continuamos

                // verificamos si ese usuario no tiene un turno a esa hora ya tomado
                this.ps = connection.prepareStatement(dobleHoraTurno);
                ps.setInt(1,idpaciente);
                ps.setTime(2, Time.valueOf(hora));
                ResultSet dhtrs = ps.executeQuery();
                if (dhtrs.next()){
                    // ya tiene ese horario tomado
                    throw new Exception("Ya tienes un turno con ese horario"+
                            "muestra el turno...");
                }else {
                    // no tiene turno a ese horario prosiga ..

                    this.ps = connection.prepareStatement(insertTurno);
                    ps.setInt(1,idmedico);
                    ps.setInt(2,idpaciente);
                    ps.setString(3,direccion);
                    ps.setDate(4, Date.valueOf(fecha));
                    ps.setTime(5, Time.valueOf(hora));
                    ps.setInt(6, consultorio);

                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected == 1) {
                        System.out.println("El turno se insertó correctamente.");
                    } else {
                        throw new Exception("Hubo un problema al insertar el turno.");
                    }

                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
