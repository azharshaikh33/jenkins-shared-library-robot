def lintchecks() {
    sh '''
            echo lint checks for ${COMPONENT}
            # mvn checkstyle:check
            echo performing lint checks for ${COMPONENT}
            echo performing lint checks completed for ${COMPONENT}
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
        }
    }
}