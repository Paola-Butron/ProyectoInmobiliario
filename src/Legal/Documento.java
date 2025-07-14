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
import java.util.Date;
import java.util.List;

/**
 * Representa la entidad Documento. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Documento {

    // --- Atributos que coinciden con la tabla DOCUMENTO ---
    private long idDocumento;
    private String nombreDocumento;
    private String estado;
    private Date fechaFirma;
    private long idAbogado;
    private long idAsesorMunicipal;
    private long idTasador;
    private long idCliente;

    // --- Constructor ---
    public Documento(long idDocumento, String nombreDocumento, String estado, Date fechaFirma, long idAbogado, long idAsesorMunicipal, long idTasador, long idCliente) {
        this.idDocumento = idDocumento;
        this.nombreDocumento = nombreDocumento;
        this.estado = estado;
        this.fechaFirma = fechaFirma;
        this.idAbogado = idAbogado;
        this.idAsesorMunicipal = idAsesorMunicipal;
        this.idTasador = idTasador;
        this.idCliente = idCliente;
    }

    // --- Getters y Setters ---
    public long getIdDocumento() { return idDocumento; }
    public void setIdDocumento(long idDocumento) { this.idDocumento = idDocumento; }
    public String getNombreDocumento() { return nombreDocumento; }
    public void setNombreDocumento(String nombreDocumento) { this.nombreDocumento = nombreDocumento; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Date getFechaFirma() { return fechaFirma; }
    public void setFechaFirma(Date fechaFirma) { this.fechaFirma = fechaFirma; }
    public long getIdAbogado() { return idAbogado; }
    public void setIdAbogado(long idAbogado) { this.idAbogado = idAbogado; }
    public long getIdAsesorMunicipal() { return idAsesorMunicipal; }
    public void setIdAsesorMunicipal(long idAsesorMunicipal) { this.idAsesorMunicipal = idAsesorMunicipal; }
    public long getIdTasador() { return idTasador; }
    public void setIdTasador(long idTasador) { this.idTasador = idTasador; }
    public long getIdCliente() { return idCliente; }
    public void setIdCliente(long idCliente) { this.idCliente = idCliente; }

    @Override
    public String toString() {
        return "Documento{" + "idDocumento=" + idDocumento + ", nombreDocumento='" + nombreDocumento + "'}";
    }

    // =====================================================================
    // MÉTODOS DE ACCESO A DATOS (DAO)
    // =====================================================================

    public static void insertar(Connection conn, Documento documento) {
        String sql = "INSERT INTO Documento (IDDOCUMENTO, NOMBREDOCUMENTO, ESTADO, FECHAFIRMA, IDABOGADO, IDASESORMUNICIPAL, IDTASADOR, IDCLIENTE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, documento.getIdDocumento());
            pstmt.setString(2, documento.getNombreDocumento());
            pstmt.setString(3, documento.getEstado());
            pstmt.setDate(4, new java.sql.Date(documento.getFechaFirma().getTime()));
            pstmt.setLong(5, documento.getIdAbogado());
            pstmt.setLong(6, documento.getIdAsesorMunicipal());
            pstmt.setLong(7, documento.getIdTasador());
            pstmt.setLong(8, documento.getIdCliente());
            pstmt.executeUpdate();
            System.out.println("✅ Nuevo documento insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar el documento: " + e.getMessage());
        }
    }

    public static Documento buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Documento WHERE IDDOCUMENTO = ?";
        Documento documento = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    documento = new Documento(
                        rs.getLong("IDDOCUMENTO"),
                        rs.getString("NOMBREDOCUMENTO"),
                        rs.getString("ESTADO"),
                        rs.getDate("FECHAFIRMA"),
                        rs.getLong("IDABOGADO"),
                        rs.getLong("IDASESORMUNICIPAL"),
                        rs.getLong("IDTASADOR"),
                        rs.getLong("IDCLIENTE")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar el documento: " + e.getMessage());
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
                    rs.getLong("IDDOCUMENTO"),
                    rs.getString("NOMBREDOCUMENTO"),
                    rs.getString("ESTADO"),
                    rs.getDate("FECHAFIRMA"),
                    rs.getLong("IDABOGADO"),
                    rs.getLong("IDASESORMUNICIPAL"),
                    rs.getLong("IDTASADOR"),
                    rs.getLong("IDCLIENTE")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los documentos: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Documento WHERE IDDOCUMENTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Documento con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ningún documento con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el documento: " + e.getMessage());
        }
    }
}