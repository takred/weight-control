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
        stage('Test'){
            steps {
                sh './mvnw test'
            }
        }
    }
}
