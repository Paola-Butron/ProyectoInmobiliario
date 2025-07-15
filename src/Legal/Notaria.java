package Legal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Notaria {

    private int idNotaria;
    private String nombre;
    private String ubicacion;
    private String numeroRegistro;
    private String notarioPrincipal;

    public Notaria(int idNotaria, String nombre, String ubicacion, String numeroRegistro, String notarioPrincipal) {
        this.idNotaria = idNotaria;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.numeroRegistro = numeroRegistro;
        this.notarioPrincipal = notarioPrincipal;
    }
    
    public int getIdNotaria() { return idNotaria; }
    public void setIdNotaria(int idNotaria) { this.idNotaria = idNotaria; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getNumeroRegistro() { return numeroRegistro; }
    public void setNumeroRegistro(String numeroRegistro) { this.numeroRegistro = numeroRegistro; }
    public String getNotarioPrincipal() { return notarioPrincipal; }
    public void setNotarioPrincipal(String notarioPrincipal) { this.notarioPrincipal = notarioPrincipal; }

     @Override
    public String toString() {
        return "Notaria{" + "idNotaria=" + idNotaria + ", nombre='" + nombre + "'}";
    }

    public static void insertar(Connection conn, Notaria notaria) {
        String sql = "INSERT INTO Notaria (IDNOTARIA, NOMBRE, UBICACION, NUMEROREGISTRO, NOTARIOPRINCIPAL) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, notaria.getIdNotaria());
            pstmt.setString(2, notaria.getNombre());
            pstmt.setString(3, notaria.getUbicacion());
            pstmt.setString(4, notaria.getNumeroRegistro());
            pstmt.setString(5, notaria.getNotarioPrincipal());
            pstmt.executeUpdate();
            System.out.println("Nueva notaría insertada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar la notaría: " + e.getMessage());
        }
    }

    public static Notaria buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Notaria WHERE IDNOTARIA = ?";
        Notaria notaria = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    notaria = new Notaria(
                        rs.getInt("IDNOTARIA"),
                        rs.getString("NOMBRE"),
                        rs.getString("UBICACION"),
                        rs.getString("NUMEROREGISTRO"),
                        rs.getString("NOTARIOPRINCIPAL")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la notaría: " + e.getMessage());
        }
        return notaria;
    }

    public static List<Notaria> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Notaria ORDER BY IDNOTARIA";
        List<Notaria> listaNotarias = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                listaNotarias.add(new Notaria(
                    rs.getInt("IDNOTARIA"),
                    rs.getString("NOMBRE"),
                    rs.getString("UBICACION"),
                    rs.getString("NUMEROREGISTRO"),
                    rs.getString("NOTARIOPRINCIPAL")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las notarías: " + e.getMessage());
        }
        return listaNotarias;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Notaria WHERE IDNOTARIA = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Notaría con ID " + id + " eliminada correctamente.");
            } else {
                System.out.println("No se encontró ninguna notaría con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar la notaría: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Notaria n) {
        String sql = "UPDATE Notaria SET NOMBRE = ?, UBICACION = ?, NUMEROREGISTRO = ?, NOTARIOPRINCIPAL = ? WHERE IDNOTARIA = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, n.getNombre());
            pstmt.setString(2, n.getUbicacion());
            pstmt.setString(3, n.getNumeroRegistro());
            pstmt.setString(4, n.getNotarioPrincipal());
            pstmt.setInt(5, n.getIdNotaria());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Notaría actualizada correctamente.");
            } else {
                System.out.println("No se encontró ninguna notaría con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar la notaría: " + e.getMessage());
        }
    }
}