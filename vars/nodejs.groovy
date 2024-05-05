def lintchecks(COMPONENT) {
    sh '''
            sh "echo Installing JSlist"
            sh "npm i jslint"
            sh "ls -ltr node_modules/jslint/bin/"
            sh "node_modules/jslint/bin/jslint.js server.js"
            sh "echo performing lint checks"
            sh "echo performing lint checks completed"
    '''
}

def call(COMPONENT) {
    pipeline {
        agent any
        stages {
            stage('Lint CHeck') {
                steps {
                    script {
                        nodejs.lintchecks(COMPONENT)
                    }
                }
            }
        }
    }
}