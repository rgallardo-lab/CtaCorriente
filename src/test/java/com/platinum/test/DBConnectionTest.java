package com.platinum.test;

import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import com.platinum.db.DBConnection; // Importar la clase creada

public class DBConnectionTest {
    @Test
    public void testDatabaseConnection() {
        Connection conn = null;
        try {
            // Intenta obtener la conexión usando la clase de conexión
            conn = DBConnection.getConnection(); 
            assertNotNull("La conexión a la base de datos no debería ser nula.", conn);
            
        } catch (SQLException e) {
            // Si hay un error, la prueba falla
            fail("Falló la prueba de conexión a la DB. Verifica el puerto/credenciales: " + e.getMessage());
        } finally {
            // Asegura que la conexión se cierre
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // Ignorar
                }
            }
        }
    }
}
