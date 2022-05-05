package com.github.typist.gitflower.deployer;

import com.google.common.base.Preconditions;
import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author: zhangjl
 * @Date: 22-5-5
 * @Description:
 */
@Component("mvnDeployer")
public class MvnDeployer implements Deployer {
    @Override
    public void deploy(File deployFile) {
        // https://stackoverflow.com/questions/4023597/specify-pom-xml-in-mvn-command-and-mix-goals-of-other-project
        ProcBuilder procBuilder = new ProcBuilder("mvn")
                .withArgs("clean", "install", "-U", "-f", deployFile.getAbsolutePath())
                .withNoTimeout();
        System.out.println(procBuilder.getCommandLine());
        ProcResult mvnRes = procBuilder.run();
        Preconditions.checkState(mvnRes.getExitValue() == 0, "mvn deploy fail @" + deployFile);
    }
}
