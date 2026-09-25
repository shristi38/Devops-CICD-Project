pipeline {
    agent any 
    stages {
      stage('Build') {
        steps {
            sh 'mvn clean package'
        }
     }
      stage('Docker Build') {
        steps {
            sh 'docker build -t devops-cicd-java-app .'
        }
    }
      stage('Docker Push') {
        steps {
          withCredentials([usernamePassword(
            credentialsId: 'dockerhub-credentials',
            usernameVariable: 'DOCKER_USERNAME',
            passwordVariable: 'DOCKER_PASSWORD'
        )]) {
             sh '''
                echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                docker tag devops-cicd-java-app "$DOCKER_USERNAME/devops-cicd-java-app:latest"
                docker push "$DOCKER_USERNAME/devops-cicd-java-app:latest"
            '''
          }
        }
    }

    stage('Deploy') {
        steps {
            withCredentials([usernamePassword(
                credentialsId: 'dockerhub-credentials',
                usernameVariable: 'DOCKER_USERNAME',
                passwordVariable: 'DOCKER_PASSWORD'
            )]) {
                sh '''
                    docker pull "$DOCKER_USERNAME/devops-cicd-java-app:latest"

                    docker stop devops-cicd-app-container || true
                    docker rm devops-cicd-app-container || true

                    docker run -d \
                        --name devops-cicd-app-container \
                        -p 8081:8080 \
                        "$DOCKER_USERNAME/devops-cicd-java-app:latest"
                '''
            }
        }
    }

    }
}