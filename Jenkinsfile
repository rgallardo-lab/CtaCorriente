// Jenkinsfile (Declarative Pipeline)
pipeline {
    // 1. Configuración de Agente: Ejecutar en cualquier nodo disponible
    agent any
    
    environment {
        // Variable Maven HOME configurada en tu Jenkins Global Tool Configuration
        MAVEN_HOME = tool 'Maven_3.9' // Usar el nombre que le diste a la instalación en Herramientas Globales
        // Ruta al repositorio Artifactory
        ARTIFACTORY_URL = 'http://localhost:8081/artifactory'
        // Identificador de la instancia configurada globalmente en Jenkins
        ARTIFACTORY_SERVER_ID = 'MyConstruction_Artifactory_ID' 
        // Repositorio de destino
        ARTIFACTORY_REPO = 'maven-releases-local'
        // Credenciales Artifactory (Usaremos ID Global en el script)
        ARTIFACTORY_CREDS = 'admin-artifactory-creds' // *Debes configurar esta ID en Jenkins Credenciales*
    }
    
    stages {
        // 3.1 Etapa 1: Preparación del proyecto e integración con Repositorio remoto
        stage('SCM Checkout') {
            steps {
                echo '=== 1. OBTENIENDO CÓDIGO DE GITHUB ==='
                // Obtiene la última versión del código de GitHub (ya configurado en el Job)
                git url: 'https://github.com/rgallardo-lab/MyConstruction-Automation.git', branch: 'master'
            }
        }
        
        // 3.2 Etapa 2: Construcción del proyecto (.WAR)
        stage('BUILD ARTIFACT') {
            steps {
                echo '=== 2. COMPILANDO ARTEFACTO WAR ==='
                sh "${MAVEN_HOME}/bin/mvn clean package -Dmaven.test.skip=true"
                // El .war se genera en target/CtaCorriente-1.0-SNAPSHOT.war
            }
        }
        
        // 3.3 Etapa 3: Pruebas (JUnit y Cucumber)
        stage('TEST EXECUTION') {
            steps {
                echo '=== 3. EJECUTANDO PRUEBAS UNITARIAS Y AUTOMATIZADAS ==='
                // Ejecuta las pruebas JUnit y Cucumber (genera reportes en XML/HTML)
                sh "${MAVEN_HOME}/bin/mvn test"
                // Guarda los resultados de las pruebas en archivo XML (Requisito)
                junit 'target/surefire-reports/*.xml' 
            }
        }

        // 4. Gestión de Archivos y Trazabilidad (Implica Artifactory - Requisito 5)
        stage('DEPLOY TO ARTIFACTORY') {
            steps {
                echo '=== 4. DESPLIEGUE Y GESTIÓN EN ARTIFACTORY ==='
                // Utiliza la funcionalidad nativa del plugin de Artifactory para Maven
                // Esta es la forma correcta de integrar los Puntos 5 y 8
                withArtifactory(serverId: ARTIFACTORY_SERVER_ID, username: 'admin', password: 'tu_password_db') {
                    // Despliega el artefacto .war al repositorio maven-releases-local
                    deployer.deployArtifacts(
                        configuration: [
                            deployer: [
                                id: ARTIFACTORY_SERVER_ID,
                                url: ARTIFACTORY_URL
                            ]
                        ],
                        artifacts: [
                            // Ruta local del .war y ruta de destino Maven en Artifactory
                            [path: "target/CtaCorriente-1.0-SNAPSHOT.war", targetPath: "com/platinum/CtaCorriente/1.0-SNAPSHOT/CtaCorriente-1.0-SNAPSHOT.war"]
                        ],
                        buildName: 'BancoPlatinum-Pipeline',
                        buildNumber: env.BUILD_NUMBER
                    )
                }
                // Evidenciar artefacto generado después de compilación (visible en Artifactory)
                echo "Artefacto CtaCorriente-1.0-SNAPSHOT.war implementado en Artifactory."
            }
        }
    }
}