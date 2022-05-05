package com.github.typist.gitflower.deployer;

import java.io.File;

/**
 * @author: zhangjl
 * @Date: 22-5-5
 * @Description:
 */
public interface Deployer {
    void deploy(File deployFile);
}
