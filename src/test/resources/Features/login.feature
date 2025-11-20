Feature: Login y Pruebas de Ejecutivo en Banco Platinum

  Scenario: 4.1 Ingreso exitoso de Ejecutivo (Datos almacenados en BD)
    Given el ejecutivo está en la página de login del Banco Platinum
    When el ejecutivo ingresa "11111111-1" y "password_valido"
    And hace click en el botón "Ingresar"
    Then el sistema debe mostrar la página "Bienvenido, Ejecutivo"

  Scenario: 4.2 Ingreso fallido por credenciales erróneas
    Given el ejecutivo está en la página de login del Banco Platinum
    When el ejecutivo ingresa "99999999-9" y "clave_erronea"
    And hace click en el botón "Ingresar"
    Then el sistema debe mostrar el mensaje "Credenciales inválidas"