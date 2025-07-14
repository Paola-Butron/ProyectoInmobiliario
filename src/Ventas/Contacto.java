/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representa la entidad de enlace Contacto. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Contacto {

    // --- Atributos que coinciden con la tabla CONTACTO ---
    private long idLead;
    private long idVendedor;
    private String tipoContacto;
    private Date fechaContacto;

    // --- Constructor ---
    public Contacto(long idLead, long idVendedor, String tipoContacto, Date fechaContacto) {
        this.idLead = idLead;
        this.idVendedor = idVendedor;
        this.tipoContacto = tipoContacto;
        this.fechaContacto = fechaContacto;
    }

    // --- Getters y Setters ---
    public long getIdLead() { return idLead; }
    public void setIdLead(long idLead) { this.idLead = idLead; }
    public long getIdVendedor() { return idVendedor; }
    public void setIdVendedor(long idVendedor) { this.idVendedor = idVendedor; }
    public String getTipoContacto() { return tipoContacto; }
    public void setTipoContacto(String tipoContacto) { this.tipoContacto = tipoContacto; }
    public Date getFechaContacto() { return fechaContacto; }
    public void setFechaContacto(Date fechaContacto) { this.fechaContacto = fechaContacto; }

    @Override
    public String toString() {
        return "Contacto{" + "idLead=" + idLead + ", idVendedor=" + idVendedor + ", tipo='" + tipoContacto + "'}";
    }

    // =====================================================================
    // MÉTODOS DE ACCESO A DATOS (DAO)
    // =====================================================================

    public static void insertar(Connection conn, Contacto contacto) {
        String sql = "INSERT INTO Contacto (IDLEAD, IDVENDEDOR, TIPOCONTACTO, FECHACONTACTO) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, contacto.getIdLead());
            pstmt.setLong(2, contacto.getIdVendedor());
            pstmt.setString(3, contacto.getTipoContacto());
            pstmt.setDate(4, new java.sql.Date(contacto.getFechaContacto().getTime()));
            pstmt.executeUpdate();
            System.out.println("✅ Nuevo contacto insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar el contacto: " + e.getMessage());
        }
    }

    /**
     * Busca un contacto por su clave primaria compuesta.
     * @param conn La conexión a la BD.
     * @param idLead El ID del Lead.
     * @param idVendedor El ID del Vendedor.
     * @return El objeto Contacto encontrado, o null.
     */
    public static Contacto buscarPorId(Connection conn, long idLead, long idVendedor) {
        String sql = "SELECT * FROM Contacto WHERE IDLEAD = ? AND IDVENDEDOR = ?";
        Contacto contacto = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, idLead);
            pstmt.setLong(2, idVendedor);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    contacto = new Contacto(
                        rs.getLong("IDLEAD"),
                        rs.getLong("IDVENDEDOR"),
                        rs.getString("TIPOCONTACTO"),
                        rs.getDate("FECHACONTACTO")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar el contacto: " + e.getMessage());
        }
        return contacto;
    }

    public static List<Contacto> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Contacto ORDER BY FECHACONTACTO DESC";
        List<Contacto> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Contacto(
                    rs.getLong("IDLEAD"),
                    rs.getLong("IDVENDEDOR"),
                    rs.getString("TIPOCONTACTO"),
                    rs.getDate("FECHACONTACTO")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los contactos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Elimina un contacto por su clave primaria compuesta.
     * @param conn La conexión a la BD.
     * @param idLead El ID del Lead.
     * @param idVendedor El ID del Vendedor.
     */
    public static void eliminar(Connection conn, long idLead, long idVendedor) {
        String sql = "DELETE FROM Contacto WHERE IDLEAD = ? AND IDVENDEDOR = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, idLead);
            pstmt.setLong(2, idVendedor);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Contacto (" + idLead + ", " + idVendedor + ") eliminado correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ningún contacto con esa combinación de IDs.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el contacto: " + e.getMessage());
        }
    }
}

