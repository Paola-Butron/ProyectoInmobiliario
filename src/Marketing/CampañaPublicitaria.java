package Marketing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CampañaPublicitaria {

    private int idCampaña;
    private String nombreCanal;
    private String tipoCanal;
    private String descripcion;
    private String estado;

    public CampañaPublicitaria(int idCampaña, String nombreCanal, String tipoCanal, String descripcion, String estado) {
        this.idCampaña = idCampaña;
        this.nombreCanal = nombreCanal;
        this.tipoCanal = tipoCanal;
        this.descripcion = descripcion;
        this.estado = estado;
    }
    
    public int getIdCampaña() { return idCampaña; }
    public void setIdCampaña(int idCampaña) { this.idCampaña = idCampaña; }
    public String getNombreCanal() { return nombreCanal; }
    public void setNombreCanal(String nombreCanal) { this.nombreCanal = nombreCanal; }
    public String getTipoCanal() { return tipoCanal; }
    public void setTipoCanal(String tipoCanal) { this.tipoCanal = tipoCanal; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "CampañaPublicitaria{" + "idCampaña=" + idCampaña + ", nombreCanal='" + nombreCanal + "'}";
    }

    public static void insertar(Connection conn, CampañaPublicitaria campaña) {
        String sql = "INSERT INTO CampañaPublicitaria (IDCAMPAÑA, NOMBRECANAL, TIPOCANAL, DESCRIPCION, ESTADO) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, campaña.getIdCampaña());
            pstmt.setString(2, campaña.getNombreCanal());
            pstmt.setString(3, campaña.getTipoCanal());
            pstmt.setString(4, campaña.getDescripcion());
            pstmt.setString(5, campaña.getEstado());
            pstmt.executeUpdate();
            System.out.println("Nueva campaña publicitaria insertada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar la campaña: " + e.getMessage());
        }
    }

    public static CampañaPublicitaria buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM CampañaPublicitaria WHERE IDCAMPAÑA = ?";
        CampañaPublicitaria campaña = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    campaña = new CampañaPublicitaria(
                        rs.getInt("IDCAMPAÑA"),
                        rs.getString("NOMBRECANAL"),
                        rs.getString("TIPOCANAL"),
                        rs.getString("DESCRIPCION"),
                        rs.getString("ESTADO")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la campaña: " + e.getMessage());
        }
        return campaña;
    }

    public static List<CampañaPublicitaria> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM CampañaPublicitaria ORDER BY IDCAMPAÑA";
        List<CampañaPublicitaria> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new CampañaPublicitaria(
                    rs.getInt("IDCAMPAÑA"),
                    rs.getString("NOMBRECANAL"),
                    rs.getString("TIPOCANAL"),
                    rs.getString("DESCRIPCION"),
                    rs.getString("ESTADO")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las campañas: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM CampañaPublicitaria WHERE IDCAMPAÑA = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Campaña con ID " + id + " eliminada correctamente.");
            } else {
                System.out.println("No se encontró ninguna campaña con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar la campaña: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, CampañaPublicitaria campaña) {
        String sql = "UPDATE CampañaPublicitaria SET NOMBRECANAL = ?, TIPOCANAL = ?, DESCRIPCION = ?, ESTADO = ? WHERE IDCAMPAÑA = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, campaña.getNombreCanal());
            pstmt.setString(2, campaña.getTipoCanal());
            pstmt.setString(3, campaña.getDescripcion());
            pstmt.setString(4, campaña.getEstado());
            pstmt.setInt(5, campaña.getIdCampaña());

            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Campaña actualizada correctamente.");
            } else {
                System.out.println("No se encontró una campaña con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar la campaña: " + e.getMessage());
        }
    }
}