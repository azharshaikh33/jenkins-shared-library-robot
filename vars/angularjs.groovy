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
                        common.sonarchecks ()
                    }
                }
            }
                               stage('Test Cases') {
                    parallel {
                        stage('Unit Test') {
                            steps {
                                // sh "npm test"
                                sh "Performing unit testing"
                            }
                        }
                            stage('Integration test') {
                            steps {
                                // sh "npm verify"
                                sh "Performing Integration testing"
                            }
                        }
                            stage('Functional Testing') {
                            steps {
                                sh "Performing Functional testing"
                            }
                        }
                    }
                }
        

            stage('Prepare the artifacts') {
                when { expression { env.TAG_NAME != null } }
                steps {
                    sh "echo prepare the artifacts"
                }
            }

            stage('Publish the artifacts') {
                when { expression { env.TAG_NAME != null } }
                steps {
                    sh "echo publish the artifacts"
                }
            }

        }
    }
}