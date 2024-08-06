import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

def zipDir(dirPath, zipFilePath) {
    def file = new File(zipFilePath)
    file.withOutputStream { os ->
        def zos = new ZipOutputStream(os)
        new File(dirPath).eachFileRecurse { f ->
            if (!f.isDirectory()) {
                def relativePath = dirPath.toURI().relativize(f.toURI()).path
                zos.putNextEntry(new ZipEntry(relativePath))
                zos << f.bytes
                zos.closeEntry()
            }
        }
        zos.close()
    }
}

pipeline {
    agent any
    tools {
        maven 'Maven 3.9.0'
        jdk 'JDK 17'
    }
    stages {
        stage('Test') {
            steps {
                sh '''
                    cd dummy-service
                    mvn test
//                     zip -r dummy-service-jacoco-report.zip /dummy-service/target/site/jacoco
                '''
                script {
                    zipDir('dummy-service/target/site/jacoco', 'dummy-service-jacoco-report.zip')
                }
            }
        }

        stage('Build jar file') {
            steps {
                sh 'cd dummy-service && mvn install -DskipTests'
            }
        }

//         stage('Packaging/Pushing image') {
//             steps {
//                 withDockerRegistry(credentialsId: 'dockerhub-credential', url: 'https://index.docker.io/v1/') {
//                     sh 'cd dummy-service && docker build --platform linux/amd64 --build-arg SERVICE_NAME=dummy-service -t quangduong2903/genesis-dummy-service .'
//                     sh 'docker push quangduong2903/genesis-dummy-service'
//                 }
//             }
//         }
    }
    post {
        always {
            emailext (
                to: 'quangduongptsc@gmail.com',
                subject: "${env.JOB_NAME} - Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}!",
                body: """
                    <p>Build finished. Please check the JaCoCo coverage report.</p>
                    <p>${env.JOB_NAME} - Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}:</p>
                    <p>Check console output at <a href="${env.BUILD_URL}">${env.BUILD_URL}</a> to view the results.</p>
                """,
                mimeType: 'text/html',
                attachmentsPattern: 'dummy-service-jacoco-report.zip'
            )
            cleanWs()
        }
    }
}