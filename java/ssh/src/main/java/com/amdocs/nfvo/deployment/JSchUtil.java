package com.amdocs.nfvo.deployment;

import com.jcraft.jsch.*;
import org.apache.log4j.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by SERGEYGO on 24.11.2014.
 */
public class JSchUtil {
    private int connectionTimeout = 10000;

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(JSchUtil.class);


    public void execCommand(String host, String login, String password, String command) throws JSchException, SftpException, IOException {
        logger.info("executing at " + login + "@" + host + ": " + command);
        Session session = null;
        Channel channel = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(login, host, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect(connectionTimeout);

            channel = session.openChannel("exec");
            ChannelExec execChannel = (ChannelExec) channel;
            execChannel.setCommand(command);
            execChannel.setInputStream(null);
            //        execChannel.setOutputStream(System.out);
            execChannel.setErrStream(System.err);

            InputStream in = channel.getInputStream();

            channel.connect(connectionTimeout);

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    logger.info(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) continue;
                    logger.info("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }

    }

    public void copyTo(String host, String login, String password, String file, String path) throws JSchException, SftpException {
        logger.info("copying " + file + " to " + login+ "@" + host + ":" + path);
        Session session = null;
        Channel channel = null;
        try {
            JSch ssh = new JSch();
            session = ssh.getSession(login, host, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.put(file, path);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}
