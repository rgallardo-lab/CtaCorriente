<!DOCTYPE html>
<html>
<head><title>Banco Platinum - Login</title></head>
<body>
    <h1>Acceso de Ejecutivos</h1>
    <form action="LoginEjecutivoServlet" method="POST">
        <label for="rut">RUT Ejecutivo:</label>
        <input type="text" id="rut" name="rut" required><br><br>
        
        <label for="password">Contraseña (Monto de prueba):</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <input type="submit" value="Ingresar">
    </form>
</body>
</html>