pipeline {
  agent any

  stages {
    stage('Checkout') { steps { checkout scm } }

    stage('Build & Test') {
      steps {
        script {
          if (isUnix()) {
            sh './gradlew clean build --no-daemon'
          } else {
            bat 'gradlew.bat clean build --no-daemon'
          }
        }
      }
      post {
        always {
          junit '**/build/test-results/**/*.xml'
          archiveArtifacts artifacts: 'build/libs/*.jar', onlyIfSuccessful: true
        }
      }
    }
  }
}
