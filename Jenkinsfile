pipeline {
    agent any

    environment {
        EC2_HOST = '16.170.141.194'
        EC2_USER = 'ec2-user'
        APP_WAR  = 'jamesspetitions-0.0.1-SNAPSHOT.war'
    }

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
            }
        }

        stage('Archive WAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            }
        }

        stage('Approve Deploy') {
            steps {
                input message: 'Deploy jamesspetitions to EC2?', ok: 'Proceed'
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([sshUserPrivateKey(
                    credentialsId: 'ec2-deploy-key-file',
                    keyFileVariable: 'SSH_KEY',
                    usernameVariable: 'SSH_USER'
                )]) {
                    sh """
                        scp -i "$SSH_KEY" -o StrictHostKeyChecking=no target/${APP_WAR} ${SSH_USER}@${EC2_HOST}:~
                        ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no ${SSH_USER}@${EC2_HOST} "
                            sudo bash -c 'cd /opt/tomcat/bin && ./shutdown.sh' || true
                            sudo rm -rf /opt/tomcat/webapps/jamesspetitions-0.0.1-SNAPSHOT
                            sudo rm -f /opt/tomcat/webapps/jamesspetitions-0.0.1-SNAPSHOT.war
                            sudo rm -rf /opt/tomcat/webapps/ec2-user
                            sudo mv /home/${SSH_USER}/${APP_WAR} /opt/tomcat/webapps/
                            sudo bash -c 'cd /opt/tomcat/bin && ./startup.sh'
                        "
                    """
                }
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