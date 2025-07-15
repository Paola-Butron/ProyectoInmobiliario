package Legal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Abogado {

    private int idAbogado;
    private String nombreCompleto;
    private String colegiatura;
    private String telefono;
    private String correo;
    private String especialidad;
    private int idNotaria;

    public Abogado(int idAbogado, String nombreCompleto, String colegiatura, String telefono, String correo, String especialidad, int idNotaria) {
        this.idAbogado = idAbogado;
        this.nombreCompleto = nombreCompleto;
        this.colegiatura = colegiatura;
        this.telefono = telefono;
        this.correo = correo;
        this.especialidad = especialidad;
        this.idNotaria = idNotaria;
    }

    public int getIdAbogado() { return idAbogado; }
    public void setIdAbogado(int idAbogado) { this.idAbogado = idAbogado; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getColegiatura() { return colegiatura; }
    public void setColegiatura(String colegiatura) { this.colegiatura = colegiatura; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public int getIdNotaria() { return idNotaria; }
    public void setIdNotaria(int idNotaria) { this.idNotaria = idNotaria; }

    @Override
    public String toString() {
        return "Abogado{" + "idAbogado=" + idAbogado + ", nombreCompleto='" + nombreCompleto + "'}";
    }

    public static void insertar(Connection conn, Abogado abogado) {
        String sql = "INSERT INTO Abogado (IDABOGADO, NOMBRECOMPLETO, COLEGIATURA, TELEFONO, CORREO, ESPECIALIDAD, IDNOTARIA) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, abogado.getIdAbogado());
            pstmt.setString(2, abogado.getNombreCompleto());
            pstmt.setString(3, abogado.getColegiatura());
            pstmt.setString(4, abogado.getTelefono());
            pstmt.setString(5, abogado.getCorreo());
            pstmt.setString(6, abogado.getEspecialidad());
            pstmt.setInt(7, abogado.getIdNotaria());
            pstmt.executeUpdate();
            System.out.println("Nuevo abogado insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar el abogado: " + e.getMessage());
        }
    }

    public static Abogado buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Abogado WHERE IDABOGADO = ?";
        Abogado abogado = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    abogado = new Abogado(
                        rs.getInt("IDABOGADO"),
                        rs.getString("NOMBRECOMPLETO"),
                        rs.getString("COLEGIATURA"),
                        rs.getString("TELEFONO"),
                        rs.getString("CORREO"),
                        rs.getString("ESPECIALIDAD"),
                        rs.getInt("IDNOTARIA")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el abogado: " + e.getMessage());
        }
        return abogado;
    }

    public static List<Abogado> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Abogado ORDER BY IDABOGADO";
        List<Abogado> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Abogado(
                    rs.getInt("IDABOGADO"),
                    rs.getString("NOMBRECOMPLETO"),
                    rs.getString("COLEGIATURA"),
                    rs.getString("TELEFONO"),
                    rs.getString("CORREO"),
                    rs.getString("ESPECIALIDAD"),
                    rs.getInt("IDNOTARIA")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los abogados: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Abogado WHERE IDABOGADO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Abogado con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún abogado con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el abogado: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Abogado abogado) {
        String sql = "UPDATE Abogado SET NOMBRECOMPLETO = ?, COLEGIATURA = ?, TELEFONO = ?, CORREO = ?, ESPECIALIDAD = ?, IDNOTARIA = ? WHERE IDABOGADO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, abogado.getNombreCompleto());
            pstmt.setString(2, abogado.getColegiatura());
            pstmt.setString(3, abogado.getTelefono());
            pstmt.setString(4, abogado.getCorreo());
            pstmt.setString(5, abogado.getEspecialidad());
            pstmt.setInt(6, abogado.getIdNotaria());
            pstmt.setInt(7, abogado.getIdAbogado());

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Abogado actualizado correctamente.");
            } else {
                System.out.println("No se encontró el abogado con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el abogado: " + e.getMessage());
        }
    }
}
