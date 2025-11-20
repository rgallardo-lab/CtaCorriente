package com.platinum.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/login.feature", // Ruta al archivo Gherkin
    glue = {"com.platinum.steps"}, // Ruta al paquete con los Step Definitions
    plugin = {"pretty", "html:target/cucumber-reports.html"}, // Genera un reporte HTML
    monochrome = true // Hace la salida de la consola más legible
)
public class CucumberRunner {
    // Esta clase queda vacía, solo sirve para ejecutar las pruebas.
}