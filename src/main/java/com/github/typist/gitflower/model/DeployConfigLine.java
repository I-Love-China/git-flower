package com.github.typist.gitflower.model;

import lombok.Data;

import java.util.List;

@Data
public class DeployConfigLine {
    private String deployFile;
    private String deployer;
    private List<Depend> depends;

    @Data
    public static class Depend {
        private String projectName;
        private String deployFile;
    }
}
