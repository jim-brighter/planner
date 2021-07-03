def REPO_URL = "https://github.com/jim-brighter/planner.git"

def DOCKER_TAG

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

def updateGithubStatus(stage, state, sha) {
    withCredentials([
        usernamePassword(credentialsId: 'git-login', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')
    ]) {
        def branch = isPr() ? env.CHANGE_BRANCH : env.BRANCH_NAME
        withEnv([
            "sha=${sha}",
            "state=${state}",
            "stage=${stage}",
            "branch=${env.BRANCH_NAME}",
            "buildNumber=${env.BUILD_NUMBER}"
        ]) {
            sh '''
                curl --output /dev/null --silent --fail https://api.github.com/repos/jim-brighter/planner/statuses/$sha \
                    -H "Authorization: token $GIT_PASSWORD" \
                    -H "Accept: application/vnd.github.v3+json" \
                    -H "Content-Type: application/json" \
                    -X POST \
                    -d '{
                        "state": "'"$state"'",
                        "target_url": "'"http://jimsjenkins.xyz/blue/organizations/jenkins/Planner/detail/$branch/$buildNumber/pipeline/"'",
                        "description": "'"$stage - $state"'",
                        "context": "'"continuous-integration/jenkins/$stage"'"
                    }'
            '''
        }
    }
}

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

    stage("INIT") {
        gitOutput = git(
            url: "${REPO_URL}",
            credentialsId: 'git-login',
            branch: isPr() ? env.CHANGE_BRANCH : env.BRANCH_NAME
        )

        GIT_COMMIT = gitOutput.GIT_COMMIT

        updateGithubStatus(STAGE_NAME, PENDING, GIT_COMMIT)

        DOCKER_TAG = "${BUILD_TIMESTAMP}".replace(" ","").replace(":","").replace("-","")

        sh "chmod +x ./pipeline/*.sh"

        updateGithubStatus(STAGE_NAME, SUCCESS, GIT_COMMIT)
    }

    stage("BUILD ARTIFACTS") {
        updateGithubStatus(STAGE_NAME, PENDING, GIT_COMMIT)
        sh label: "Build Java Artifacts", script: "./pipeline/build-java.sh"
        sh label: "Build UI Artifacts", script: "./pipeline/build-ui.sh"
        updateGithubStatus(STAGE_NAME, SUCCESS, GIT_COMMIT)
    }

    if (isWorkingBranch()) {
        currentBuild.result = 'SUCCESS'
        return
    }

    stage("PULL BASE IMAGES") {
        updateGithubStatus(STAGE_NAME, PENDING, GIT_COMMIT)
        sh label: "Pull Base Images", script: "./pipeline/pull-base-images.sh"
        updateGithubStatus(STAGE_NAME, SUCCESS, GIT_COMMIT)
    }

    stage("BUILD DOCKER") {
        updateGithubStatus(STAGE_NAME, PENDING, GIT_COMMIT)
        withEnv([
            "DOCKER_TAG=${DOCKER_TAG}"
        ]) {
            sh label: "Build Docker Images", script: "./pipeline/build-docker.sh"
        }
        updateGithubStatus(STAGE_NAME, SUCCESS, GIT_COMMIT)
    }

    if (isPr()) {
        currentBuild.result = 'SUCCESS'
        return
    }

    stage("PUSH DOCKER") {
        updateGithubStatus(STAGE_NAME, PENDING, GIT_COMMIT)
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
        updateGithubStatus(STAGE_NAME, SUCCESS, GIT_COMMIT)
    }

    stage("DEPLOY") {
        updateGithubStatus(STAGE_NAME, PENDING, GIT_COMMIT)
        withCredentials([
            usernamePassword(credentialsId: "git-login", passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME'),
            string(credentialsId: 'do-token', variable: 'DO_TOKEN')
        ]) {
            sh label: "Deploy App to DO Droplet", script: "./pipeline/deploy.sh"
            sh label: "Run Healthcheck", script: "./pipeline/healthcheck.sh"
        }
        updateGithubStatus(STAGE_NAME, SUCCESS, GIT_COMMIT)
    }

    stage("TAG") {
        updateGithubStatus(STAGE_NAME, PENDING, GIT_COMMIT)
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
        updateGithubStatus(STAGE_NAME, SUCCESS, GIT_COMMIT)
    }
}
