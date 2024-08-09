FROM jenkins/jenkins:2.462.1-lts-jdk17

USER root
RUN apt-get update && apt-get install ca-certificates -y && apt-get clean
RUN install -m 0755 -d /etc/apt/keyrings
RUN curl -fsSLo /etc/apt/keyrings/docker.asc https://download.docker.com/linux/debian/gpg
RUN chmod a+r /etc/apt/keyrings/docker.asc

RUN echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/debian \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  tee /etc/apt/sources.list.d/docker.list > /dev/null
RUN apt-get update && apt-get install -y docker-ce-cli && apt-get clean

USER jenkins
RUN jenkins-plugin-cli --plugins "docker-plugin docker-workflow ssh-agent pipeline-stage-view pipeline-utility-steps sonar"