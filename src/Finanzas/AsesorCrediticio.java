package Finanzas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AsesorCrediticio {

    private int idAsesorCrediticio;
    private String nombreCompleto;
    private String dni;
    private String telefono;
    private String correo;
    private int idBanco; 

    public AsesorCrediticio(int idAsesorCrediticio, String nombreCompleto, String dni, String telefono, String correo, int idBanco) {
        this.idAsesorCrediticio = idAsesorCrediticio;
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.telefono = telefono;
        this.correo = correo;
        this.idBanco = idBanco;
    }

    public int getIdAsesorCrediticio() { return idAsesorCrediticio; }
    public void setIdAsesorCrediticio(int idAsesorCrediticio) { this.idAsesorCrediticio = idAsesorCrediticio; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public int getIdBanco() { return idBanco; }
    public void setIdBanco(int idBanco) { this.idBanco = idBanco; }

    @Override
    public String toString() {
        return "AsesorCrediticio{" + "idAsesorCrediticio=" + idAsesorCrediticio + ", nombreCompleto='" + nombreCompleto + "'}";
    }

    public static void insertar(Connection conn, AsesorCrediticio asesor) {
        String sql = "INSERT INTO AsesorCrediticio (IDASESORCREDITICIO, NOMBRECOMPLETO, DNI, TELEFONO, CORREO, IDBANCO) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, asesor.getIdAsesorCrediticio());
            pstmt.setString(2, asesor.getNombreCompleto());
            pstmt.setString(3, asesor.getDni());
            pstmt.setString(4, asesor.getTelefono());
            pstmt.setString(5, asesor.getCorreo());
            pstmt.setInt(6, asesor.getIdBanco());
            pstmt.executeUpdate();
            System.out.println("Nuevo asesor crediticio insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar el asesor crediticio: " + e.getMessage());
        }
    }

    public static AsesorCrediticio buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM AsesorCrediticio WHERE IDASESORCREDITICIO = ?";
        AsesorCrediticio asesor = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    asesor = new AsesorCrediticio(
                        rs.getInt("IDASESORCREDITICIO"),
                        rs.getString("NOMBRECOMPLETO"),
                        rs.getString("DNI"),
                        rs.getString("TELEFONO"),
                        rs.getString("CORREO"),
                        rs.getInt("IDBANCO")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el asesor crediticio: " + e.getMessage());
        }
        return asesor;
    }

    public static List<AsesorCrediticio> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM AsesorCrediticio ORDER BY IDASESORCREDITICIO";
        List<AsesorCrediticio> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new AsesorCrediticio(
                    rs.getInt("IDASESORCREDITICIO"),
                    rs.getString("NOMBRECOMPLETO"),
                    rs.getString("DNI"),
                    rs.getString("TELEFONO"),
                    rs.getString("CORREO"),
                    rs.getInt("IDBANCO")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los asesores crediticios: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM AsesorCrediticio WHERE IDASESORCREDITICIO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Asesor crediticio con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún asesor crediticio con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el asesor crediticio: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, AsesorCrediticio asesor) {
        String sql = "UPDATE AsesorCrediticio SET NOMBRECOMPLETO = ?, DNI = ?, TELEFONO = ?, CORREO = ?, IDBANCO = ? WHERE IDASESORCREDITICIO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, asesor.getNombreCompleto());
            pstmt.setString(2, asesor.getDni());
            pstmt.setString(3, asesor.getTelefono());
            pstmt.setString(4, asesor.getCorreo());
            pstmt.setInt(5, asesor.getIdBanco());
            pstmt.setInt(6, asesor.getIdAsesorCrediticio());

            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Asesor crediticio actualizado correctamente.");
            } else {
                System.out.println("No se encontró un asesor crediticio con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el asesor crediticio: " + e.getMessage());
        }
    }
}