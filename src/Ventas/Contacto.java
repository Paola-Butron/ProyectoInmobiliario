package Ventas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Contacto {

    private int idLead;
    private int idVendedor;
    private String tipoContacto;
    private Date fechaContacto;

    public Contacto(int idLead, int idVendedor, String tipoContacto, Date fechaContacto) {
        this.idLead = idLead;
        this.idVendedor = idVendedor;
        this.tipoContacto = tipoContacto;
        this.fechaContacto = fechaContacto;
    }

    public int getIdLead() { return idLead; }
    public void setIdLead(int idLead) { this.idLead = idLead; }
    public int getIdVendedor() { return idVendedor; }
    public void setIdVendedor(int idVendedor) { this.idVendedor = idVendedor; }
    public String getTipoContacto() { return tipoContacto; }
    public void setTipoContacto(String tipoContacto) { this.tipoContacto = tipoContacto; }
    public Date getFechaContacto() { return fechaContacto; }
    public void setFechaContacto(Date fechaContacto) { this.fechaContacto = fechaContacto; }

    @Override
    public String toString() {
        return "Contacto{" + "idLead=" + idLead + ", idVendedor=" + idVendedor + ", tipo='" + tipoContacto + "'}";
    }

    public static void insertar(Connection conn, Contacto contacto) {
        String sql = "INSERT INTO Contacto (IDLEAD, IDVENDEDOR, TIPOCONTACTO, FECHACONTACTO) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, contacto.getIdLead());
            pstmt.setInt(2, contacto.getIdVendedor());
            pstmt.setString(3, contacto.getTipoContacto());
            pstmt.setDate(4, new java.sql.Date(contacto.getFechaContacto().getTime()));
            pstmt.executeUpdate();
            System.out.println("Nuevo contacto insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar el contacto: " + e.getMessage());
        }
    }

    public static Contacto buscarPorId(Connection conn, int idLead, int idVendedor) {
        String sql = "SELECT * FROM Contacto WHERE IDLEAD = ? AND IDVENDEDOR = ?";
        Contacto contacto = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idLead);
            pstmt.setInt(2, idVendedor);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    contacto = new Contacto(
                        rs.getInt("IDLEAD"),
                        rs.getInt("IDVENDEDOR"),
                        rs.getString("TIPOCONTACTO"),
                        rs.getDate("FECHACONTACTO")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el contacto: " + e.getMessage());
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
                    rs.getInt("IDLEAD"),
                    rs.getInt("IDVENDEDOR"),
                    rs.getString("TIPOCONTACTO"),
                    rs.getDate("FECHACONTACTO")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los contactos: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int idLead, int idVendedor) {
        String sql = "DELETE FROM Contacto WHERE IDLEAD = ? AND IDVENDEDOR = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idLead);
            pstmt.setInt(2, idVendedor);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Contacto (" + idLead + ", " + idVendedor + ") eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún contacto con esa combinación de IDs.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el contacto: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Contacto contacto) {
        String sql = "UPDATE Contacto SET TIPOCONTACTO = ?, FECHACONTACTO = ? WHERE IDLEAD = ? AND IDVENDEDOR = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contacto.getTipoContacto());
            pstmt.setDate(2, new java.sql.Date(contacto.getFechaContacto().getTime()));
            pstmt.setInt(3, contacto.getIdLead());
            pstmt.setInt(4, contacto.getIdVendedor());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Contacto actualizado correctamente.");
            } else {
                System.out.println("No se encontró el contacto para actualizar.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el contacto: " + e.getMessage());
        }
    }
}
