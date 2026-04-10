pipeline {
    agent any

    stages {
        stage('Get Code') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean compile'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Package') {
            steps {
                sh './mvnw clean package'
                sh 'cp target/*.war target/jamesspetitions.war'
            }
        }

        stage('Archive WAR') {
            steps {
                archiveArtifacts artifacts: 'target/jamesspetitions.war', fingerprint: true
            }
        }

        stage('Approve Deploy') {
            steps {
                input message: 'Deploy jamesspetitions to Tomcat on EC2?'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deployment step will go here once EC2 and Tomcat are ready'
            }
        }
    }
}