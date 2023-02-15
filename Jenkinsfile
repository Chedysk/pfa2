pipeline {
    agent any

    stages {
        stage('checkout github repositoy') {
            steps {
                echo 'pulling';
                git branch:'devops',url : 'https://github.com/Chedysk/pfa2.git';
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
                    nexusUrl: '192.168.0.5:8081',
                     nexusVersion: 'nexus3', 
                     protocol: 'http',
                      repository: 'maven-releases',
                       version: '0.0.1'
            }
        }
         stage('Docker image'){
            steps {
                 sh 'docker build -t rihabhn/springapp .'
            }
        }
           stage('push to DockerHub'){
            steps { 
		   withCredentials([string(credentialsId: 'dockerhubdevv', variable: 'dockerhubpwdd')]) {
                    sh 'docker login -u rihabhn -p ${dockerhubpwdd}'
                    sh 'docker push rihabhn/springapp'
                    
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
