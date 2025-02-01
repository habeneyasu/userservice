pipline {
    agent any

    environment{
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
        DOCKER_IMAGE = ""
    }

    stages {

        stage('Checkout') {
            steps {
                git branch : './main',url : 'https://github.com/habeneyasu/userservice.git'
            }
        }

        stage('Build') {
            steps {
                sh  './mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh './mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mkdir -p build && cp target/*.jar build/app.jar'
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    withDockerRegistry([credentialsId: 'docker-hub-credentials', url: '']) {
                        sh "docker build -t ${DOCKER_IMAGE}:latest ."
                        sh "docker push ${DOCKER_IMAGE}:latest"
                    }
                }
            }
        }

        stage('Deploy to Railway') {
            steps {
                sh 'curl -X POST "https://api.railway.app/deploy" -H "Authorization: Bearer YOUR_RAILWAY_TOKEN"'
            }
        }

    }

    post {
        success {
            echo 'Deployment is successful!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}