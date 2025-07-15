package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DB_USER = "PROYECTO";
    private static final String DB_PASSWORD = "PASSWORD"; 

    public static Connection obtenerConexion() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("✅ Conexión a Oracle exitosa.");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a Oracle: " + e.getMessage());
            return null;
        }
    }
}
