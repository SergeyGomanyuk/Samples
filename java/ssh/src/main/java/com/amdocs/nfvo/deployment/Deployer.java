package com.amdocs.nfvo.deployment;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SERGEYGO on 24.11.2014.
 */
public class Deployer {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Deployer.class);

    public static void main(String[] args) throws Exception {
        String deploymentDescriptor = System.getProperty("deployer.descriptor");
        if(deploymentDescriptor == null) {
            logger.error("deployer.descriptor property is not set");
        }
        String rootPath = System.getProperty("deployer.rootPath");
        if(rootPath == null) {
            logger.error("deployer.rootPath property is not set");
        }

        Deployer deployer = new Deployer();
        JSchUtil jSchUtil = new JSchUtil();

        DeploymentDescriptor descriptor = new Gson().fromJson(new FileReader(deploymentDescriptor), DeploymentDescriptor.class);
        String date = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());

        for(DeploymentEntry entry : descriptor.getEntries()) {
            logger.debug("start processing " + entry.getId());
            if(entry.isDeploy()) {
                logger.info("prepare remote directory " + entry.getId());
                jSchUtil.execCommand(entry.getHost(), entry.getLogin(), entry.getPassword(), "mkdir -p " + entry.getDstPath());
                logger.info("backup " + entry.getId());
                if(entry.isUnpack()) {
                    jSchUtil.execCommand(entry.getHost(), entry.getLogin(), entry.getPassword(), "cd " + entry.getDstPath() + "; zip \"" + entry.getId() + "-" + date + ".zip\" * -r -x *.zip *.log");
                } else {
                    jSchUtil.execCommand(entry.getHost(), entry.getLogin(), entry.getPassword(), "cd " + entry.getDstPath() + "; zip \"" + entry.getId() + "-" + date + ".zip\" " + entry.getArtifact());
                }
                logger.info("copy " + entry.getId() + " to remote directory");
                jSchUtil.copyTo(entry.getHost(), entry.getLogin(), entry.getPassword(), rootPath + File.separator + entry.getSrcPath() + File.separator + entry.getArtifact(), entry.getDstPath());
                if (entry.isUnpack()) {
                    logger.debug("unpack and setup " + entry.getId());
                    new JSchUtil().execCommand(entry.getHost(), entry.getLogin(), entry.getPassword(), "cd " + entry.getDstPath() + "; unzip -o " + entry.getArtifact() + "; chmod a+x *.sh");
                }
            }
            logger.info("finish processing " + entry.getId());
        }
    }
}
