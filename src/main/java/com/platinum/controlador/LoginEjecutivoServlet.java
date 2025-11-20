package com.platinum.controlador;

import com.platinum.db.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Mapea la URL del formulario (action="LoginEjecutivoServlet")
@WebServlet("/LoginEjecutivoServlet")
public class LoginEjecutivoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Obtener parámetros del formulario
        String rut = request.getParameter("rut");
        String password = request.getParameter("password");
        
        Connection conn = null;
        try {
            // 2. Establecer conexión con la DB (utilizando la clase DBConnection)
            conn = DBConnection.getConnection();
            
            // NOTA IMPORTANTE: Para la tabla 'ejecutivo', la contraseña no está definida en el esquema 
            // del examen. Usaremos el RUT como una clave de validación simple por ahora, 
            // o asumiremos que el campo 'monto' en CtaCorriente es el password temporal.
            
            // Para fines de la prueba de examen, simplificaremos la validación:
            // Un ejecutivo es válido si su RUT existe en la tabla 'ejecutivo'.
            String sql = "SELECT nombre, departamento FROM ejecutivo WHERE rutEjecutivo = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, rut);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    
                    if (rs.next()) {
                        // 3. Login Exitoso
                        String nombreEjecutivo = rs.getString("nombre");
                        // Redirigir al dashboard o a la página de bienvenida
                        request.setAttribute("nombre", nombreEjecutivo);
                        request.getRequestDispatcher("/home.jsp").forward(request, response);
                    } else {
                        // 4. Login Fallido
                        request.setAttribute("error", "Credenciales inválidas o RUT no registrado.");
                        request.getRequestDispatcher("/login.jsp").forward(request, response);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error de servidor o de base de datos.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (Exception e) {}
            }
        }
    }
}