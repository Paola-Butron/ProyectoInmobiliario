package Municipal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AsesorMunicipal {

    private int idAsesorMunicipal;
    private String nombreCompleto;
    private String dni;
    private String telefono;
    private String correo;
    private int idMunicipalidad; // Clave Foránea

    public AsesorMunicipal(int idAsesorMunicipal, String nombreCompleto, String dni, String telefono, String correo, int idMunicipalidad) {
        this.idAsesorMunicipal = idAsesorMunicipal;
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.telefono = telefono;
        this.correo = correo;
        this.idMunicipalidad = idMunicipalidad;
    }

    public int getIdAsesorMunicipal() { return idAsesorMunicipal; }
    public void setIdAsesorMunicipal(int idAsesorMunicipal) { this.idAsesorMunicipal = idAsesorMunicipal; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public int getIdMunicipalidad() { return idMunicipalidad; }
    public void setIdMunicipalidad(int idMunicipalidad) { this.idMunicipalidad = idMunicipalidad; }

    @Override
    public String toString() {
        return "AsesorMunicipal{" + "idAsesorMunicipal=" + idAsesorMunicipal + ", nombreCompleto='" + nombreCompleto + "'}";
    }

    public static void insertar(Connection conn, AsesorMunicipal asesor) {
        String sql = "INSERT INTO AsesorMunicipal (IDASESORMUNICIPAL, NOMBRECOMPLETO, DNI, TELEFONO, CORREO, IDMUNICIPALIDAD) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, asesor.getIdAsesorMunicipal());
            pstmt.setString(2, asesor.getNombreCompleto());
            pstmt.setString(3, asesor.getDni());
            pstmt.setString(4, asesor.getTelefono());
            pstmt.setString(5, asesor.getCorreo());
            pstmt.setInt(6, asesor.getIdMunicipalidad());
            pstmt.executeUpdate();
            System.out.println("Nuevo asesor municipal insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar el asesor municipal: " + e.getMessage());
        }
    }

    public static AsesorMunicipal buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM AsesorMunicipal WHERE IDASESORMUNICIPAL = ?";
        AsesorMunicipal asesor = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    asesor = new AsesorMunicipal(
                        rs.getInt("IDASESORMUNICIPAL"),
                        rs.getString("NOMBRECOMPLETO"),
                        rs.getString("DNI"),
                        rs.getString("TELEFONO"),
                        rs.getString("CORREO"),
                        rs.getInt("IDMUNICIPALIDAD")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar el asesor municipal: " + e.getMessage());
        }
        return asesor;
    }

    public static List<AsesorMunicipal> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM AsesorMunicipal ORDER BY IDASESORMUNICIPAL";
        List<AsesorMunicipal> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new AsesorMunicipal(
                    rs.getInt("IDASESORMUNICIPAL"),
                    rs.getString("NOMBRECOMPLETO"),
                    rs.getString("DNI"),
                    rs.getString("TELEFONO"),
                    rs.getString("CORREO"),
                    rs.getInt("IDMUNICIPALIDAD")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los asesores municipales: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM AsesorMunicipal WHERE IDASESORMUNICIPAL = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Asesor municipal con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún asesor municipal con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el asesor municipal: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, AsesorMunicipal asesor) {
        String sql = "UPDATE AsesorMunicipal SET NOMBRECOMPLETO = ?, DNI = ?, TELEFONO = ?, CORREO = ?, IDMUNICIPALIDAD = ? WHERE IDASESORMUNICIPAL = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, asesor.getNombreCompleto());
            pstmt.setString(2, asesor.getDni());
            pstmt.setString(3, asesor.getTelefono());
            pstmt.setString(4, asesor.getCorreo());
            pstmt.setInt(5, asesor.getIdMunicipalidad());
            pstmt.setInt(6, asesor.getIdAsesorMunicipal());

            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Asesor municipal actualizado correctamente.");
            } else {
                System.out.println("No se encontró un asesor municipal con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el asesor municipal: " + e.getMessage());
        }
    }
}