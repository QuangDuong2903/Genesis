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
                    mvn clean test

                '''
            }
        }

        stage('Build jar file') {
            steps {
                sh 'cd dummy-service && mvn clean install -DskipTests'
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
                subject: "Build ${currentBuild.fullDisplayName} Finished",
                body: """<p>Build finished. Please check the JaCoCo coverage report.</p>""",
                attachmentsPattern: '**/target/site/jacoco/*'
            )
            cleanWs()
        }
    }
}