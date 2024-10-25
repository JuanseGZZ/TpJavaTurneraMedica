package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOconecction {
    private String jdbcUrl = "jdbc:h2:~/orm"; // Conexión a la base de datos 'test' que creaste
    private String username = "sa";
    private String password = "";
    protected static Connection connection = null;
    protected PreparedStatement ps = null;

    DAOconecction(){
        try {
            connection = DriverManager.getConnection(jdbcUrl,username,password);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
