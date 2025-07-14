/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Legal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la entidad Abogado. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Abogado {

    // --- Atributos que coinciden con la tabla ABOGADO ---
    private long idAbogado;
    private String nombreCompleto;
    private String colegiatura;
    private String telefono;
    private String correo;
    private String especialidad;
    private long idNotaria; // Clave Foránea

    // --- Constructor ---
    public Abogado(long idAbogado, String nombreCompleto, String colegiatura, String telefono, String correo, String especialidad, long idNotaria) {
        this.idAbogado = idAbogado;
        this.nombreCompleto = nombreCompleto;
        this.colegiatura = colegiatura;
        this.telefono = telefono;
        this.correo = correo;
        this.especialidad = especialidad;
        this.idNotaria = idNotaria;
    }

    // --- Getters y Setters ---
    public long getIdAbogado() { return idAbogado; }
    public void setIdAbogado(long idAbogado) { this.idAbogado = idAbogado; }
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
    public long getIdNotaria() { return idNotaria; }
    public void setIdNotaria(long idNotaria) { this.idNotaria = idNotaria; }

    @Override
    public String toString() {
        return "Abogado{" + "idAbogado=" + idAbogado + ", nombreCompleto='" + nombreCompleto + "'}";
    }

    // =====================================================================
    // MÉTODOS DE ACCESO A DATOS (DAO)
    // =====================================================================

    public static void insertar(Connection conn, Abogado abogado) {
        String sql = "INSERT INTO Abogado (IDABOGADO, NOMBRECOMPLETO, COLEGIATURA, TELEFONO, CORREO, ESPECIALIDAD, IDNOTARIA) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, abogado.getIdAbogado());
            pstmt.setString(2, abogado.getNombreCompleto());
            pstmt.setString(3, abogado.getColegiatura());
            pstmt.setString(4, abogado.getTelefono());
            pstmt.setString(5, abogado.getCorreo());
            pstmt.setString(6, abogado.getEspecialidad());
            pstmt.setLong(7, abogado.getIdNotaria());
            pstmt.executeUpdate();
            System.out.println("✅ Nuevo abogado insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar el abogado: " + e.getMessage());
        }
    }

    public static Abogado buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Abogado WHERE IDABOGADO = ?";
        Abogado abogado = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    abogado = new Abogado(
                        rs.getLong("IDABOGADO"),
                        rs.getString("NOMBRECOMPLETO"),
                        rs.getString("COLEGIATURA"),
                        rs.getString("TELEFONO"),
                        rs.getString("CORREO"),
                        rs.getString("ESPECIALIDAD"),
                        rs.getLong("IDNOTARIA")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar el abogado: " + e.getMessage());
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
                    rs.getLong("IDABOGADO"),
                    rs.getString("NOMBRECOMPLETO"),
                    rs.getString("COLEGIATURA"),
                    rs.getString("TELEFONO"),
                    rs.getString("CORREO"),
                    rs.getString("ESPECIALIDAD"),
                    rs.getLong("IDNOTARIA")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los abogados: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Abogado WHERE IDABOGADO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Abogado con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ningún abogado con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el abogado: " + e.getMessage());
        }
    }
}