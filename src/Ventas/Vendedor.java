package Ventas;

import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Vendedor {
    private int idVendedor;
    private String nombre;
    private String dni;
    private String email;
    private String telefono;

    public Vendedor() {
    }

    public Vendedor(int idVendedor, String nombre, String dni, String email, String telefono) {
        this.idVendedor = idVendedor;
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
    }

    public int getIdVendedor() { return idVendedor; }
    public void setIdVendedor(int idVendedor) { this.idVendedor = idVendedor;   }
    public String getNombre() { return nombre;  }
    public void setNombre(String nombre) {  this.nombre = nombre;   }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni;}
    public String getEmail() { return email;   }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) {  this.telefono = telefono;   }

    @Override
    public String toString() {
        return "Vendedor{" + "idVendedor=" + idVendedor + ", nombre='" + nombre + '\'' + ", dni='" + dni + '\'' + '}';
    }

    public static void insertarVendedor(Connection conn, Vendedor vendedor) {
        String sql = "INSERT INTO Vendedor (IDVENDEDOR, NOMBREVENDEDOR, DNI, EMAIL, TELEFONO) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vendedor.getIdVendedor());
            pstmt.setString(2, vendedor.getNombre());
            pstmt.setString(3, vendedor.getDni());
            pstmt.setString(4, vendedor.getEmail());
            pstmt.setString(5, vendedor.getTelefono());
            pstmt.executeUpdate();
            System.out.println("Vendedor insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar vendedor: " + e.getMessage());
        }
    }

    public static Vendedor buscarPorId(Connection conn, int id) {
        String sql = "SELECT * FROM Vendedor WHERE IDVENDEDOR = ?";
        Vendedor vendedor = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    vendedor = new Vendedor(
                        rs.getInt("IDVENDEDOR"),
                        rs.getString("NOMBREVENDEDOR"),
                        rs.getString("DNI"),
                        rs.getString("EMAIL"),
                        rs.getString("TELEFONO")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener vendedor: " + e.getMessage());
        }
        return vendedor;
    }

    public static List<Vendedor> mostrarTodos(Connection conn) {
        String sql = "SELECT * FROM Vendedor";
        List<Vendedor> listaVendedores = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vendedor vendedor = new Vendedor(
                    rs.getInt("IDVENDEDOR"),
                    rs.getString("NOMBREVENDEDOR"),
                    rs.getString("DNI"),
                    rs.getString("EMAIL"),
                    rs.getString("TELEFONO")
                );
                listaVendedores.add(vendedor);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los vendedores: " + e.getMessage());
        }
        return listaVendedores;
    }

    public static void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM Vendedor WHERE IDVENDEDOR = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Vendedor con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún vendedor con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el vendedor: " + e.getMessage());
        }
    }

    public static void actualizar(Connection conn, Vendedor vendedor) {
        String sql = "UPDATE Vendedor SET NOMBREVENDEDOR = ?, DNI = ?, EMAIL = ?, TELEFONO = ? WHERE IDVENDEDOR = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, vendedor.getNombre());
            pstmt.setString(2, vendedor.getDni());
            pstmt.setString(3, vendedor.getEmail());
            pstmt.setString(4, vendedor.getTelefono());
            pstmt.setInt(5, vendedor.getIdVendedor());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Vendedor actualizado correctamente.");
            } else {
                System.out.println("No se encontró ningún vendedor con ese ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el vendedor: " + e.getMessage());
        }
    }
}