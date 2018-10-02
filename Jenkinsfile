pipeline {    
    agent any
    
    tools {
        maven 'mvn'
        jdk 'jdk'
    }
    
    options {
    	buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    
    environment {
    	groupId = readMavenPom().getGroupId()
		artifactId = readMavenPom().getArtifactId()
		version = readMavenPom().getVersion()
    }
    
    stages {
        stage ('Inicializacion') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
		stage ('Pruebas') {
			steps {
			    sh 'mvn clean verify'
			}

		}
		
		stage ('SonarQube'){
			steps {
			    sh '''
					mvn sonar:sonar \
					  -Dsonar.host.url=http://10.0.2.5:9000 \
					  -Dsonar.login=b3f2d2bd019e4c39bc31bfe8515b9cad'''
		
			}

		}
		
        stage ('Compilacion y Entrega') {
            steps {
                sh '''
                mvn package -DskipTests=true
                
                ''' 
            }
        }
        
    }
    
    post {
    	always {
        	junit 'target/surefire-reports/*.xml'
    	}
    }

}