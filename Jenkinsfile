const REPO_URL = "https://github.com/jim-brighter/planner.git"

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
}