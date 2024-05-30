def call () {
    node {
        properties([
            parameters([
                choice(choices: ['dev\nprod'], description: "Select the environment", name: "ENV"),
                choice(choices: ['apply\ndestroy'], description: "Select the action", name: "ACTION"),
            ]),
        ])
        ansiColor('xterm') {
            sh "rm -rf *"
                git branch: 'main', url: "https://github.com/azharshaikh33/${REPONAME}.git"
                
                stage('terraform init') {
                    sh '''
                        terrafile -f env-dev/Terrafile
                        terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars
                    '''
                }
                
                stage('terraform plan') {
                    sh '''
                        terraform plan -var-file=env-${ENV}/${ENV}.tfvars
                    '''
                }
                
                stage('terraform apply') {
                    sh '''
                        terraform ${ACTION} -var-file=env-${ENV}/${ENV}.tfvars -auto-approve
                    '''
                }
    
        }
    }
}

    
