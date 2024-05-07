def lintchecks() {
    sh '''
            echo lint checks for ${COMPONENT}
            echo Installing JSlist
            # sh npm i jslint
            # sh ls -ltr node_modules/jslint/bin/
            # sh node_modules/jslint/bin/jslint.js server.js
            echo performing lint checks for ${COMPONENT}
            echo performing lint checks completed for ${COMPONENT}
    '''
}

def sonarchecks() {
    sh '''
    sonar-scanner -X -Dsonar.host.url=http://172.31.17.108:9000 -Dsonar.sources=. -Dsonar.projectkey=${COMPONENT} -Dsonar.login=admin -Dsonar.password=password

    '''
}

def call() {
    pipeline {
        agent any
        stages {
            stage('Lint Check') {
                steps {
                    script {
                        lintchecks ()
                    }
                }
            }
            stage('sonar Check') {
                steps {
                    script {
                        sonarchecks ()
                    }
                }
            }
        }
    }
}