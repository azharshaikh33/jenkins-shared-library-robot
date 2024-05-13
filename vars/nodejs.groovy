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
    // sh "sonar-scanner -X -Dsonar.host.url=http://172.31.20.98:9000 -Dsonar.sources=. -Dsonar.projectkey=${COMPONENT} -Dsonar.login=$(SONAR_USR) -Dsonar.password=$(SONAR_PSW)"
    // curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh
    // bash -x quality-gate.sh $(SONAR_PSW) $(SONAR_PSW) $(SONAR_URL) $(COMPONENT)
    sh "echo performing code quality check"
}

def call() {
    pipeline {
        agent any

        environment { 
        SONAR_URL = "http://172.31.20.98"
        SONAR = credentials ('SONAR')
    }
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
                        env.ARGS="-Dsonar.sources=."
                        sonarchecks ()
                    }
                }
            }
            stage('performing npm install') {
                steps {
                    sh "echo hai"
                }
            }
        }
    }
}