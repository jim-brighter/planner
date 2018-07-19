def REPO_URL = "https://github.com/jim-brighter/planner.git"

node {
    stage("INIT") {
        GIT_BRANCH = GIT_BRANCH.replace("origin/", "")
        git(
            url: "${REPO_URL}",
            credentialsId: 'git-login',
            branch: "${GIT_BRANCH}"
        )
        currentBuild.setDisplayName("${GIT_BRANCH}-${BUILD_NUMBER}")
    }

    stage("BUILD") {
        sh """
            ./gradlew clean build
        """
    }

    stage("MERGE") {
        withCredentials([
            usernamePassword(credentialsId: "git-login", passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME'),
            string(credentialsId: 'git-name', variable: 'GIT_NAME'),
            string(credentialsId: 'git-email', variable: 'GIT_EMAIL')
        ]) {
            GIT_PASSWORD = GIT_PASSWORD.replace('@','%40')
            def tag = "planner-${BUILD_TIMESTAMP}-${GIT_BRANCH}"
            tag = tag.replace(" ", "_",).replace(":","-")
            def origin = "https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/jim-brighter/planner.git"
            sh """
                git remote set-url origin https://github.com/jim-brighter/planner.git
                git config user.name ${GIT_NAME}
                git config user.email ${GIT_EMAIL}
                git tag -a ${tag} -m "New Tag ${tag}"
                git push origin ${tag}

                if [ "${GIT_BRANCH}" = "ci" ]; then
                    git checkout -- .
                    git checkout master
                    git merge ${GIT_BRANCH}
                    git push origin master
                else
                    exit 0
                fi
            """
        }
    }
}