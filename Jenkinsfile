pipeline {
    agent any
    stages {
        stage ('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage ('deploy') {
            when {
                branch 'main'
            }
            
            steps {
                sh 'cp target/project0.war ~/apache-tomcat-9.0.40/webapps'
            }
        }
    }    
}
