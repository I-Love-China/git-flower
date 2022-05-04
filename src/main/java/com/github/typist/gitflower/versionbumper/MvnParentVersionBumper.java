package com.github.typist.gitflower.versionbumper;

import com.github.typist.gitflower.model.Version;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author: zhangjl
 * @Date: 22-5-4
 * @Description:
 */
@Component
public class MvnParentVersionBumper implements VersionBumper {
    @Override
    public void bump(File versionFile, Version newVersion) {

    }
}
