package Services;

import DAO.DAOIBajarTurno;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class DAOBajarTurno extends DAOconecction implements DAOIBajarTurno {

    // a la hora de mostrar voy a ponerlos en una coleccio en orden y voy a mostrar el orden y el idex que pikee lo voy a traer a elminar

    @Override
    public void darBaja(int medico, int paciente, String lugar, LocalDate fecha, LocalTime hora, int consultorio) {
        String getTurno = "SELECT t.id \n" +
                "FROM TURNOS t\n" +
                "WHERE t.medico = ? \n" +
                "  AND t.paciente = ? \n" +
                "  AND t.hospital = ? \n" +
                "  AND t.fecha = ? \n" +
                "  AND t.hora = ? \n" +
                "  AND t.consultorio = ? \n" +
                "  AND t.estado > 0;\n";

        String updateTurno = "UPDATE TURNOS SET estado = 0 WHERE id = ?;";

        try {
            // Preparar la consulta para obtener el ID del turno
            this.ps = connection.prepareStatement(getTurno);
            ps.setInt(1, medico);
            ps.setInt(2, paciente);
            ps.setString(3, lugar);
            ps.setDate(4, Date.valueOf(fecha));
            ps.setTime(5, Time.valueOf(hora));
            ps.setInt(6, consultorio);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Obtener el ID del turno
                int turnoId = rs.getInt("id");

                // Preparar y ejecutar la consulta para actualizar el estado del turno
                this.ps = connection.prepareStatement(updateTurno);
                ps.setInt(1, turnoId);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("El estado del turno con ID " + turnoId + " ha sido actualizado a 0.");
                } else {
                    System.out.println("No se pudo actualizar el estado del turno.");
                }
            } else {
                System.out.println("No se encontró un turno con los criterios especificados.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
