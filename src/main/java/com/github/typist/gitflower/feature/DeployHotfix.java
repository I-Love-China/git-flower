package com.github.typist.gitflower.feature;

import com.github.typist.gitflower.config.GitConfig;
import com.github.typist.gitflower.config.GitLabConfig;
import com.github.typist.gitflower.model.ProjectConfigLine;
import com.github.typist.gitflower.model.UpgradeVersionConfigLine;
import com.github.typist.gitflower.model.Version;
import com.github.typist.gitflower.versionbumper.VersionBumper;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;

@Component
public class DeployHotfix {
    @Autowired
    private GitLabConfig gitLabConfig;

    @Autowired
    private GitConfig gitConfig;

    @Resource(name = "projectConfig")
    private Map<String, ProjectConfigLine> config;

    @Autowired
    private ApplicationContext appCtx;

    @SneakyThrows
    public void process(Version newVersion) {
        List<Git> repos = Lists.newArrayListWithExpectedSize(config.size());
        for (Map.Entry<String, ProjectConfigLine> entry : config.entrySet()) {
            String projectName = entry.getKey();
            ProjectConfigLine projectConfig = entry.getValue();

            // 1. clone repo & checkout master
            Git repo = Git.cloneRepository()
                    .setCloneAllBranches(true)
                    .setURI(projectConfig.getRepoUrl())
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitLabConfig.getUsername(), gitLabConfig.getPassword()))
                    .setDirectory(new File(gitConfig.getLocalDir() + "/" + projectName))
                    .call();
            repos.add(repo);

            // 2. update the POM version
            for (UpgradeVersionConfigLine upgradeVersionConfig : projectConfig.getUpgradeVersion()) {
                File versionFile = new File(upgradeVersionConfig.getVersionFile());
                VersionBumper versionBumper = appCtx.getBean(upgradeVersionConfig.getVersionBumper(), VersionBumper.class);

                versionBumper.bump(versionFile, newVersion);
            }

            repo.add().addFilepattern(".").call();
        }

        // 3. deploy
        // todo

        // 4. commit & push
        String commitMsg = "Bump version to " + newVersion;
        for (Git repo : repos) {
            repo.add().addFilepattern(".").call();
            repo.commit()
                    .setAuthor(gitConfig.getAuthor(), gitConfig.getEmail())
                    .setMessage(commitMsg)
                    .call();
        }
    }
}
