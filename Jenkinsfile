def REPO_URL = "https://github.com/jim-brighter/planner.git"

def DOCKER_TAG

node {

    deleteDir()

    stage("INIT") {
        GIT_BRANCH = GIT_BRANCH.replace("origin/", "")
        git(
            url: "${REPO_URL}",
            credentialsId: 'git-login',
            branch: "${GIT_BRANCH}"
        )
        currentBuild.setDisplayName("${GIT_BRANCH}-${BUILD_NUMBER}")
        DOCKER_TAG = "${BUILD_TIMESTAMP}".replace(" ","").replace(":","").replace("-","")

        sh "chmod +x ./pipeline/*.sh"
    }

    stage("PULL BASE IMAGES") {
        sh label: "Pull Base Images", script: "./pipeline/pull-base-images.sh"
    }

    stage("BUILD ARTIFACTS") {
        sh label: "Build App Artifacts", script: "./pipeline/build-artifacts.sh"
    }

    stage("BUILD DOCKER") {

        withEnv([
            "DOCKER_TAG=${DOCKER_TAG}"
        ]) {
            sh label: "Build Docker Images", script: "./pipeline/build-docker.sh"
        }
    }

    stage("PUSH DOCKER") {
        withCredentials([
            usernamePassword(credentialsId: "docker-login", passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')
        ]) {

            withEnv([
                "DOCKER_TAG=${DOCKER_TAG}"
            ]) {
                sh label: "Push Docker Images - Tag", script: "./pipeline/push-docker-tag.sh"

                if (GIT_BRANCH == "ci" || GIT_BRANCH == "master") {
                    sh label: "Push Docker Images - Latest", script: "./pipeline/push-docker-latest.sh"
                }
            }
        }
    }

    stage("DEPLOY") {
        withCredentials([
            usernamePassword(credentialsId: "git-login", passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME'),
            string(credentialsId: 'do-token', variable: 'DO_TOKEN')
        ]) {
            sh label: "Deploy App to DO Droplet", script: "./pipeline/deploy.sh"
        }
    }

    stage("MERGE") {
        withCredentials([
            usernamePassword(credentialsId: "git-login", passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME'),
            string(credentialsId: 'git-name', variable: 'GIT_NAME'),
            string(credentialsId: 'git-email', variable: 'GIT_EMAIL')
        ]) {
            def tag = "planner-${BUILD_TIMESTAMP}-${GIT_BRANCH}"
            tag = tag.replace(" ", "_",).replace(":","-")
            def origin = "https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/jim-brighter/planner.git"

            withEnv([
                "origin=${origin}",
                "tag=${tag}"
            ]) {
                sh label: "Push Git Tag", script: "./pipeline/push-git-tag.sh"

                if (GIT_BRANCH == "ci") {
                    sh label: "Merge to Master", script: "./pipeline/merge-to-master.sh"
                }
            }
        }
    }
}