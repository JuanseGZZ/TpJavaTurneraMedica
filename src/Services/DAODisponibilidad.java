package Services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class DAODisponibilidad extends DAOconecction {

    public int consolidar(int dniMedico, LocalDate desde, LocalDate hasta, String direccion, int consultorio, LocalTime desdeH, LocalTime hastaH) {
        // Consulta para verificar si hay solapamiento de horarios
        String checkQuery = """
        SELECT * 
        FROM DISPONIBILIDAD 
        WHERE 
            CONSULTORIO = ? 
            AND HOSPITAL = (SELECT ID FROM HOSPITAL WHERE DIRECCION = ?) 
            AND (DESDE <= ? AND HASTA >= ?)
            AND (HORADESDE < ? AND HORAHASTA > ?);
    """;

        // Consulta para insertar nuevo intervalo
        String insertQuery = """
        INSERT INTO DISPONIBILIDAD (MEDICO, DESDE, HASTA, HOSPITAL, CONSULTORIO, HORADESDE, HORAHASTA)
        VALUES (?, ?, ?, (SELECT ID FROM HOSPITAL WHERE DIRECCION = ?), ?, ?, ?);
    """;

        try {
            // Verificar solapamiento
            this.ps = connection.prepareStatement(checkQuery);
            ps.setInt(1, consultorio);
            ps.setString(2, direccion);
            ps.setDate(3, java.sql.Date.valueOf(hasta));
            ps.setDate(4, java.sql.Date.valueOf(desde));
            ps.setTime(5, java.sql.Time.valueOf(hastaH));
            ps.setTime(6, java.sql.Time.valueOf(desdeH));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Ya existe un intervalo que solapa
                System.out.println("Ya existe un intervalo que solapa con las fechas y horas proporcionadas.");
                return 1;
            }

            // Insertar nuevo intervalo
            this.ps = connection.prepareStatement(insertQuery);
            ps.setInt(1, dniMedico);
            ps.setDate(2, java.sql.Date.valueOf(desde));
            ps.setDate(3, java.sql.Date.valueOf(hasta));
            ps.setString(4, direccion);
            ps.setInt(5, consultorio);
            ps.setTime(6, java.sql.Time.valueOf(desdeH));
            ps.setTime(7, java.sql.Time.valueOf(hastaH));
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Intervalo consolidado exitosamente.");
            } else {
                System.out.println("No se pudo consolidar el intervalo.");
            }
            return 0;
        } catch (SQLException e) {
            System.out.println("Error al consolidar el intervalo: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public Map<String, String> getHospitales() {
        String sql = "SELECT * FROM HOSPITAL";
        Map<String, String> map = new HashMap<>();
        try {
            this.ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Agregar un bucle while para iterar por los resultados
            while (rs.next()) {
                String nombre = rs.getString("NOMBRE");
                String direccion = rs.getString("DIRECCION");
                map.put(nombre, direccion); // Insertar en el mapa
            }
            return map;
        } catch (SQLException e) {
            System.out.println("aca falla");
            throw new RuntimeException(e);
        }
    }


    public Map<String, String> getMedicos() {
        String sql = "SELECT * FROM MEDICO";
        Map<String, String> map = new HashMap<>();
        try {
            this.ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Obtener valores de las columnas NOMBRE, APELLIDO y DNI
                String nombreCompleto = rs.getString("NOMBRE") + " " + rs.getString("APELLIDO");
                String dni = rs.getString("DNI");

                // Insertar en el mapa
                map.put(nombreCompleto, dni);
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return map; // Retornar el mapa con los valores
    }

    public Map<String, String> getConsultoriosPorHospital(String direccionHospital) {
        String sql = """
        SELECT c.NOMBRE, c.DESC
        FROM CONSULTORIO c
        JOIN HOSPITAL h ON h.ID = c.HOSPITAL
        WHERE h.DIRECCION = ?
    """;
        Map<String, String> consultorios = new HashMap<>();
        try {
            this.ps = connection.prepareStatement(sql);
            ps.setString(1, direccionHospital); // Pasar la dirección del hospital como parámetro
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Obtener el nombre y descripción del consultorio y agregarlos al mapa
                String nombre = rs.getString("NOMBRE");
                String descripcion = rs.getString("DESC");
                consultorios.put(nombre, descripcion);
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return consultorios; // Retornar el mapa con los consultorios
    }


}
