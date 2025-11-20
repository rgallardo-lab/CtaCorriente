package com.platinum.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class LoginSteps {

    // Variable global para el navegador (WebDriver)
    private WebDriver driver;
    private final String BASE_URL = "http://localhost:8080/CtaCorriente/login.jsp";
    // Nota: Necesitas configurar la ruta al ChromeDriver en tu sistema
    
    // --- GIVEN ---
    @Given("el ejecutivo está en la página de login del Banco Platinum")
    public void elEjecutivoEstaEnLaPaginaDeLogin() {
        // Asume que Tomcat ya está corriendo y el WAR está desplegado
        System.setProperty("webdriver.chrome.driver", "ruta/a/tu/chromedriver.exe"); 
        // Reemplazar la ruta con la ubicación real de tu ChromeDriver
        
        driver = new ChromeDriver();
        driver.get(BASE_URL);
    }
    
    // --- WHEN ---
    @When("el ejecutivo ingresa {string} y {string}")
    public void elEjecutivoIngresaCredenciales(String rut, String password) {
        // Ingresa el RUT en el campo con name="rut"
        driver.findElement(By.name("rut")).sendKeys(rut);
        // Ingresa la Contraseña en el campo con name="password"
        driver.findElement(By.name("password")).sendKeys(password);
    }
    
    @When("hace click en el botón {string}")
    public void haceClickEnElBoton(String boton) {
        // Asume que el botón submit no tiene un nombre, por lo que buscamos por tag
        driver.findElement(By.xpath("//input[@value='" + boton + "']")).click();
    }

    // --- THEN (Casos Exitosos y Fallidos) ---
    @Then("el sistema debe mostrar la página {string}")
    public void elSistemaDebeMostrarLaPagina(String titulo) {
        // Verifica si el título de la página (o un elemento clave) contiene el texto
        assertTrue("El login no fue exitoso.", driver.getTitle().contains(titulo));
        driver.quit(); // Cierra el navegador al finalizar la prueba exitosa
    }

    @Then("el sistema debe mostrar el mensaje {string}")
    public void elSistemaDebeMostrarElMensaje(String mensaje) {
        // En tu LoginEjecutivoServlet, este mensaje se envía de vuelta a login.jsp
        // Verificamos si la página contiene el mensaje de error.
        String pageSource = driver.getPageSource();
        assertTrue("No se encontró el mensaje de error esperado.", pageSource.contains(mensaje));
        driver.quit(); // Cierra el navegador al finalizar la prueba fallida
    }
}