package Ventas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private int idCliente;
    private String inmuebleInteresado;
    private String interesProyecto;
    private String estadoDeAprobacion;
    private double montoAprobado;
    private int plazoMeses;
    private int idLead; 

    public Cliente(int idCliente, String inmuebleInteresado, String interesProyecto, String estadoDeAprobacion, double montoAprobado, int plazoMeses, int idLead) {
        this.idCliente = idCliente;
        this.inmuebleInteresado = inmuebleInteresado;
        this.interesProyecto = interesProyecto;
        this.estadoDeAprobacion = estadoDeAprobacion;
        this.montoAprobado = montoAprobado;
        this.plazoMeses = plazoMeses;
        this.idLead = idLead;
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public String getInmuebleInteresado() { return inmuebleInteresado; }
    public void setInmuebleInteresado(String inmuebleInteresado) { this.inmuebleInteresado = inmuebleInteresado; }
    public String getInteresProyecto() { return interesProyecto; }
    public void setInteresProyecto(String interesProyecto) { this.interesProyecto = interesProyecto; }
    public String getEstadoDeAprobacion() { return estadoDeAprobacion; }
    public void setEstadoDeAprobacion(String estadoDeAprobacion) { this.estadoDeAprobacion = estadoDeAprobacion; }
    public double getMontoAprobado() { return montoAprobado; }
    public void setMontoAprobado(double montoAprobado) { this.montoAprobado = montoAprobado; }
    public int getPlazoMeses() { return plazoMeses; }
    public void setPlazoMeses(int plazoMeses) { this.plazoMeses = plazoMeses; }
    public int getIdLead() { return idLead; }
    public void setIdLead(int idLead) { this.idLead = idLead; }

    @Override
    public String toString() {
        return "Cliente{" + "idCliente=" + idCliente + ", estadoDeAprobacion='" + estadoDeAprobacion + "'}";
    }

    public static void insertar(Connection conn, Cliente cliente) {
        String sql = "INSERT INTO Clientes (IDCLIENTE, INMUEBLEINTERESADO, INTERESPROYECTO, ESTADODEAPROBACION, MONTOAPROBADO, PLAZOMESES, IDLEAD) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cliente.getIdCliente());
            pstmt.setString(2, cliente.getInmuebleInteresado());
            pstmt.setString(3, cliente.getInteresProyecto());
            pstmt.setString(4, cliente.getEstadoDeAprobacion());
            pstmt.setDouble(5, cliente.getMontoAprobado());
            pstmt.setInt(6, cliente.getPlazoMeses());
            pstmt.setInt(7, cliente.getIdLead());
            pstmt.executeUpdate();
            System.out.println("Nuevo cliente insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar el cliente: " + e.getMessage());
        }
    }

    public static Cliente buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Clientes WHERE IDCLIENTE = ?";
        Cliente cliente = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                        rs.getInt("IDCLIENTE"),
                        rs.getString("INMUEBLEINTERESADO"),
                        rs.getString("INTERESPROYECTO"),
                        rs.getString("ESTADODEAPROBACION"),
                        rs.getDouble("MONTOAPROBADO"),
                        rs.getInt("PLAZOMESES"),
                        rs.getInt("IDLEAD")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el cliente: " + e.getMessage());
        }
        return cliente;
    }

    public static List<Cliente> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Clientes ORDER BY IDCLIENTE";
        List<Cliente> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Cliente(
                    rs.getInt("IDCLIENTE"),
                    rs.getString("INMUEBLEINTERESADO"),
                    rs.getString("INTERESPROYECTO"),
                    rs.getString("ESTADODEAPROBACION"),
                    rs.getDouble("MONTOAPROBADO"),
                    rs.getInt("PLAZOMESES"),
                    rs.getInt("IDLEAD")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los clientes: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Clientes WHERE IDCLIENTE = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Cliente con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún cliente con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el cliente: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Cliente cliente) {
        String sql = "UPDATE Clientes SET INMUEBLEINTERESADO = ?, INTERESPROYECTO = ?, ESTADODEAPROBACION = ?, MONTOAPROBADO = ?, PLAZOMESES = ?, IDLEAD = ? WHERE IDCLIENTE = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getInmuebleInteresado());
            pstmt.setString(2, cliente.getInteresProyecto());
            pstmt.setString(3, cliente.getEstadoDeAprobacion());
            pstmt.setDouble(4, cliente.getMontoAprobado());
            pstmt.setInt(5, cliente.getPlazoMeses());
            pstmt.setInt(6, cliente.getIdLead());
            pstmt.setInt(7, cliente.getIdCliente());
            int filasActualizadas = pstmt.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Cliente actualizado correctamente.");
            } else {
                System.out.println("No se encontró ningún cliente con ese ID para actualizar.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el cliente: " + e.getMessage());
        }
    }
}
