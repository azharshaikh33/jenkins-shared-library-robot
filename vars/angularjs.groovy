def call() {
    node {
        env.APP_TYPE = 'angularjs'
        common.lintchecks
        env.ARGS="-Dsonar.sources=."
        common.sonarchecks ()
        common.testcases ()
    }
}

// def call() {
//     pipeline {
//         agent any

//         environment { 
//         SONAR_URL = "http://172.31.20.98"
//         SONAR = credentials ('SONAR')
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
//                                stage('Test Cases') {
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
        

//             stage('Prepare the artifacts') {
//                 when { expression { env.TAG_NAME != null } }
//                 steps {
//                     sh "echo prepare the artifacts"
//                 }
//             }

//             stage('Publish the artifacts') {
//                 when { expression { env.TAG_NAME != null } }
//                 steps {
//                     sh "echo publish the artifacts"
//                 }
//             }

//         }
//     }
// }