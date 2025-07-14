/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Finanzas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la entidad Banco. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Banco {

    // --- Atributos que coinciden con la tabla BANCO ---
    private long idBanco;
    private String nombre;
    private String direccion;
    private String distrito;
    private String ruc;

    // --- Constructores ---
    public Banco() {
    }

    public Banco(long idBanco, String nombre, String direccion, String distrito, String ruc) {
        this.idBanco = idBanco;
        this.nombre = nombre;
        this.direccion = direccion;
        this.distrito = distrito;
        this.ruc = ruc;
    }

    // --- Getters y Setters ---
    public long getIdBanco() { return idBanco; }
    public void setIdBanco(long idBanco) { this.idBanco = idBanco; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }

    @Override
    public String toString() {
        return "Banco{" + "idBanco=" + idBanco + ", nombre='" + nombre + "'}";
    }

    /*
     * Inserta un nuevo banco en la base de datos.
     * @param conn La conexión activa a la base de datos.
     * @param banco El objeto Banco con los datos a insertar.
     */
    public static void insertar(Connection conn, Banco banco) {
        String sql = "INSERT INTO Banco (IDBANCO, NOMBRE, DIRECCION, DISTRITO, RUC) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, banco.getIdBanco());
            pstmt.setString(2, banco.getNombre());
            pstmt.setString(3, banco.getDireccion());
            pstmt.setString(4, banco.getDistrito());
            pstmt.setString(5, banco.getRuc());
            pstmt.executeUpdate();
            System.out.println("✅ Nuevo banco insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar el banco: " + e.getMessage());
        }
    }

    /**
     * Busca y devuelve un banco por su ID.
     * @param conn La conexión activa a la base de datos.
     * @param id El ID del banco a buscar.
     * @return Un objeto Banco si se encuentra, o null si no.
     */
    public static Banco buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Banco WHERE IDBANCO = ?";
        Banco banco = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    banco = new Banco(
                        rs.getLong("IDBANCO"),
                        rs.getString("NOMBRE"),
                        rs.getString("DIRECCION"),
                        rs.getString("DISTRITO"),
                        rs.getString("RUC")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar el banco: " + e.getMessage());
        }
        return banco;
    }

    /**
     * Devuelve una lista con todos los bancos de la base de datos.
     * @param conn La conexión activa a la base de datos.
     * @return Una lista de objetos Banco.
     */
    public static List<Banco> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Banco ORDER BY IDBANCO";
        List<Banco> listaBancos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Banco banco = new Banco(
                    rs.getLong("IDBANCO"),
                    rs.getString("NOMBRE"),
                    rs.getString("DIRECCION"),
                    rs.getString("DISTRITO"),
                    rs.getString("RUC")
                );
                listaBancos.add(banco);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los bancos: " + e.getMessage());
        }
        return listaBancos;
    }

    /**
     * Elimina un banco de la base de datos por su ID.
     * @param conn La conexión activa a la base de datos.
     * @param id El ID del banco a eliminar.
     */
    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Banco WHERE IDBANCO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Banco con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ningún banco con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el banco: " + e.getMessage());
        }
    }
}
