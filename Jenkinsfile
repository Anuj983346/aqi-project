pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

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
          script {
            // only run junit if tests exist
            if (fileExists('build/test-results') || fileExists('build/test-results/test')) {
              echo "Found test results - publishing."
              junit '**/build/test-results/**/*.xml'
            } else {
              echo "No test results found - skipping junit."
            }

            // archive jar if present
            if (fileExists('build/libs')) {
              echo "Archiving jars."
              archiveArtifacts artifacts: 'build/libs/*.jar', onlyIfSuccessful: true
            } else {
              echo "No artifacts to archive."
            }
          }
        }
      }
    }
  }

  post {
    success { echo "Pipeline success." }
    failure { echo "Pipeline failed." }
  }
}
