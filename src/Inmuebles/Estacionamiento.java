package Inmuebles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Estacionamiento {

    private int idInmueble;
    private String numero;
    private String subterraneo;
    private String tipoCobertura;
    private double areaTechada;

    public Estacionamiento(int idInmueble, String numero, String subterraneo, String tipoCobertura, double areaTechada) {
        this.idInmueble = idInmueble;
        this.numero = numero;
        this.subterraneo = subterraneo;
        this.tipoCobertura = tipoCobertura;
        this.areaTechada = areaTechada;
    }

    public int getIdInmueble() { return idInmueble; }
    public void setIdInmueble(int idInmueble) { this.idInmueble = idInmueble; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getSubterraneo() { return subterraneo; }
    public void setSubterraneo(String subterraneo) { this.subterraneo = subterraneo; }
    public String getTipoCobertura() { return tipoCobertura; }
    public void setTipoCobertura(String tipoCobertura) { this.tipoCobertura = tipoCobertura; }
    public double getAreaTechada() { return areaTechada; }
    public void setAreaTechada(double areaTechada) { this.areaTechada = areaTechada; }

    @Override
    public String toString() {
        return "Estacionamiento{" + "idInmueble=" + idInmueble + ", numero='" + numero + "'}";
    }

    public static void insertar(Connection conn, Estacionamiento estacionamiento) {
        String sql = "INSERT INTO Estacionamiento (IDINMUEBLE, NUMERO, SUBTERRANEO, TIPOCOBERTURA, AREATECHADA) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, estacionamiento.getIdInmueble());
            pstmt.setString(2, estacionamiento.getNumero());
            pstmt.setString(3, estacionamiento.getSubterraneo());
            pstmt.setString(4, estacionamiento.getTipoCobertura());
            pstmt.setDouble(5, estacionamiento.getAreaTechada());
            pstmt.executeUpdate();
            System.out.println("Nuevo estacionamiento insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar el estacionamiento: " + e.getMessage());
        }
    }

    public static Estacionamiento buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Estacionamiento WHERE IDINMUEBLE = ?";
        Estacionamiento estacionamiento = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    estacionamiento = new Estacionamiento(
                        rs.getInt("IDINMUEBLE"),
                        rs.getString("NUMERO"),
                        rs.getString("SUBTERRANEO"),
                        rs.getString("TIPOCOBERTURA"),
                        rs.getDouble("AREATECHADA")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el estacionamiento: " + e.getMessage());
        }
        return estacionamiento;
    }

    public static List<Estacionamiento> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Estacionamiento ORDER BY IDINMUEBLE";
        List<Estacionamiento> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Estacionamiento(
                    rs.getInt("IDINMUEBLE"),
                    rs.getString("NUMERO"),
                    rs.getString("SUBTERRANEO"),
                    rs.getString("TIPOCOBERTURA"),
                    rs.getDouble("AREATECHADA")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los estacionamientos: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Estacionamiento WHERE IDINMUEBLE = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Estacionamiento con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún estacionamiento con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el estacionamiento: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Estacionamiento estacionamiento) {
        String sql = "UPDATE Estacionamiento SET NUMERO = ?, SUBTERRANEO = ?, TIPOCOBERTURA = ?, AREATECHADA = ? WHERE IDINMUEBLE = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, estacionamiento.getNumero());
            pstmt.setString(2, estacionamiento.getSubterraneo());
            pstmt.setString(3, estacionamiento.getTipoCobertura());
            pstmt.setDouble(4, estacionamiento.getAreaTechada());
            pstmt.setInt(5, estacionamiento.getIdInmueble());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Estacionamiento actualizado correctamente.");
            } else {
                System.out.println("No se encontró un estacionamiento con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estacionamiento: " + e.getMessage());
        }
    }
}