def sonarchecks() {
    stage('Sonar Checks') {
    // sh "sonar-scanner -X -Dsonar.host.url=http://172.31.20.98:9000 ${ARGS} -Dsonar.projectkey=${COMPONENT} -Dsonar.login=$(SONAR_USR) -Dsonar.password=$(SONAR_PSW)"
    // curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh
    // bash -x quality-gate.sh $(SONAR_PSW) $(SONAR_PSW) $(SONAR_URL) $(COMPONENT)
    sh "echo performing code quality check"
    }
}

def testcases() {
    stage('test cases') {
        def stages = [:]

        stages["Unit testing"] = {
            echo "started unit testing"
            echo "completed unit testing"
            // sh mvn test or npm test
        }
        stages["Integration testing"] = {
            echo "started Integration testing"
            echo "completed Integration testing"
            // sh mvn verify or npm verify
        }
        stages["Functional testing"] = {
            echo "started Functional testing"
            echo "completed Functional testing"
            // sh mvn verify or npm verify
        }

        parallel(stages)
    }
}

def lintchecks() {
    stage('lint checks') {
        if(env.APP_TYPE == 'angularjs') {
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
        else if(env.APP_TYPE == 'nodejs') {
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
        else if(env.APP_TYPE == 'python') {
        sh '''
            echo lint checks for ${COMPONENT}
            # pylint *.py
            echo performing lint checks for ${COMPONENT}
            echo performing lint checks completed for ${COMPONENT}
        '''
        }
        else(env.APP_TYPE == 'maven') {
        sh '''
            echo lint checks for ${COMPONENT}
            # mvn checkstyle:check
            echo performing lint checks for ${COMPONENT}
            echo performing lint checks completed for ${COMPONENT}
        '''
        }
    }
}

def artifacts() {
    stage('Check the release') {
        env.UPLOAD_STATUS = sh(returnStdout: true, script: 'curl -L -s http://${NEXUS_URL}:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME} || true')
        print UPLOAD_STATUS
    }

if(env.UPLOAD_STATUS == "" ) {

            stage('Preparing the artifacts') {
                if(env.APP_TYPE == 'nodejs') {
                sh '''
                npm install
                echo preparing the artifacts
                zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js
                '''
                }
                else if(env.APP_TYPE == 'maven') {
                    sh ''' 
                        echo "yet to type"
                    '''
                }
                else if(env.APP_TYPE == 'python') {
                    sh ''' 
                        echo "yet to type"
                    '''

                }
                else(env.APP_TYPE == 'angularjs') {
                    sh ''' 
                        echo "yet to type"
                    '''

                }
            }
            stage('Publishing the artifacts') {
                withCredentials([usernamePassword(credentialsId: 'NEXUS', passwordVariable: 'NEXUS_PSW', usernameVariable: 'NEXUS_USR')]) {
                sh "curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUS_URL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"
                }
            }
    
    }
}