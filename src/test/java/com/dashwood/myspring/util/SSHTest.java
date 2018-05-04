package com.dashwood.myspring.util;

import com.google.common.base.Optional;
import com.google.common.net.HostAndPort;

import com.jcraft.jsch.*;
import com.jcraft.jsch.agentproxy.AgentProxyException;
import com.jcraft.jsch.agentproxy.Buffer;
import com.jcraft.jsch.agentproxy.Connector;
import com.jcraft.jsch.agentproxy.connector.SSHAgentConnector;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.DisconnectReason;
import net.schmizz.sshj.transport.TransportException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.sshj.SSHClientConnection;
import org.jclouds.sshj.SshjSshClient;
import org.junit.Test;

import java.io.*;

public class SSHTest {

    @Test
    public void test1() throws IOException {

//        ssh.authPassword("root", "123456");
//        if (ssh.isConnected()) {
//            System.out.println("连接成功");
//
//        }//        SSHClient ssh = new SSHClient();
//        try {
//            ssh.connect("192.168.30.14", 22);
//        } catch (TransportException e) {
//            if (e.getDisconnectReason() == DisconnectReason.HOST_KEY_NOT_VERIFIABLE) {
//                String msg = e.getMessage();
//                String[] split = msg.split("`");
//                String vc = split[3];
//                ssh = new SSHClient();
//                ssh.addHostKeyVerifier(vc);
//                ssh.connect("192.168.30.14", 22);
//            } else {
//                throw e;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



    }

    @Test
    public void test2() {
        HostAndPort h = HostAndPort.fromHost("192.168.30.14");
        System.out.println(h.getHost());
        Optional<Connector> agentConn = Optional.absent();
        SSHClientConnection connection = SSHClientConnection.builder()
                .hostAndPort(h)
                .agentConnector(agentConn)
                .loginCredentials(LoginCredentials.builder().user("root").password("123456").build())
                .build();
        try {
            SSHClient ssh = new SSHClientCreate(connection).create();
            if (ssh.isConnected()) {
                System.out.println("连接成功");

//                ssh.registerX11Forwarder();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Session session;
    @Test
    public void test3() throws JSchException, IOException, InterruptedException {
        JSch jSch = new JSch();
        session = jSch.getSession("root", "192.168.30.14", 22);
        UserInfo userInfo = new UserInfo() {
            @Override
            public String getPassphrase() {
                System.out.println("getPassphrase");
                return null;
            }
            @Override
            public String getPassword() {
                System.out.println("getPassword");
                return null;
            }
            @Override
            public boolean promptPassword(String s) {
                System.out.println("promptPassword:"+s);
                return false;
            }
            @Override
            public boolean promptPassphrase(String s) {
                System.out.println("promptPassphrase:"+s);
                return false;
            }
            @Override
            public boolean promptYesNo(String s) {
                System.out.println("promptYesNo:"+s);
                return true;//notice here!
            }
            @Override
            public void showMessage(String s) {
                System.out.println("showMessage:"+s);
            }
        };

        session.setUserInfo(userInfo);
        session.setPassword("123456");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(5000);
        if (session.isConnected()) {

            System.out.println("连接成功");
            Channel channel = session.openChannel("shell");

            OutputStream inputstream_for_the_channel = channel.getOutputStream();
            PrintStream commander = new PrintStream(inputstream_for_the_channel, true);

            channel.setOutputStream(System.out, true);

            channel.connect();

            commander.println("ll");
            commander.println("cd /");
            commander.println("ls -la");
            commander.println("exit");
            commander.close();

            do {
                Thread.sleep(1000);
            } while(!channel.isEOF());

            session.disconnect();
//            Channel channel=session.openChannel("exec");
//            System.out.println(commend(channel, "cd /"));
//            System.out.println(commend(channel, "ls"));
////            channel.disconnect();
//            session.disconnect();
        }

    }

    public static String commend(Channel channel, String commend) throws IOException, JSchException {

        ((ChannelExec)channel).setCommand(commend);


        // X Forwarding
        // channel.setXForwarding(true);

        //channel.setInputStream(System.in);
        channel.setInputStream(null);

        //channel.setOutputStream(System.out);

        //FileOutputStream fos=new FileOutputStream("/tmp/stderr");
        //((ChannelExec)channel).setErrStream(fos);
        ((ChannelExec)channel).setErrStream(System.err);

        InputStream in=channel.getInputStream();

        channel.connect();

        System.out.println(">>>" + org.apache.commons.io.IOUtils.toString(channel.getInputStream()));
        channel.disconnect();
        return org.apache.commons.io.IOUtils.toString(channel.getInputStream());
    }

}
