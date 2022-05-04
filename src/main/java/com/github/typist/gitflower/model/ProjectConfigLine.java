package com.github.typist.gitflower.model;

import lombok.Data;

import java.util.List;

@Data
public class ProjectConfigLine {
    private String repoUrl;

    private List<UpgradeVersionConfigLine> upgradeVersion;

    private List<DeployConfigLine> deploy;
}
