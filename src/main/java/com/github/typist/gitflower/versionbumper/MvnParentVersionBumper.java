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
@Component("mvnParentVersionBumper")
public class MvnParentVersionBumper implements VersionBumper {
    @Override
    public void bump(File versionFile, Version newVersion) {
        // https://stackoverflow.com/questions/39449275/update-parent-version-in-a-maven-projects-module

        ProcBuilder procBuilder =
                new ProcBuilder("mvn")
                        .withArgs(
                                "versions:update-parent",
                                "-DparentVersion=[" + newVersion + "]",
                                "-DallowSnapshots=true",
                                "-f", versionFile.getAbsolutePath()
                        )
                        .withNoTimeout();
        System.out.println(procBuilder.getCommandLine());
        ProcResult mvnRes = procBuilder.run();
        Preconditions.checkState(mvnRes.getExitValue() == 0, "mvn versions:update-parent fail @" + versionFile);
    }
}
