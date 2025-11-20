// Jenkinsfile (Declarative Pipeline - Versión FINAL Definitiva)
pipeline {
    agent any
    
    // Configuración de MAVEN_HOME
    environment {
        MAVEN_HOME = tool 'Maven_3.9'
        
        // Configuración de rutas y credenciales (para mayor seguridad y claridad)
        ARTIFACT_NAME = "CtaCorriente-0.0.1-SNAPSHOT.war"
        TARGET_FILE = "target/${ARTIFACT_NAME}"
        
        // URL de despliegue en Artifactory
        ARTIFACTORY_URI = "http://localhost:8081/artifactory/maven-releases-platinum/com/platinum/CtaCorriente/0.0.1-SNAPSHOT/${ARTIFACT_NAME}"
        
        // Credenciales claras
        ADMIN_USER = 'admin'
        ADMIN_PASS = 'Artifactory' // Usar la clave de tu instancia
    }
    
    stages {
        
        stage('Source & Preparation') {
            steps {
                echo '=== 1. OBTENIENDO CÓDIGO DE GITHUB ==='
            }
        }
        
        stage('Build Artifact (WAR)') {
            steps {
                echo '=== 2. COMPILANDO ARTEFACTO WAR ==='
                bat "${MAVEN_HOME}\\bin\\mvn clean package"
            }
        }
        
        stage('Test Execution & Reporting') {
            steps {
                echo '=== 3. EJECUTANDO PRUEBAS Y GENERANDO XML ==='
                // Ejecuta las pruebas JUnit y Cucumber
                bat "${MAVEN_HOME}\\bin\\mvn test"
                
                // Publicar resultados de pruebas (Requisito 56)
                junit 'target/surefire-reports/*.xml' 
            }
        }

stage('Deploy to Artifactory') {
            steps {
                echo "=== SUBIENDO ARTEFACTO A ARTIFACTORY ==="
                
                powershell """
                \$Repo = 'platinum-deploy'
                \$GroupPath = 'com/platinum/CtaCorriente/0.0.1-SNAPSHOT'
                \$FileName = 'CtaCorriente-0.0.1-SNAPSHOT.war'

                # Ruta del archivo generado por Maven
                \$FilePath = "C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\BancoPlatinum-Pipeline\\target\\\$FileName"

                # URL destino en Artifactory
                \$Url = "http://localhost:8081/artifactory/\$Repo/\$GroupPath/\$FileName"

                # Credenciales en Base64 (admin:Artifactory)
                \$Auth = "Basic YWRtaW46QXJ0aWZhY3Rvcnk="

                Write-Host "Subiendo archivo a: \$Url"
                Write-Host "Archivo local: \$FilePath"

                Invoke-WebRequest -Method PUT -Uri \$Url -InFile \$FilePath `
                    -Headers @{ Authorization = \$Auth } `
                    -UseBasicParsing

                Write-Host "=== DEPLOY COMPLETADO ==="
                """
            }
        }
    }
}

// 31