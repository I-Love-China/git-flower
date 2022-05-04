package com.github.typist.gitflower.versionbumper;

import com.github.typist.gitflower.model.Version;

import java.io.File;

/**
 * @author: zhangjl
 * @Date: 22-5-3
 * @Description:
 */
public interface VersionBumper {
    /**
     * @param versionFile 保存
     * @param newVersion
     */
    void bump(File versionFile, Version newVersion);
}
