package Legal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Documento {

    private int idDocumento;
    private String nombreDocumento;
    private String estado;
    private Date fechaFirma;
    private int idAbogado;
    private int idAsesorMunicipal;
    private int idTasador;
    private int idCliente;

    public Documento(int idDocumento, String nombreDocumento, String estado, Date fechaFirma, int idAbogado, int idAsesorMunicipal, int idTasador, int idCliente) {
        this.idDocumento = idDocumento;
        this.nombreDocumento = nombreDocumento;
        this.estado = estado;
        this.fechaFirma = fechaFirma;
        this.idAbogado = idAbogado;
        this.idAsesorMunicipal = idAsesorMunicipal;
        this.idTasador = idTasador;
        this.idCliente = idCliente;
    }

    public int getIdDocumento() { return idDocumento; }
    public void setIdDocumento(int idDocumento) { this.idDocumento = idDocumento; }
    public String getNombreDocumento() { return nombreDocumento; }
    public void setNombreDocumento(String nombreDocumento) { this.nombreDocumento = nombreDocumento; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Date getFechaFirma() { return fechaFirma; }
    public void setFechaFirma(Date fechaFirma) { this.fechaFirma = fechaFirma; }
    public int getIdAbogado() { return idAbogado; }
    public void setIdAbogado(int idAbogado) { this.idAbogado = idAbogado; }
    public int getIdAsesorMunicipal() { return idAsesorMunicipal; }
    public void setIdAsesorMunicipal(int idAsesorMunicipal) { this.idAsesorMunicipal = idAsesorMunicipal; }
    public int getIdTasador() { return idTasador; }
    public void setIdTasador(int idTasador) { this.idTasador = idTasador; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    @Override
    public String toString() {
        return "Documento{" + "idDocumento=" + idDocumento + ", nombreDocumento='" + nombreDocumento + "'}";
    }

    public static void insertar(Connection conn, Documento documento) {
        String sql = "INSERT INTO Documento (IDDOCUMENTO, NOMBREDOCUMENTO, ESTADO, FECHAFIRMA, IDABOGADO, IDASESORMUNICIPAL, IDTASADOR, IDCLIENTE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, documento.getIdDocumento());
            pstmt.setString(2, documento.getNombreDocumento());
            pstmt.setString(3, documento.getEstado());
            pstmt.setDate(4, new java.sql.Date(documento.getFechaFirma().getTime()));
            pstmt.setInt(5, documento.getIdAbogado());
            pstmt.setInt(6, documento.getIdAsesorMunicipal());
            pstmt.setInt(7, documento.getIdTasador());
            pstmt.setInt(8, documento.getIdCliente());
            pstmt.executeUpdate();
            System.out.println("Nuevo documento insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar el documento: " + e.getMessage());
        }
    }

    public static Documento buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Documento WHERE IDDOCUMENTO = ?";
        Documento documento = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    documento = new Documento(
                        rs.getInt("IDDOCUMENTO"),
                        rs.getString("NOMBREDOCUMENTO"),
                        rs.getString("ESTADO"),
                        rs.getDate("FECHAFIRMA"),
                        rs.getInt("IDABOGADO"),
                        rs.getInt("IDASESORMUNICIPAL"),
                        rs.getInt("IDTASADOR"),
                        rs.getInt("IDCLIENTE")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el documento: " + e.getMessage());
        }
        return documento;
    }

    public static List<Documento> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Documento ORDER BY IDDOCUMENTO";
        List<Documento> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Documento(
                    rs.getInt("IDDOCUMENTO"),
                    rs.getString("NOMBREDOCUMENTO"),
                    rs.getString("ESTADO"),
                    rs.getDate("FECHAFIRMA"),
                    rs.getInt("IDABOGADO"),
                    rs.getInt("IDASESORMUNICIPAL"),
                    rs.getInt("IDTASADOR"),
                    rs.getInt("IDCLIENTE")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los documentos: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Documento WHERE IDDOCUMENTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Documento con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún documento con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el documento: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Documento documento) {
        String sql = "UPDATE Documento SET NOMBREDOCUMENTO = ?, ESTADO = ?, FECHAFIRMA = ?, IDABOGADO = ?, IDASESORMUNICIPAL = ?, IDTASADOR = ?, IDCLIENTE = ? WHERE IDDOCUMENTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, documento.getNombreDocumento());
            pstmt.setString(2, documento.getEstado());
            pstmt.setDate(3, new java.sql.Date(documento.getFechaFirma().getTime()));
            pstmt.setInt(4, documento.getIdAbogado());
            pstmt.setInt(5, documento.getIdAsesorMunicipal());
            pstmt.setInt(6, documento.getIdTasador());
            pstmt.setInt(7, documento.getIdCliente());
            pstmt.setInt(8, documento.getIdDocumento());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Documento actualizado correctamente.");
            } else {
                System.out.println("No se encontró el documento con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el documento: " + e.getMessage());
        }
    }
}