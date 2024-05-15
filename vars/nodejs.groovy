
def call() {
    node {
        git branch: 'main', url: "https://github.com/azharshaikh33/${COMPONENT}.git"
        env.APP_TYPE = 'nodejs'
        common.lintchecks ()
        env.ARGS="-Dsonar.sources=."
        common.sonarchecks ()
        common.testcases ()

        if(env.TAG_NAME != null) {
            common.artifacts ()
        }
    }
}

// def call() {
//     pipeline {
//         agent any

//         environment { 
//         SONAR_URL = "172.31.20.98"
//         NEXUS_URL = "172.31.13.151"
//         SONAR = credentials ('SONAR')
//         NEXUS = credentials ('NEXUS')
//     }
//         stages {
//             stage('Lint Check') {
//                 steps {
//                     script {
//                         lintchecks ()
//                     }
//                 }
//             }
//             stage('sonar Check') {
//                 steps {
//                     script {
//                         env.ARGS="-Dsonar.sources=."
//                         common.sonarchecks ()
//                     }
//                 }
//             }

//             stage('Test Cases') {
//                         parallel {
//                             stage('unit test') {
//                                 steps {
//                                     // npm test
//                                     sh "echo performing unit test"
//                                 }
//                             }
//                             stage('Integrity test') {
//                                 steps {
//                                     // npm verify
//                                     sh "echo performing integrity test"
//                                 }
//                             }
//                             stage('Functional test') {
//                                 steps {
//                                     // npm test
//                                     sh "echo functional unit test"
//                                 }
//                             }
//                         }
//                     }

//              stage('Checking the version') {
//                 when { expression { env.TAG_NAME != null } }
//                 steps {
//                    script {
//                             env.UPLOAD_STATUS = sh(returnStdout: true, script: 'curl -L -s http://${NEXUS_URL}:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME} || true')
//                             print UPLOAD_STATUS
//                    }
//                 }
//             }

//              stage('Prepare the artifacts') {
//                 when { 
//                     expression { env.TAG_NAME != null }
//                     expression { env.UPLOAD_STATUS == "" }
//                     }
//                 steps {
//                     sh "npm install"
//                     sh "echo preparing the artifacts"
//                     sh "zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js"
//                 }
//             }

//             stage('Publish the artifacts') {
//                 when { 
//                     expression { env.TAG_NAME != null }
//                     expression { env.UPLOAD_STATUS == "" }
//                     }
//                 steps {
//                     sh "echo publish the artifacts"
//                     sh "curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUS_URL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"
//                 }
//             }
//         }
        
//     }
// }