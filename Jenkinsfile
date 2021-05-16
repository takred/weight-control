pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                sh './mvnw -B -DskipTests clean package'
            }
        }
        stage('Stop docker') {
            steps {
                sh 'docker-compose down'
            }
        }
        stage('Start docker') {
            steps {
                sh 'docker-compose up -d --build'
            }
        }
    }
}
