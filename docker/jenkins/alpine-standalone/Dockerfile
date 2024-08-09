FROM jenkins/jenkins:2.462.1-lts-alpine
USER root
RUN apk update && apk add --no-cache openssh-keygen
USER jenkins
RUN jenkins-plugin-cli --plugins "ssh-agent pipeline-stage-view pipeline-utility-steps sonar"