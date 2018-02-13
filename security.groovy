#!groovy
 
import jenkins.model.*
import hudson.security.*
import jenkins.security.s2m.AdminWhitelistRule
import com.dabsquared.gitlabjenkins.connection.*

def instance = Jenkins.getInstance()
 
// using secrets (docker swarm only)
//def user = new File("/run/secrets/jenkins-user").text.trim()
//def pass = new File("/run/secrets/jenkins-pass").text.trim()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)

// hardcodear passwords no es seguro, pero sin swarm y para local es la unica opcion
hudsonRealm.createAccount("admin", "miaumiaumiau")
instance.setSecurityRealm(hudsonRealm)
 
def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)
instance.save()
 
Jenkins.instance.getInjector().getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)

/*
GitLabConnectionConfig descriptor = (GitLabConnectionConfig) Jenkins.getInstance().getDescriptor(GitLabConnectionConfig.class)

GitLabConnection gitLabConnection = new GitLabConnection('GitLab',
                                        'http://git_gitlab',
                                        '',
                                        true,
                                        10,
                                        10)
descriptor.getConnections().clear()
descriptor.addConnection(gitLabConnection)
descriptor.save()
*/