## USAGE
# docker build -t myjenkins --build-arg IMAGE_VERSION=2.46.3 .

ARG IMAGE_VERSION=latest
FROM jenkins:${IMAGE_VERSION}
USER root

# needed to install plugins via install-plugins.sh
ENV JENKINS_UC https://updates.jenkins.io
ENV JENKINS_UC_EXPERIMENTAL=https://updates.jenkins.io/experimental

# prevents configuration wizard from running at startup
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

# rm removes post-installation crap
RUN apt update && \
    apt install -y sudo libltdl-dev && \
    rm -rf /var/lib/apt/lists/*

RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
# loads oc binary in case it's needed
# COPY oc /usr/bin/oc

# copy security scripts  (admin user creation)
COPY security.groovy /usr/share/jenkins/ref/init.groovy.d/security.groovy

# copy folder to home
#COPY extra/ /var/jenkins_home/

# careful when changing the new_plugins.txt file  
# it doesn't like windows EOLs
#COPY plugins.txt /plugins.txt
#RUN /usr/local/bin/install-plugins.sh < /plugins.txt

RUN git config --global http.sslVerify false
USER jenkins
EXPOSE 50000 8080
