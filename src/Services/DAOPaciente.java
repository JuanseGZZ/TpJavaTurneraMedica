package Services;

import DAO.DAOIPaciente;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}