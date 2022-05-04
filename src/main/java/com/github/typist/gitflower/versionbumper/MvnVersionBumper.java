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
public class MvnVersionBumper implements VersionBumper {
    @Override
    public void bump(File versionFile, Version newVersion) {
        ProcResult mvnRes = new ProcBuilder("mvn")
                .withArg("versions:set -DnewVersion=" + newVersion)
                .withWorkingDirectory(versionFile.getParentFile())
                .run();
        Preconditions.checkState(mvnRes.getExitValue() == 0, "mvn versions:set fail @" + versionFile);
    }
}
