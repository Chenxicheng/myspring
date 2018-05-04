package com.dashwood.myspring.util;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.net.HostAndPort;
import com.jcraft.jsch.agentproxy.AgentProxy;
import com.jcraft.jsch.agentproxy.Connector;
import com.jcraft.jsch.agentproxy.Identity;
import com.jcraft.jsch.agentproxy.sshj.AuthAgent;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.Buffer;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.keyprovider.OpenSSHKeyFile;
import net.schmizz.sshj.userauth.method.AuthMethod;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.sshj.SSHClientConnection;
import org.jclouds.sshj.SshjSshClient;

import java.util.List;

public class SSHClientCreate implements SshjSshClient.Connection<SSHClient> {
    private SSHClientConnection connection;
    private SSHClient ssh;

    public SSHClientCreate(SSHClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void clear() throws Exception {

    }

    @Override
    public SSHClient create() throws Exception {
        ssh = new net.schmizz.sshj.SSHClient();
        ssh.addHostKeyVerifier(new PromiscuousVerifier());
        if (connection.getConnectTimeout() != 0) {
            ssh.setConnectTimeout(connection.getConnectTimeout());
        }
        if (connection.getSessionTimeout() != 0) {
            ssh.setTimeout(connection.getSessionTimeout());
        }
        HostAndPort hostAndPort = connection.getHostAndPort();
        ssh.connect(hostAndPort.getHost(), hostAndPort.getPortOrDefault(22));
        LoginCredentials loginCredentials = connection.getLoginCredentials();
        Optional<Connector> agentConnector = connection.getAgentConnector();
        if (loginCredentials.hasUnencryptedPrivateKey()) {
            OpenSSHKeyFile key = new OpenSSHKeyFile();
            key.init(loginCredentials.getOptionalPrivateKey().get(), null);
            ssh.authPublickey(loginCredentials.getUser(), key);
        } else if (loginCredentials.getOptionalPassword().isPresent()) {
            ssh.authPassword(loginCredentials.getUser(), loginCredentials.getOptionalPassword().get());
        } else if (agentConnector.isPresent()) {
            AgentProxy proxy = new AgentProxy(agentConnector.get());
            ssh.auth(loginCredentials.getUser(), getAuthMethods(proxy));
        }
        return ssh;
    }
    private static List<AuthMethod> getAuthMethods(AgentProxy agent) throws Buffer.BufferException {
        ImmutableList.Builder<AuthMethod> identities = ImmutableList.builder();
        for (Identity identity : agent.getIdentities()) {
            identities.add(new AuthAgent(agent, identity));
        }
        return identities.build();
    }
}
