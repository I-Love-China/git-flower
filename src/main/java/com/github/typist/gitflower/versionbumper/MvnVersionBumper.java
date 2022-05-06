package com.github.typist.gitflower.versionbumper;

import com.github.typist.gitflower.model.Version;
import com.google.common.base.Preconditions;
import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;
import org.springframework.stereotype.Component;

import java.io.File;


/**
 * @author: zhangjl
 * @Date: 22-5-4
 * @Description:
 */
@Component("mvnVersionBumper")
public class MvnVersionBumper implements VersionBumper {
    @Override
    public void bump(File versionFile, Version newVersion) {

        ProcBuilder procBuilder = new ProcBuilder("mvn")
                .withArgs("versions:set", "-DnewVersion=" + newVersion, "-f", versionFile.getAbsolutePath())
                .withNoTimeout();
        System.out.println(procBuilder.getCommandLine());
        ProcResult mvnRes = procBuilder.run();
        Preconditions.checkState(mvnRes.getExitValue() == 0, "mvn versions:set fail @" + versionFile);
    }
}
