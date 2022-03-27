def REPO_URL = "https://github.com/jim-brighter/planner.git"

def isPr() {
    return env.BRANCH_NAME.startsWith("PR-")
}

def isPushToMaster() {
    return env.BRANCH_NAME == "master"
}

def isWorkingBranch() {
    return !isPr() && !isPushToMaster()
}

def ERROR = "error"
def FAILURE = "failure"
def PENDING = "pending"
def SUCCESS = "success"

def GIT_COMMIT

node {

    properties([
        [$class: 'JiraProjectProperty'],
        buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')),
        [$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false],
        pipelineTriggers([
            [$class: "TimerTrigger", spec: isPushToMaster() ? "H 18 * * 5" : ""]
        ])
    ])

    deleteDir()

    if (isWorkingBranch()) {
        currentBuild.result = 'SUCCESS'
        return
    }

    stage("INIT") {
        gitOutput = git(
            url: "${REPO_URL}",
            credentialsId: 'git-login',
            branch: isPr() ? env.CHANGE_BRANCH : env.BRANCH_NAME
        )

        GIT_COMMIT = gitOutput.GIT_COMMIT

        sh "chmod +x ./pipeline/*.sh"
    }

    stage("BUILD ARTIFACTS") {
        sh label: "Build Java Artifacts", script: "./pipeline/build-java.sh"
        sh label: "Build UI Artifacts", script: "./pipeline/build-ui.sh"
    }

    stage("PULL BASE IMAGES") {
        sh label: "Pull Base Images", script: "./pipeline/pull-base-images.sh"
    }

    stage("BUILD DOCKER") {
        sh label: "Build Docker Images", script: "./pipeline/build-docker.sh"
    }

    if (isPr()) {
        currentBuild.result = 'SUCCESS'
        return
    }

    stage("PUSH DOCKER") {
        withCredentials([
            usernamePassword(credentialsId: "docker-login", passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')
        ]) {
            sh label: "Push Docker Images - Tag", script: "./pipeline/push-docker-tag.sh"
        }
    }

    stage("DEPLOY") {
        withCredentials([
            usernamePassword(credentialsId: "git-login", passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME'),
            string(credentialsId: 'do-token', variable: 'DO_TOKEN')
        ]) {
            sh label: "Deploy App to DO Droplet", script: "./pipeline/deploy.sh"
            sh label: "Run Healthcheck", script: "./pipeline/healthcheck.sh"
        }
    }

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
