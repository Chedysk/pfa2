pipeline {
    agent any

    stages {
        stage('checkout github repositoy') {
            steps {
                echo 'pulling';
                git branch:'chedy',url : 'https://github.com/Chedysk/pfa2.git';
            }
        }
         stage("Build") {
            steps {
                sh 'mvn clean '
				sh 'mvn compile'
				sh 'mvn package'
		   
            }
        }
          stage('RUN tests') {
            parallel {
                  stage('Unit test ') {
            steps {
               
                sh 'mvn  test'
            }
        }
          stage('Sonarqube Analysis ') {
            steps {
                withSonarQubeEnv('sonarqube-8.9.7'){
                sh 'mvn sonar:sonar'
                }
                }
            }
        }
               
                
            }
            stage('nexus deploy') {
            steps {
               
               nexusArtifactUploader artifacts: [[artifactId: 'ExamThourayaS2', 
               classifier: '',
                file: 'target/ExamThourayaS2-0.0.1.jar',
                 type: 'jar']],
                  credentialsId: 'nexus',
                   groupId: 'tn.esprit',
                    nexusUrl: '10.110.40.110:8081',
                     nexusVersion: 'nexus3', 
                     protocol: 'http',
                      repository: 'maven-releases',
                       version: '0.0.1'
            }
        }
         stage('Docker image'){
            steps {
                 sh 'docker build -t chedysk/springapp .'
            }
        }
           stage('push to DockerHub'){
            steps { 
		   withCredentials([string(credentialsId: 'dockerHub1-id', variable: 'dockerhubpwd')]) {
                    sh 'docker login -u chedysk -p ${dockerhubpwd}'
                    sh 'docker push chedysk/springapp'
                    
                }
       }
       }   
        stage('DockerCompose') {
        
                       steps {
                            
				            sh 'docker-compose up -d'
                        }
                          
        }   
            
        }
        
    }