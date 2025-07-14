package Ventas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la entidad Cliente. Contiene los datos del modelo y los
 * métodos estáticos para interactuar con la base de datos.
 */
public class Cliente {

    // --- Atributos que coinciden con la tabla CLIENTES ---
    private long idCliente;
    private String inmuebleInteresado;
    private String interesProyecto;
    private String estadoDeAprobacion;
    private double montoAprobado;
    private int plazoMeses;
    private long idLead; // Clave Foránea

    // --- Constructor ---
    public Cliente(long idCliente, String inmuebleInteresado, String interesProyecto, String estadoDeAprobacion, double montoAprobado, int plazoMeses, long idLead) {
        this.idCliente = idCliente;
        this.inmuebleInteresado = inmuebleInteresado;
        this.interesProyecto = interesProyecto;
        this.estadoDeAprobacion = estadoDeAprobacion;
        this.montoAprobado = montoAprobado;
        this.plazoMeses = plazoMeses;
        this.idLead = idLead;
    }

    // --- Getters y Setters ---
    public long getIdCliente() { return idCliente; }
    public void setIdCliente(long idCliente) { this.idCliente = idCliente; }
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
    public long getIdLead() { return idLead; }
    public void setIdLead(long idLead) { this.idLead = idLead; }

    @Override
    public String toString() {
        return "Cliente{" + "idCliente=" + idCliente + ", estadoDeAprobacion='" + estadoDeAprobacion + "'}";
    }

    // =====================================================================
    // MÉTODOS DE ACCESO A DATOS (DAO)
    // =====================================================================

    public static void insertar(Connection conn, Cliente cliente) {
        String sql = "INSERT INTO Clientes (IDCLIENTE, INMUEBLEINTERESADO, INTERESPROYECTO, ESTADODEAPROBACION, MONTOAPROBADO, PLAZOMESES, IDLEAD) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, cliente.getIdCliente());
            pstmt.setString(2, cliente.getInmuebleInteresado());
            pstmt.setString(3, cliente.getInteresProyecto());
            pstmt.setString(4, cliente.getEstadoDeAprobacion());
            pstmt.setDouble(5, cliente.getMontoAprobado());
            pstmt.setInt(6, cliente.getPlazoMeses());
            pstmt.setLong(7, cliente.getIdLead());
            pstmt.executeUpdate();
            System.out.println("✅ Nuevo cliente insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar el cliente: " + e.getMessage());
        }
    }

    public static Cliente buscarPorId(Connection conn, long id) {
        String sql = "SELECT * FROM Clientes WHERE IDCLIENTE = ?";
        Cliente cliente = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                        rs.getLong("IDCLIENTE"),
                        rs.getString("INMUEBLEINTERESADO"),
                        rs.getString("INTERESPROYECTO"),
                        rs.getString("ESTADODEAPROBACION"),
                        rs.getDouble("MONTOAPROBADO"),
                        rs.getInt("PLAZOMESES"),
                        rs.getLong("IDLEAD")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar el cliente: " + e.getMessage());
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
                    rs.getLong("IDCLIENTE"),
                    rs.getString("INMUEBLEINTERESADO"),
                    rs.getString("INTERESPROYECTO"),
                    rs.getString("ESTADODEAPROBACION"),
                    rs.getDouble("MONTOAPROBADO"),
                    rs.getInt("PLAZOMESES"),
                    rs.getLong("IDLEAD")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los clientes: " + e.getMessage());
        }
        return lista;
    }

    public static void eliminar(Connection conn, long id) {
        String sql = "DELETE FROM Clientes WHERE IDCLIENTE = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Cliente con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("ℹ️ No se encontró ningún cliente con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el cliente: " + e.getMessage());
        }
    }
    
       
}