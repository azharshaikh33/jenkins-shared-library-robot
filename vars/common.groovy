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