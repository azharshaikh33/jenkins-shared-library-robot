env.APP_TYPE = 'maven'
def call {
    node {
        common.lintchecks
        env.ARGS="-Dsonar.java.binaries=target/"
        common.sonarchecks ()
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

//              stage('sonar Check') {
//                 steps {
//                     script {
//                         sh "mvn clean compile"
//                         env.ARGS="-Dsonar.java.binaries=target/"
//                         common.sonarchecks ()
//                     }
//                 }
//             }
//                    stage('Test Cases') {
//                         parallel {
//                             stage('unit test') {
//                                 steps {
//                                     // mvn test
//                                     sh "echo performing unit test"
//                                 }
//                             }
//                             stage('Integrity test') {
//                                 steps {
//                                     // mvn verify
//                                     sh "echo performing integrity test"
//                                 }
//                             }
//                             stage('Functional test') {
//                                 steps {
//                                     // mvn test
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