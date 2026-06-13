pipeline {
    agent any

    stages {

        stage('Clone Code') {
            steps {
                git 'https://github.com/E-Nat/reactjs-devop8-template-test.git'
                sh 'ls -lrt '
                sh "pwd"
                sh """
                echo "This is the code clone " 
                ls 
                docker version 
                """

            }
        } 

        stage("Build"){
            steps{
                sh """
                    docker build -t jenkins-react-pipeline . 
                """
            }
        }


        stage("Deploy"){
            steps{
                sh"""
                docker stop reactjs-cont || true 
                docker rm reactjs-cont || true 


                docker run -dp 3000:80 \
                    --name reactjs-cont \
                    jenkins-react-pipeline
                """

            }
        }

        stage("Add Domain Name"){
            steps{
                sh """

                which certbot
                certbot --version 
                # write reverse proxy config 
                # sudo nginx -s reload 
                """ 
            }
        }
    }
}
