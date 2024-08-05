pipeline {
    agent any
    stages {
        stage('Build jar file') {
            steps {
                sh 'cd dummy-service'
                sh 'chmod +x ./mvnw'
                sh './mvnw clean install -DskipTests'
            }
        }

        stage('Packaging/Pushing image') {
            when {
                branch 'main'
            }
            steps {
                withDockerRegistry(credentialsId: 'dockerhub-credential', url: 'https://index.docker.io/v1/') {
                    sh 'cd dummy-service'
                    sh 'docker build --platform linux/amd64 --build-arg SERVICE_NAME=dummy-service -t quangduong2903/genesis-dummy-service .'
                    sh 'docker push quangduong2903/genesis-dummy-service'
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}