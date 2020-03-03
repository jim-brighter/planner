def REPO_URL = "https://github.com/jim-brighter/planner.git"

def DOCKER_TAG

def isPr() {
    return env.BRANCH_NAME.startsWith("PR-")
}

def isPushToMaster() {
    return env.BRANCH_NAME == "master"
}

def updateGithubStatus() {
    withCredentials([
        usernamePassword(credentialsId: 'git-login', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')
    ]) {
        sh """
            curl -i https://api.github.com -u ${GIT_USERNAME}:${GIT_PASSWORD}
        """
    }
}

node {

    properties([[$class: 'JiraProjectProperty'], buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 
                [$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false]])

    deleteDir()

    stage("INIT") {
        git(
            url: "${REPO_URL}",
            credentialsId: 'git-login',
            branch: isPr() ? env.CHANGE_BRANCH : env.BRANCH_NAME
        )
        updateGithubStatus()

        DOCKER_TAG = "${BUILD_TIMESTAMP}".replace(" ","").replace(":","").replace("-","")

        sh "chmod +x ./pipeline/*.sh"
    }

    if (isPr() || isPushToMaster()) {
        stage("PULL BASE IMAGES") {
            sh label: "Pull Base Images", script: "./pipeline/pull-base-images.sh"
        }
    }

    if (isPr() || isPushToMaster()) {
        stage("BUILD ARTIFACTS") {
            sh label: "Build App Artifacts", script: "./pipeline/build-artifacts.sh"
        }
    }

    if (isPr() || isPushToMaster()) {
        stage("BUILD DOCKER") {
            withEnv([
                "DOCKER_TAG=${DOCKER_TAG}"
            ]) {
                sh label: "Build Docker Images", script: "./pipeline/build-docker.sh"
            }
        }
    }

    if (isPr() || isPushToMaster()) {
        stage("PUSH DOCKER") {
            withCredentials([
                usernamePassword(credentialsId: "docker-login", passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')
            ]) {

                withEnv([
                    "DOCKER_TAG=${DOCKER_TAG}"
                ]) {
                    sh label: "Push Docker Images - Tag", script: "./pipeline/push-docker-tag.sh"

                    if (isPushToMaster()) {
                        sh label: "Push Docker Images - Latest", script: "./pipeline/push-docker-latest.sh"
                    }
                }
            }
        }
    }

    if (isPushToMaster()) {
        stage("DEPLOY") {
            withCredentials([
                usernamePassword(credentialsId: "git-login", passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME'),
                string(credentialsId: 'do-token', variable: 'DO_TOKEN')
            ]) {
                sh label: "Deploy App to DO Droplet", script: "./pipeline/deploy.sh"
                sh label: "Run Healthcheck", script: "./pipeline/healthcheck.sh"
            }
        }
    }

    if (isPushToMaster()) {
        stage("TAG") {
            withCredentials([
                usernamePassword(credentialsId: "git-login", passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')
            ]) {
                def tag = "planner-${BUILD_TIMESTAMP}-${BRANCH_NAME}"
                tag = tag.replace(" ", "_",).replace(":","-")
                def origin = "https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/jim-brighter/planner.git"

                withEnv([
                    "origin=${origin}",
                    "tag=${tag}"
                ]) {
                    sh label: "Push Git Tag", script: "./pipeline/push-git-tag.sh"
                }
            }
        }
    }
}
