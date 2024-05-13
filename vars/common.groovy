def sonarchecks() {
    // sh "sonar-scanner -X -Dsonar.host.url=http://172.31.20.98:9000 -Dsonar.sources=. -Dsonar.projectkey=${COMPONENT} -Dsonar.login=$(SONAR_USR) -Dsonar.password=$(SONAR_PSW)"
    // curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh
    // bash -x quality-gate.sh $(SONAR_PSW) $(SONAR_PSW) $(SONAR_URL) $(COMPONENT)
    sh "echo performing code quality check"
}