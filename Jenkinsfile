// Jenkinsfile (Declarative Pipeline - Solución Corregida)
pipeline {
    agent any
    
    // Variables de Entorno Globales
    environment {
        // Asume que 'Maven_3.9' está en Global Tool Configuration
        MAVEN_HOME = tool 'Maven_3.9' 
        
        // Configuración de Artifactory
        ARTIFACTORY_SERVER_ID = 'BancoPlatinum_Artifactory_ID' 
        ARTIFACTORY_REPO = 'maven-releases-platinum' 
        BUILD_NAME = 'BancoPlatinum-Pipeline'
    }
    
    stages {
        // Etapa 1: Source & Preparation
        stage('Source & Preparation') {
            steps {
                echo '=== 1. OBTENIENDO CÓDIGO DE GITHUB ==='
                // Descarga el código
                git url: 'https://github.com/rgallardo-lab/CtaCorriente.git', branch: 'master' 
            }
        }
        
        // Etapa 2: Construcción, Pruebas (Unificada) y Despliegue con Plugin Artifactory
        stage('Build, Test & Deploy') {
            steps {
                // Usamos el bloque 'script' para el despliegue avanzado
                script {
                    echo '=== 2. CONFIGURANDO LA INTEGRACIÓN CON ARTIFACTORY ==='
                    
                    // 1. Obtener la instancia de Artifactory (rtServer es un objeto Groovy)
                    def server = Artifactory.server(ARTIFACTORY_SERVER_ID)
                    
                    // 2. Ejecutar Maven, correr las pruebas y automáticamente desplegar el artefacto.
                    // Usamos el wrapper rtMaven para integrar Maven y Artifactory
                    // Nota: Las pruebas de Surefire/Cucumber se ejecutarán aquí.
                    def buildInfo = server.runMaven (
                        pom: 'pom.xml',
                        goals: 'clean install', // goals: 'clean install' corre todas las pruebas (JUnit/Cucumber)
                        resolverId: ARTIFACTORY_SERVER_ID, // Uso para resolver dependencias
                        deployerId: ARTIFACTORY_SERVER_ID, // Uso para el despliegue
                        repo: ARTIFACTORY_REPO, // Repositorio de destino
                        buildName: BUILD_NAME,
                        buildNumber: env.BUILD_NUMBER
                    )
                    
                    // 3. Publicar los resultados de las pruebas XML (Requisito 56)
                    echo '=== 3. PUBLICANDO RESULTADOS DE PRUEBAS JUNIT/CUCUMBER ==='
                    junit 'target/surefire-reports/*.xml'
                    
                    // 4. Publicar la información de la compilación para trazabilidad (Punto 57)
                    echo '=== 4. PUBLICANDO BUILD INFO EN ARTIFACTORY ==='
                    server.publishBuildInfo(buildInfo)
                }
            }
        }
    }
}