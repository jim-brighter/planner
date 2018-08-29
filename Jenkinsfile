def REPO_URL = "https://github.com/jim-brighter/planner.git"

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
    }

    stage("PULL BASE IMAGES") {
        sh """
            docker pull openjdk:8-jre-alpine
            docker pull postgres:10-alpine
            docker pull nginx:1.15-alpine
        """
    }

    stage("BUILD ARTIFACTS") {
        sh """
            ./gradlew clean build
        """
    }

    stage("BUILD DOCKER") {
        sh """
            docker build -t jimbrighter/planner-svc:latest -f planner-svc/Dockerfile planner-svc
            docker build -t jimbrighter/planner-auth:latest -f planner-auth/Dockerfile planner-auth
            docker build -t jimbrighter/planner-db:latest -f planner-db/Dockerfile planner-db
            docker build -t jimbrighter/planner-ui:latest -f planner-ui/Dockerfile planner-ui/staging
        """
    }

    stage("PUSH DOCKER") {
        withCredentials([
            usernamePassword(credentialsId: "docker-login", passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')
        ]) {
            sh """
                docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}
                docker push jimbrighter/planner-svc:latest
                docker push jimbrighter/planner-auth:latest
                docker push jimbrighter/planner-db:latest
                docker push jimbrighter/planner-ui:latest
            """
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
            sh """
                git remote set-url origin https://github.com/jim-brighter/planner.git
                git config user.name "${GIT_NAME}"
                git config user.email ${GIT_EMAIL}
                git tag -a ${tag} -m "New Tag ${tag}"
                git push ${origin} ${tag}

                if [ "${GIT_BRANCH}" = "ci" ]; then
                    git checkout -- .
                    git checkout master
                    git merge ${GIT_BRANCH}
                    git push ${origin} master
                else
                    exit 0
                fi
            """
        }
    }
}