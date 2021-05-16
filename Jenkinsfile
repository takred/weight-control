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
        stage('Deploy'){
            steps {
                sh '''
                    cp target/weight-control-0.0.1-SNAPSHOT.jar /home/riptor/projects/jenkins-install/deploy
                    cd /home/riptor/projects/jenkins-install/deploy
                    test -f application.pid \\&\\& pkill -F application.pid
                    nohup java -jar -Dserver.port=8083 weight-control-0.0.1-SNAPSHOT.jar \\&
                '''
            }
        }
    }
}
