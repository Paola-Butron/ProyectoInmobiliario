package Finanzas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Tasador {

    private int idTasador;
    private String nombreCompleto;
    private String registro;
    private String telefono;
    private String correo;
    private int idBanco; 

    public Tasador(int idTasador, String nombreCompleto, String registro, String telefono, String correo, int idBanco) {
        this.idTasador = idTasador;
        this.nombreCompleto = nombreCompleto;
        this.registro = registro;
        this.telefono = telefono;
        this.correo = correo;
        this.idBanco = idBanco;
    }

    public int getIdTasador() { return idTasador; }
    public void setIdTasador(int idTasador) { this.idTasador = idTasador; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getRegistro() { return registro; }
    public void setRegistro(String registro) { this.registro = registro; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public int getIdBanco() { return idBanco; }
    public void setIdBanco(int idBanco) { this.idBanco = idBanco; }

    @Override
    public String toString() {
        return "Tasador{" + "idTasador=" + idTasador + ", nombreCompleto='" + nombreCompleto + "'}";
    }

    public static void insertar(Connection conn, Tasador tasador) {
        String sql = "INSERT INTO Tasador (IDTASADOR, NOMBRECOMPLETO, REGISTRO, TELEFONO, CORREO, IDBANCO) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tasador.getIdTasador());
            pstmt.setString(2, tasador.getNombreCompleto());
            pstmt.setString(3, tasador.getRegistro());
            pstmt.setString(4, tasador.getTelefono());
            pstmt.setString(5, tasador.getCorreo());
            pstmt.setInt(6, tasador.getIdBanco());
            pstmt.executeUpdate();
            System.out.println("Nuevo tasador insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar el tasador: " + e.getMessage());
        }
    }

    public static Tasador buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Tasador WHERE IDTASADOR = ?";
        Tasador tasador = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    tasador = new Tasador(
                        rs.getInt("IDTASADOR"),
                        rs.getString("NOMBRECOMPLETO"),
                        rs.getString("REGISTRO"),
                        rs.getString("TELEFONO"),
                        rs.getString("CORREO"),
                        rs.getInt("IDBANCO")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el tasador: " + e.getMessage());
        }
        return tasador;
    }

    public static List<Tasador> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Tasador ORDER BY IDTASADOR";
        List<Tasador> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Tasador(
                    rs.getInt("IDTASADOR"),
                    rs.getString("NOMBRECOMPLETO"),
                    rs.getString("REGISTRO"),
                    rs.getString("TELEFONO"),
                    rs.getString("CORREO"),
                    rs.getInt("IDBANCO")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los tasadores: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Tasador WHERE IDTASADOR = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Tasador con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún tasador con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el tasador: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Tasador tasador) {
        String sql = "UPDATE Tasador SET NOMBRECOMPLETO = ?, REGISTRO = ?, TELEFONO = ?, CORREO = ?, IDBANCO = ? WHERE IDTASADOR = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tasador.getNombreCompleto());
            pstmt.setString(2, tasador.getRegistro());
            pstmt.setString(3, tasador.getTelefono());
            pstmt.setString(4, tasador.getCorreo());
            pstmt.setInt(5, tasador.getIdBanco());
            pstmt.setInt(6, tasador.getIdTasador());

            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Tasador actualizado correctamente.");
            } else {
                System.out.println("No se encontró un tasador con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el tasador: " + e.getMessage());
        }
    }
}

