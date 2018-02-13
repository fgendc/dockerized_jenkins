# JENKINS CONTAINER
## BUILD
```
docker build -t myjenkins:latest --build-arg IMAGE_VERSION=2.46.3 --no-cache . 
```
If present it will process the plugins.txt file

## RUN
```
docker run \
-d --name jenkins \
# in case you want to mount a vagrant folder
# -v /vagrant:/vagrant  \
-v jenkins_home:/var/jenkins_home  \
-v $(which docker):/usr/bin/docker   \
-p 8080:8080  \
-p 50000:50000  \
myjenkins:latest
```