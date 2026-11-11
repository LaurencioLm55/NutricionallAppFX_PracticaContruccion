package nutricionalappfx.modelo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Administra la conexión a la base de datos usando database.properties.
 *
 * @author lopez
 */
public class ConexionBD {

    private static final Properties propiedades = new Properties();

    static {
        cargarDatosProperties();
    }

    public static void cargarDatosProperties() {
        try (InputStream input = ConexionBD.class.getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new RuntimeException("Error: No se encontró el archivo database.properties");
            }
            propiedades.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName(propiedades.getProperty("db.driver"));
            return DriverManager.getConnection(
                    propiedades.getProperty("db.url"),
                    propiedades.getProperty("db.user"),
                    propiedades.getProperty("db.password"));
        } catch (ClassNotFoundException ex) {
            throw new SQLException("No se encontró el driver de MySQL: " + ex.getMessage());
        }
    }

}
