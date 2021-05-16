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
                sh 'docker-compose --project-name=weight-control-app down'
            }
        }
        stage('Start docker') {
            steps {
                sh 'docker-compose  --project-name=weight-control-app up -d --build'
            }
        }
    }
}
