package kemel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectorSQL {

    private Connection conexion;

    public ConectorSQL() {
        this.conexion = null;
    }

    public void conectar() {
        try {
            String url = "jdbc:sqlserver://kemel.database.windows.net:1433;database=kemel;" +
                         "user=jllozada@kemel;password=JoseL1005339128;" +
                         "encrypt=true;trustServerCertificate=false;" +
                         "hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            
            conexion = DriverManager.getConnection(url);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public Connection getConexion() {
        return conexion;
    }
}

