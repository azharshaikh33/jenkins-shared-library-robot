def lintchecks() {
    sh '''
            sh "echo lint checks for ${COMPONENT}"
            sh "echo Installing JSlist"
            sh "npm i jslint"
            sh "ls -ltr node_modules/jslint/bin/"
            sh "node_modules/jslint/bin/jslint.js server.js"
            sh "echo performing lint checks"
            sh "echo performing lint checks completed"
    '''
}

def call() {
    pipeline {
        agent any
        stages {
            stage('Lint CHeck') {
                steps {
                    script {
                        lintchecks
                    }
                }
            }
        }
    }
}