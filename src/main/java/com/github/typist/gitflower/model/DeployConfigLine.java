package com.github.typist.gitflower.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class DeployConfigLine {
    private String deployFile;
    private String deployer;
    private List<Depend> depends;


    @EqualsAndHashCode
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Depend {
        private String projectName;
        private String deployFile;
    }
}
