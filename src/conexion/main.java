/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;


import Finanzas.Banco;
import Inmuebles.Constructora;
import Inmuebles.Departamento;
import Inmuebles.Inmueble;
import Inmuebles.Proyecto;
import Marketing.CampañaPublicitaria;
import Marketing.CanalEntrada;
import Ventas.Cliente;
import Ventas.Lead;
import Ventas.Venta;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;


public class main { 

public static void main(String[] args) {

    String dbUrl = "jdbc:oracle:thin:@localhost:1521:XE";
    String dbUser = "PROYECTO";
    String dbPassword = "PASSWORD";

    try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
        System.out.println("✅ ¡Conexión a Oracle exitosa!");
        // 1. Insertar un nuevo banco
        //Banco nuevoBanco = new Banco(20, "Banco Financiero de bolivia", "Av. Principal 123", "San Isidro", "20100047218");
        //Banco.insertar(connection, nuevoBanco);
        
        //Inmueble nuevoInmueble = new Inmueble(1, 85.5, 250000.00, "Disponible", "Departamento", 1);
        //Inmueble.insertar(connection, nuevoInmueble);
        
        //Inmueble nuevoInmueble1 = new Inmueble(2, 85.0, 240000.00, "Disponible", "Departamento", 1);
        //Inmueble.insertar(connection, nuevoInmueble1);
        
        //Inmueble nuevoInmueble2 = new Inmueble(3, 120.0, 310000.00, "Disponible", "Duplex", 1);
        //Inmueble.insertar(connection, nuevoInmueble2);
        
        //Inmueble.eliminar(connection, 6); 
        
        //Departamento nuevoDepto = new Departamento(1, 3, 2, 2, "SI");
        //Departamento.insertar(connection, nuevoDepto);
        
        //PROYECTOS
        //Proyecto nuevoProyecto = new Proyecto(1, "Residencial Sol Naciente", "Av. Las Palmas 123", java.sql.Date.valueOf("2025-01-01"), java.sql.Date.valueOf("2025-12-31"), "En ejecución", 1);
        //Proyecto.insertar(connection, nuevoProyecto);
        
        //Proyecto nuevoProyecto1 = new Proyecto(2, "Condominio Altos del Sur", "Calle Los Olivos 456", java.sql.Date.valueOf("2025-03-01"), java.sql.Date.valueOf("2026-02-28"), "Planificado", 2);
        //Proyecto.insertar(connection, nuevoProyecto1);
        
        //CONSTRUCTORAS
        //Constructora nuevaConstructora = new Constructora(1, "Constructora Andina", "20112233445", "Juan Pérez", "Calle Central 456", "987654321", "info@andina.com");
        //Constructora.insertar(connection, nuevaConstructora);
        
        //Constructora nuevaConstructora1 = new Constructora(2, "Grupo Constructor del Sur", "20994433221", "Laura Gómez", "Av. Industrial 789", "999888777", "contacto@gcsur.com");
        //Constructora.insertar(connection, nuevaConstructora1);

        //Constructora constructoraActualizada = new Constructora(1, "Constructora Luz", "20112233445", "Ana Torres", "Av. Reforma 999", "987123456", "contacto@andina.com");
        //Constructora.actualizar(connection, constructoraActualizada);
        
        //CLIENTE
        //Cliente nuevoCliente = new Cliente(1, "Dúplex 303", "Altos del Sur", "Aprobado", 250000.00, 24, 1);
        //Cliente.insertar(connection, nuevoCliente);
        
        //Cliente nuevoCliente2 = new Cliente(2, "Estacionamiento B-12", "Condominio Centro Urbano", "Pendiente", 15000.00, 12, 2);
        //Cliente.insertar(connection, nuevoCliente2);
        
        //Cliente nuevoCliente3 = new Cliente(3, "Departamento 502", "Residencial Las Palmeras", "Aprobado", 320000.00, 20, 3);
        //Cliente.insertar(connection, nuevoCliente3);
       
        //Lead nuevoLead = new Lead(1, "Lucía Mendoza", "lucia.mendoza@email.com", "987654321", "Interesado", 2);
        //Lead.insertar(connection, nuevoLead);
        
        //Lead nuevoLead2 = new Lead(2, "Diego Ramírez", "diego.ramirez@email.com", "912345678", "Contactado", 1);
        //Lead.insertar(connection, nuevoLead2);
        
        //Lead nuevoLead3 = new Lead(3, "María Torres", "maria.torres@email.com", "987321654", "Interesado", 2);
        //Lead.insertar(connection, nuevoLead3);  

        //CampañaPublicitaria nuevaCampañaPublicitaria = new CampañaPublicitaria(1, "Facebook Ads", "Digital", "Campaña de lanzamiento para Altos del Sur", "Activa");
        //CampañaPublicitaria.insertar(connection, nuevaCampañaPublicitaria); 
        
        //CanalEntrada nuevoCanalEntrada = new CanalEntrada(1, "Campaña Facebook Verano", java.sql.Date.valueOf("2025-01-01"), java.sql.Date.valueOf("2025-03-31"), 10000.00, "Activa", "Difusión en redes sociales", "Digital", 1);
        //CanalEntrada.insertar(connection, nuevoCanalEntrada);
        
        //CanalEntrada nuevoCanalEntrada2 = new CanalEntrada(2, "Anuncios Google Ads", java.sql.Date.valueOf("2025-02-01"), java.sql.Date.valueOf("2025-04-30"), 15000.00, "Activa", "Publicidad en buscadores", "Digital", 1);
        //CanalEntrada.insertar(connection, nuevoCanalEntrada2);  
        
        //VENTA
        //Venta nuevaVenta = new Venta(1, java.sql.Date.valueOf("2025-07-15"), 250000.00, "Pendiente", 1);
        //Venta.insertar(connection, nuevaVenta);
        
        //Venta nuevaVenta2 = new Venta(2, java.sql.Date.valueOf("2025-07-16"), 15000.00, "Pendiente", 2);
        //Venta.insertar(connection, nuevaVenta2);
        
        //Venta ventaInvalida = new Venta(3, java.sql.Date.valueOf("2025-10-20"), 5000.00, "Pendiente", 3);
        //Venta.insertar(connection, ventaInvalida);
        
        
    } catch (SQLException e) {
        System.err.println("❌ Error durante la prueba: " + e.getMessage());
        e.printStackTrace();
    }
}
}
