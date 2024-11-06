package Services;

import DAO.DAOIMedico;
import Entidades.Medico;
import Entidades.Paciente;
import Entidades.Turno;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOMedico extends DAOconecction implements DAOIMedico {

    @Override
    public int getId(int dni) {
        String query = "SELECT id FROM MEDICO where dni = ?;";
        try{
            this.ps = connection.prepareStatement(query);
            ps.setInt(1,dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getInt("id");
            }else {
                throw new Exception("no hay un paciente con ese dni");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //falta ver los turnos que fueron tomados
    public List<Turno> verTurno(int dni, int tipo, LocalDate desde, LocalDate hasta) {

        // variables necesarias
        int id = 0;
        String query = "";
        List<Turno> turnos = new ArrayList<>();

        if (tipo == 2) {// medico
            String getIdMedico = "SELECT * FROM MEDICO m where m.dni = ?";
            try {
                // busquemos el id de medico
                this.ps = connection.prepareStatement(getIdMedico);
                ps.setInt(1, dni);
                ResultSet mrs = ps.executeQuery();
                if (mrs.next()) {
                    id = mrs.getInt("id");
                    query = "SELECT t.*, \n" +
                            "       m.dni AS medico_dni, \n" +
                            "       m.nombre AS medico_nombre, \n" +
                            "       m.apellido AS medico_apellido, \n" +
                            "       m.prestacion,\n" +
                            "       p.dni AS paciente_dni, \n" +
                            "       p.nombre AS paciente_nombre, \n" +
                            "       p.apellido AS paciente_apellido \n" +
                            "FROM TURNOS t\n" +
                            "JOIN MEDICO m ON (t.medico = m.id)\n" +
                            "JOIN PACIENTE p ON (t.paciente = p.id)\n" +
                            "WHERE m.id = ? and t.estado = 2" +
                            "AND t.fecha BETWEEN ? AND ? ;";
                } else {
                    throw new Exception("No se encontro medico con ese dni");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        try {
            this.ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.setDate(2, Date.valueOf(desde));
            ps.setDate(3, Date.valueOf(hasta));
            ResultSet trs = ps.executeQuery();
            while (trs.next()) {
                // medio cargo turno
                Turno t = new Turno();
                t.setHora(trs.getTime("hora").toLocalTime());
                t.setFecha(trs.getDate("fecha").toLocalDate());
                t.setConsultorio(trs.getInt("consultorio"));
                t.setLugar(trs.getString("hospital"));
                t.setEstado(trs.getInt("estado"));
                //cargo medico
                Medico m = new Medico();
                m.setDni(trs.getInt("medico_dni"));
                m.setPrestacion(trs.getString("prestacion"));
                m.setNombre(trs.getString("medico_nombre"));
                m.setApellido(trs.getString("medico_apellido"));
                //cargo paciente
                Paciente p = new Paciente();
                p.setApellido(trs.getString("paciente_apellido"));
                p.setNombre(trs.getString("paciente_nombre"));
                p.setDni(trs.getInt("paciente_dni"));
                //se los cargo a turno
                t.setMedico(m);
                t.setPaciente(p);
                // agrego turno a la lista
                turnos.add(t);
            }

            return turnos;

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }else {
            return null;
        }

    }



    public List<Turno> verTodosTurnos(LocalDate desde, LocalDate hasta){
        String query = "SELECT t.*, \n" +
                "       m.dni AS medico_dni, \n" +
                "       m.nombre AS medico_nombre, \n" +
                "       m.apellido AS medico_apellido, \n" +
                "       m.prestacion,\n" +
                "       p.dni AS paciente_dni, \n" +
                "       p.nombre AS paciente_nombre, \n" +
                "       p.apellido AS paciente_apellido \n" +
                "FROM TURNOS t\n" +
                "JOIN MEDICO m ON (t.medico = m.id)\n" +
                "JOIN PACIENTE p ON (t.paciente = p.id)\n" +
                "WHERE t.estado = 2" +
                "AND t.fecha BETWEEN ? AND ? " +
                "ORDER BY m.dni;";
        List<Turno> turnos = new ArrayList<>();

        try {
            this.ps = connection.prepareStatement(query);
            ps.setDate(1, Date.valueOf(desde));
            ps.setDate(2, Date.valueOf(hasta));
            ResultSet vtrs = ps.executeQuery();
            while (vtrs.next()){
                // medio cargo turno
                Turno t = new Turno();
                t.setHora(vtrs.getTime("hora").toLocalTime());
                t.setFecha(vtrs.getDate("fecha").toLocalDate());
                t.setConsultorio(vtrs.getInt("consultorio"));
                t.setLugar(vtrs.getString("hospital"));
                t.setEstado(vtrs.getInt("estado"));
                //cargo medico
                Medico m = new Medico();
                m.setDni(vtrs.getInt("medico_dni"));
                m.setPrestacion(vtrs.getString("prestacion"));
                m.setNombre(vtrs.getString("medico_nombre"));
                m.setApellido(vtrs.getString("medico_apellido"));
                //cargo paciente
                Paciente p = new Paciente();
                p.setApellido(vtrs.getString("paciente_apellido"));
                p.setNombre(vtrs.getString("paciente_nombre"));
                p.setDni(vtrs.getInt("paciente_dni"));
                //se los cargo a turno
                t.setMedico(m);
                t.setPaciente(p);
                // agrego turno a la lista
                turnos.add(t);
            }
            return turnos;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
