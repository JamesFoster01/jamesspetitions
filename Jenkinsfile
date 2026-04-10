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
                script {
                    timeout(time: 10, unit: 'MINUTES') {
                        input message: 'Deploy jamesspetitions to Tomcat on EC2?', ok: 'Proceed'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Starting deploy stage'
                sh 'ls -l target'
                echo 'Deployment step completed successfully'
            }
        }
    }

    post {
        success {
            echo 'Pipeline finished successfully'
        }
        failure {
            echo 'Pipeline failed'
        }
    }
}