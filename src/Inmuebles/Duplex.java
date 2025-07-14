/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inmuebles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la entidad Duplex. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Duplex {

    // --- Atributos que coinciden con la tabla DUPLEX ---
    private long idInmueble; // Clave Primaria y Foránea
    private int niveles;
    private int dormitorios;
    private int banos;
    private String terraza;
    private String accesoIndependiente;

    // --- Constructor ---
    public Duplex(long idInmueble, int niveles, int dormitorios, int banos, String terraza, String accesoIndependiente) {
        this.idInmueble = idInmueble;
        this.niveles = niveles;
        this.dormitorios = dormitorios;
        this.banos = banos;
        this.terraza = terraza;
        this.accesoIndependiente = accesoIndependiente;
    }

    // --- Getters y Setters ---
    public long getIdInmueble() { return idInmueble; }
    public void setIdInmueble(long idInmueble) { this.idInmueble = idInmueble; }
    public int getNiveles() { return niveles; }
    public void setNiveles(int niveles) { this.niveles = niveles; }
    public int getDormitorios() { return dormitorios; }
    public void setDormitorios(int dormitorios) { this.dormitorios = dormitorios; }
    public int getBanos() { return banos; }
    public void setBanos(int banos) { this.banos = banos; }
    public String getTerraza() { return terraza; }
    public void setTerraza(String terraza) { this.terraza = terraza; }
    public String getAccesoIndependiente() { return accesoIndependiente; }
    public void setAccesoIndependiente(String accesoIndependiente) { this.accesoIndependiente = accesoIndependiente; }

    @Override
    public String toString() {
        return "Duplex{" + "idInmueble=" + idInmueble + ", niveles=" + niveles + ", dormitorios=" + dormitorios + "}";
    }

    // =====================================================================
    // MÉTODOS DE ACCESO A DATOS (DAO)
    // =====================================================================

    public static void insertar(Connection conn, Duplex duplex) {
        String sql = "INSERT INTO Duplex (IDINMUEBLE, NIVELES, DORMITORIOS, BAÑOS, TERRAZA, ACCESOINDEPENDIENTE) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, duplex.getIdInmueble());
            pstmt.setInt(2, duplex.getNiveles());
            pstmt.setInt(3, duplex.getDormitorios());
            pstmt.setInt(4, duplex.getBanos());
            pstmt.setString(5, duplex.getTerraza());
            pstmt.setString(6, duplex.getAccesoIndependiente());
            pstmt.executeUpdate();
            System.out.println("✅ Nuevo duplex insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar el duplex: " + e.getMessage());
        }
    }

    public static Duplex buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Duplex WHERE IDINMUEBLE = ?";
        Duplex duplex = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    duplex = new Duplex(
                        rs.getLong("IDINMUEBLE"),
                        rs.getInt("NIVELES"),
                        rs.getInt("DORMITORIOS"),
                        rs.getInt("BAÑOS"),
                        rs.getString("TERRAZA"),
                        rs.getString("ACCESOINDEPENDIENTE")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar el duplex: " + e.getMessage());
        }
        return duplex;
    }

    public static List<Duplex> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Duplex ORDER BY IDINMUEBLE";
        List<Duplex> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Duplex(
                    rs.getLong("IDINMUEBLE"),
                    rs.getInt("NIVELES"),
                    rs.getInt("DORMITORIOS"),
                    rs.getInt("BAÑOS"),
                    rs.getString("TERRAZA"),
                    rs.getString("ACCESOINDEPENDIENTE")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los duplex: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Duplex WHERE IDINMUEBLE = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Duplex con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ningún duplex con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el duplex: " + e.getMessage());
        }
    }
}