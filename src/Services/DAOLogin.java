package Services;

import DAO.IDAOLogin;
import Entidades.Administ;
import Entidades.Medico;
import Entidades.Paciente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DAOLogin extends DAOconecction implements IDAOLogin {

    @Override
    public List<String> login(String user, String password) {
        String getAdmin = "SELECT * FROM ADMINIST where username like ? and password like ?";
        String getMedico = "SELECT * FROM MEDICO where username like ? and password like ?";
        String getPaciente = "SELECT * FROM PACIENTE where username like ? and password like ?";

        List<String> response = new ArrayList<>();

        try{
            this.ps = connection.prepareStatement(getAdmin);
            ps.setString(1,user);
            ps.setString(2,password);
            ResultSet ars = ps.executeQuery();
            if (ars.next()){
                response.add(String.valueOf(ars.getInt("tipo")));
                response.add(ars.getString("nombre"));
                response.add(ars.getString("apellido"));
                response.add(String.valueOf(ars.getInt("dni")));
                response.add(ars.getString("area"));
                return response;
            }else {
                this.ps =connection.prepareStatement(getPaciente);
                ps.setString(1,user);
                ps.setString(2,password);
                ResultSet prs = ps.executeQuery();
                if(prs.next()){
                    response.add(String.valueOf(prs.getInt("tipo")));
                    response.add(prs.getString("nombre"));
                    response.add(prs.getString("apellido"));
                    response.add(String.valueOf(prs.getInt("dni")));
                    return response;
                }else {
                    this.ps = connection.prepareStatement(getMedico);
                    ps.setString(1,user);
                    ps.setString(2,password);
                    ResultSet mrs = ps.executeQuery();
                    if (mrs.next()){
                        response.add(String.valueOf(mrs.getInt("tipo")));
                        response.add(mrs.getString("nombre"));
                        response.add(mrs.getString("apellido"));
                        response.add(String.valueOf(mrs.getInt("dni")));
                        response.add(mrs.getString("prestacion"));
                        return response;
                    }else {
                        return new ArrayList<>();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
