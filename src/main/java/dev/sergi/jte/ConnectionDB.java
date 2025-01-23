package dev.sergi.jte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import dev.sergi.jte.classes.Cliente;
import dev.sergi.jte.classes.Envio;
import dev.sergi.jte.classes.Repartidor;
import dev.sergi.jte.classes.Ubicacion;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class ConnectionDB {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ConnectionDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Repartidor> getRepartidores() {
        String sql = "SELECT * FROM Repartidor";

        // Usamos el JdbcTemplate para ejecutar la consulta y mapear los resultados
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String telefono = rs.getString("telefono");
            return new Repartidor(id, nombre, telefono);
        });
    }

    public List<Envio> getEnvios() {
        String sql = "SELECT * FROM Envio";

        // Usamos el JdbcTemplate para ejecutar la consulta y mapear los resultados
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            String direccion = rs.getString("direccion");
            Date fecha_envio =  rs.getDate("fecha");
            return new Envio(direccion, fecha_envio);
        });
    }

    public boolean existeCalle(String direccion) {
        String sql = "SELECT COUNT(*) FROM ubicacion WHERE direccion = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, direccion);
        return count != null && count > 0;
    }

    public Boolean insertCoordenadas(String direccion, float latitud, float longitud) {
        String sql = "INSERT INTO ubicacion (direccion, latitud, longitud) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, direccion, latitud, longitud) > 0;
    }

    public List<Ubicacion> getUbicaciones() {
        String sql = "SELECT * FROM ubicacion";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            String direccion = rs.getString("direccion");
            float latitud = rs.getFloat("latitud");
            float longitud = rs.getFloat("longitud");
            return new Ubicacion(direccion, latitud, longitud);
        });
    }

    public List<Envio> getTabla() {
        
        String sql = """
                    SELECT 
                    c.nombre AS cliente,
                    e.direccion AS direccion,
                    e.estado AS estado,
                    r.nombre AS repartidor,
                    r.color AS color,
                    u.latitud AS latitud,
                    u.longitud AS longitud
                    FROM Envio e
                    JOIN Ubicacion u ON
                    e.direccion = u.direccion
                    JOIN Repartidor r ON
                    r.id = e.repartidor_id
                    JOIN Factura f ON
                    f.id = e.factura_id
                    JOIN Cliente c ON
                    c.id = f.cliente_id
                    WHERE e.fecha = CAST(GETDATE() AS DATE)
                    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            String direccion_envio = rs.getString("direccion");
            String nombre_cliente = rs.getString("cliente");
            String nombre_repartidor = rs.getString("repartidor");
            String color_repartidor = rs.getString("color");
            double latitud = rs.getDouble("latitud");
            double longitud = rs.getDouble("longitud");
            String estado = rs.getString("estado");

            Cliente cliente = new Cliente(nombre_cliente);
            Repartidor repartidor = new Repartidor(nombre_repartidor, color_repartidor);
            Ubicacion ubicacion = new Ubicacion(latitud, longitud);

            return new Envio(direccion_envio, cliente, repartidor, ubicacion, estado);
        });            
    }
}
