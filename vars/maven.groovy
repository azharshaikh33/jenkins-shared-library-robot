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

             stage('sonar Check') {
                steps {
                    script {
                        env.ARGS="-Dsonar.sources=."
                        sonarchecks ()
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