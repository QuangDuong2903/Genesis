FROM jenkins/jenkins:2.452.3-jdk17
USER root
RUN apt-get update && apt-get install -y lsb-release && apt-get clean
RUN curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc \
  https://download.docker.com/linux/debian/gpg
RUN echo "deb [arch=$(dpkg --print-architecture) \
  signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
  https://download.docker.com/linux/debian \
  $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list
RUN apt-get update && apt-get install -y docker-ce-cli && apt-get clean
USER jenkins
RUN jenkins-plugin-cli --plugins "blueocean docker-workflow"