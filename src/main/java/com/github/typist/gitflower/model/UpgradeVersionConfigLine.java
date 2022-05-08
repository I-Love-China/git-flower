package com.github.typist.gitflower.model;

import lombok.Data;

@Data
public class UpgradeVersionConfigLine {
    private String versionFile;

    private String versionBumper;
}
