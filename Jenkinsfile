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
                '''
//                 zip(zipFile: 'dummy-service-jacoco-report.zip', dir: 'dummy-service/target/site/jacoco', archive: true)
            }
            post {
                always {
                    junit 'dummy-service/target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('Sonar') {
                    sh '''
                        cd dummy-service
                        mvn clean verify sonar:sonar
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build jar file') {
            steps {
                sh '''
                    cd dummy-service
                    mvn clean install -DskipTests
                '''
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
//                 attachmentsPattern: 'dummy-service-jacoco-report.zip'
            )
            cleanWs()
        }
    }
}