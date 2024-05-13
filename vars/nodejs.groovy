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

def call() {
    pipeline {
        agent any

        environment { 
        SONAR_URL = "172.31.20.98"
        NEXUS_URL = "172.31.13.151"
        SONAR = credentials ('SONAR')
        NEXUS = credentials ('NEXUS')
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
                        common.sonarchecks ()
                    }
                }
            }

            stage('Test Cases') {
                        parallel {
                            stage('unit test') {
                                steps {
                                    // npm test
                                    sh "echo performing unit test"
                                }
                            }
                            stage('Integrity test') {
                                steps {
                                    // npm verify
                                    sh "echo performing integrity test"
                                }
                            }
                            stage('Functional test') {
                                steps {
                                    // npm test
                                    sh "echo functional unit test"
                                }
                            }
                        }
                    }

             stage('Checking the version') {
                when { expression { env.TAG_NAME != null } }
                steps {
                   scripts {
                            env.UPLOAD_STATUS=sh(returnStdout: true, script: 'curl -L -s http://${NEXUS_URL}:8081/service/rest/repository/browse/${COMPONENT/ | grep ${COMPONENT-${TAG_NAME} || true')
                            print UPLOAD_STATUS
                   }
                }
            }

             stage('Prepare the artifacts') {
                when { 
                    expression { env.TAG_NAME != null }
                    expression { env.UPLOAD_STATUS == "" }
                    }
                steps {
                    sh "npm install"
                    sh "echo preparing the artifacts"
                    sh "zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js"
                }
            }

            stage('Publish the artifacts') {
                when { 
                    expression { env.TAG_NAME != null }
                    expression { env.UPLOAD_STATUS == "" }
                    }
                steps {
                    sh "echo publish the artifacts"
                    sh "curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUS_URL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"
                }
            }
        }
        
    }
}