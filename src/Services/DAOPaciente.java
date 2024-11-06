package Services;

import DAO.DAOIPaciente;
import Entidades.Paciente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOPaciente extends DAOconecction implements DAOIPaciente {
    @Override
    public int getId(int dni) {
        String query = "SELECT id FROM PACIENTE where dni = ?;";
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

    public List<Paciente> getPacientes(){
        String query = "SELECT * FROM PACIENTE;";
        List<Paciente> pacientes = new ArrayList<>();
        try{
            this.ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Paciente p = new Paciente();
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                p.setDni(rs.getInt("dni"));
                pacientes.add(p);
            }
            return pacientes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
