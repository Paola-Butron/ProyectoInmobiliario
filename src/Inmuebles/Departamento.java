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
 * Representa la entidad Departamento. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Departamento {

    // --- Atributos que coinciden con la tabla DEPARTAMENTO ---
    private long idInmueble; // Clave Primaria y Foránea
    private int numeroPiso;
    private int dormitorios;
    private int banos;
    private String balcon;

    // --- Constructor ---
    public Departamento(long idInmueble, int numeroPiso, int dormitorios, int banos, String balcon) {
        this.idInmueble = idInmueble;
        this.numeroPiso = numeroPiso;
        this.dormitorios = dormitorios;
        this.banos = banos;
        this.balcon = balcon;
    }

    // --- Getters y Setters ---
    public long getIdInmueble() { return idInmueble; }
    public void setIdInmueble(long idInmueble) { this.idInmueble = idInmueble; }
    public int getNumeroPiso() { return numeroPiso; }
    public void setNumeroPiso(int numeroPiso) { this.numeroPiso = numeroPiso; }
    public int getDormitorios() { return dormitorios; }
    public void setDormitorios(int dormitorios) { this.dormitorios = dormitorios; }
    public int getBanos() { return banos; }
    public void setBanos(int banos) { this.banos = banos; }
    public String getBalcon() { return balcon; }
    public void setBalcon(String balcon) { this.balcon = balcon; }

    @Override
    public String toString() {
        return "Departamento{" + "idInmueble=" + idInmueble + ", dormitorios=" + dormitorios + ", banos=" + banos + "}";
    }

    // =====================================================================
    // MÉTODOS DE ACCESO A DATOS (DAO)
    // =====================================================================

    public static void insertar(Connection conn, Departamento depto) {
        String sql = "INSERT INTO Departamento (IDINMUEBLE, NUMEROPISO, DORMITORIOS, BAÑOS, BALCON) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, depto.getIdInmueble());
            pstmt.setInt(2, depto.getNumeroPiso());
            pstmt.setInt(3, depto.getDormitorios());
            pstmt.setInt(4, depto.getBanos());
            pstmt.setString(5, depto.getBalcon());
            pstmt.executeUpdate();
            System.out.println("✅ Nuevo departamento insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar el departamento: " + e.getMessage());
        }
    }

    public static Departamento buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Departamento WHERE IDINMUEBLE = ?";
        Departamento depto = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    depto = new Departamento(
                        rs.getLong("IDINMUEBLE"),
                        rs.getInt("NUMEROPISO"),
                        rs.getInt("DORMITORIOS"),
                        rs.getInt("BAÑOS"),
                        rs.getString("BALCON")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar el departamento: " + e.getMessage());
        }
        return depto;
    }

    public static List<Departamento> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Departamento ORDER BY IDINMUEBLE";
        List<Departamento> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Departamento(
                    rs.getLong("IDINMUEBLE"),
                    rs.getInt("NUMEROPISO"),
                    rs.getInt("DORMITORIOS"),
                    rs.getInt("BAÑOS"),
                    rs.getString("BALCON")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los departamentos: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Departamento WHERE IDINMUEBLE = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Departamento con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ningún departamento con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el departamento: " + e.getMessage());
        }
    }
}
