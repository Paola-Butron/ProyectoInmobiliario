package Marketing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CanalEntrada {

    private int idCanalEntrada;
    private String nombreCampaña;
    private Date fechaInicio;
    private Date fechaFin;
    private double presupuesto;
    private String estado;
    private String descripcion;
    private String tipo;
    private int idCampaña;

    public CanalEntrada(int idCanalEntrada, String nombreCampaña, Date fechaInicio, Date fechaFin, double presupuesto, String estado, String descripcion, String tipo, int idCampaña) {
        this.idCanalEntrada = idCanalEntrada;
        this.nombreCampaña = nombreCampaña;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.presupuesto = presupuesto;
        this.estado = estado;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.idCampaña = idCampaña;
    }

    public int getIdCanalEntrada() { return idCanalEntrada; }
    public void setIdCanalEntrada(int idCanalEntrada) { this.idCanalEntrada = idCanalEntrada; }
    public String getNombreCampaña() { return nombreCampaña; }
    public void setNombreCampaña(String nombreCampaña) { this.nombreCampaña = nombreCampaña; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
    public double getPresupuesto() { return presupuesto; }
    public void setPresupuesto(double presupuesto) { this.presupuesto = presupuesto; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public int getIdCampaña() { return idCampaña; }
    public void setIdCampaña(int idCampaña) { this.idCampaña = idCampaña; }

    @Override
    public String toString() {
        return "CanalEntrada{" + "idCanalEntrada=" + idCanalEntrada + ", nombreCampaña='" + nombreCampaña + "'}";
    }

    public static void insertar(Connection conn, CanalEntrada canal) {
        String sql = "INSERT INTO CanalEntrada (IDCANALENTRADA, NOMBRECAMPAÑA, FECHAINICIO, FECHAFIN, PRESUPUESTO, ESTADO, DESCRIPCION, TIPO, IDCAMPAÑA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, canal.getIdCanalEntrada());
            pstmt.setString(2, canal.getNombreCampaña());
            pstmt.setDate(3, new java.sql.Date(canal.getFechaInicio().getTime()));
            pstmt.setDate(4, new java.sql.Date(canal.getFechaFin().getTime()));
            pstmt.setDouble(5, canal.getPresupuesto());
            pstmt.setString(6, canal.getEstado());
            pstmt.setString(7, canal.getDescripcion());
            pstmt.setString(8, canal.getTipo());
            pstmt.setInt(9, canal.getIdCampaña());
            pstmt.executeUpdate();
            System.out.println("Nuevo canal de entrada insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar el canal de entrada: " + e.getMessage());
        }
    }

    public static CanalEntrada buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM CanalEntrada WHERE IDCANALENTRADA = ?";
        CanalEntrada canal = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    canal = new CanalEntrada(
                        rs.getInt("IDCANALENTRADA"),
                        rs.getString("NOMBRECAMPAÑA"),
                        rs.getDate("FECHAINICIO"),
                        rs.getDate("FECHAFIN"),
                        rs.getDouble("PRESUPUESTO"),
                        rs.getString("ESTADO"),
                        rs.getString("DESCRIPCION"),
                        rs.getString("TIPO"),
                        rs.getInt("IDCAMPAÑA")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el canal de entrada: " + e.getMessage());
        }
        return canal;
    }

    public static List<CanalEntrada> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM CanalEntrada ORDER BY IDCANALENTRADA";
        List<CanalEntrada> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new CanalEntrada(
                    rs.getInt("IDCANALENTRADA"),
                    rs.getString("NOMBRECAMPAÑA"),
                    rs.getDate("FECHAINICIO"),
                    rs.getDate("FECHAFIN"),
                    rs.getDouble("PRESUPUESTO"),
                    rs.getString("ESTADO"),
                    rs.getString("DESCRIPCION"),
                    rs.getString("TIPO"),
                    rs.getInt("IDCAMPAÑA")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los canales de entrada: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM CanalEntrada WHERE IDCANALENTRADA = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Canal de entrada con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún canal de entrada con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el canal de entrada: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, CanalEntrada canal) {
        String sql = "UPDATE CanalEntrada SET NOMBRECAMPAÑA = ?, FECHAINICIO = ?, FECHAFIN = ?, PRESUPUESTO = ?, ESTADO = ?, DESCRIPCION = ?, TIPO = ?, IDCAMPAÑA = ? WHERE IDCANALENTRADA = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, canal.getNombreCampaña());
            pstmt.setDate(2, new java.sql.Date(canal.getFechaInicio().getTime()));
            pstmt.setDate(3, new java.sql.Date(canal.getFechaFin().getTime()));
            pstmt.setDouble(4, canal.getPresupuesto());
            pstmt.setString(5, canal.getEstado());
            pstmt.setString(6, canal.getDescripcion());
            pstmt.setString(7, canal.getTipo());
            pstmt.setInt(8, canal.getIdCampaña());
            pstmt.setInt(9, canal.getIdCanalEntrada());

            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Canal de entrada actualizado correctamente.");
            } else {
                System.out.println("No se encontró un canal de entrada con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el canal de entrada: " + e.getMessage());
        }
    }
}