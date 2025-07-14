/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventas;

import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Vendedor{
    private long idVendedor;
    private String nombre;
    private String dni;
    private String email;
    private String telefono;

    public Vendedor() {
    } 

    public Vendedor(long idVendedor, String nombre, String dni, String email, String telefono) {
        this.idVendedor = idVendedor;
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
    }

    public long getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(long idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return dni;
    }

    public void setApellido(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    

    @Override
    public String toString() {
        return "Vendedor{" + "idVendedor=" + idVendedor + ", nombre='" + nombre + '\'' + ", dni='" + dni + '\'' + '}';
    }
    
    public static void insertarVendedor(Connection conn, Vendedor vendedor) {
    String sql = "INSERT INTO Vendedor (IDVENDEDOR, NOMBREVENDEDOR, DNI, EMAIL, TELEFONO) VALUES (?, ?, ?, ?, ?)";
    
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        // Asignamos los valores a los '?' en orden
        pstmt.setLong(1, vendedor.getIdVendedor());
        pstmt.setString(2, vendedor.getNombre());
        pstmt.setString(3, vendedor.getApellido());
        pstmt.setString(4, vendedor.getEmail());
        pstmt.setString(5, vendedor.getTelefono());
        
        pstmt.executeUpdate(); // Ejecuta la inserción
        System.out.println("✅ Vendedor insertado correctamente.");
        
    } catch (SQLException e) {
        System.err.println("❌ Error al insertar vendedor: " + e.getMessage());
    }
}
    
    
    
    public static Vendedor obtenerVendedorPorId(Connection conn, long id) {
    String sql = "SELECT * FROM Vendedor WHERE idVendedor = ?";
    Vendedor vendedor = null;
    
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setLong(1, id);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) { // Si se encontró un resultado
                vendedor = new Vendedor(
                    rs.getLong("idVendedor"),
                    rs.getString("nombre"),
                    rs.getString("dni"),
                    rs.getString("email"),
                    rs.getString("telefono")
                );
            }
        }
    } catch (SQLException e) {
        System.err.println("❌ Error al obtener vendedor: " + e.getMessage());
    }
    return vendedor;
}
    
    
    
    public static List<Vendedor> obtenerTodosLosVendedores(Connection conn) {
    String sql = "SELECT * FROM Vendedor";
    List<Vendedor> listaVendedores = new ArrayList<>();
    
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        while (rs.next()) { // Itera mientras haya resultados
            Vendedor vendedor = new Vendedor(
                rs.getLong("idVendedor"),
                rs.getString("nombre"),
                rs.getString("dni"),
                rs.getString("email"),
                rs.getString("telefono")
            );
            listaVendedores.add(vendedor);
        }
    } catch (SQLException e) {
        System.err.println("❌ Error al obtener todos los vendedores: " + e.getMessage());
    }
    return listaVendedores;
}
}    

