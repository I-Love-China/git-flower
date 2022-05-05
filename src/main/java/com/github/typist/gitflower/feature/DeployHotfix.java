package com.github.typist.gitflower.feature;

import com.github.typist.gitflower.config.GitConfig;
import com.github.typist.gitflower.deployer.Deployer;
import com.github.typist.gitflower.model.DeployConfigLine;
import com.github.typist.gitflower.model.ProjectConfigLine;
import com.github.typist.gitflower.model.UpgradeVersionConfigLine;
import com.github.typist.gitflower.model.Version;
import com.github.typist.gitflower.versionbumper.VersionBumper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class DeployHotfix implements Command {
    public static final String KEY_NEW_VERSION = "newVersion";
    public static final String KEY_BRANCH = "branch";

    public static final Set<String> CMD_ARG_NAMES = ImmutableSet.of(KEY_NEW_VERSION, KEY_BRANCH);


    @Autowired
    private GitConfig gitConfig;

    @Resource(name = "projectConfig")
    private Map<String, ProjectConfigLine> config;

    @Autowired
    private ApplicationContext appCtx;

    @Autowired
    private CredentialsProvider credentialsProvider;

    @Override
    @SneakyThrows
    public void execute(Map<String, Object> cmdArgs) {
        Version newVersion = (Version) cmdArgs.get(KEY_NEW_VERSION);
        String branchName = (String) cmdArgs.get(KEY_BRANCH);

        List<Git> repos = Lists.newArrayListWithExpectedSize(config.size());
        for (Map.Entry<String, ProjectConfigLine> entry : config.entrySet()) {
            String projectName = entry.getKey();
            ProjectConfigLine projectConfig = entry.getValue();

            // 1. clone repo & checkout to specific branch
            Git repo = Git.cloneRepository()
                    .setCloneAllBranches(true)
                    .setURI(projectConfig.getRepoUrl())
                    .setBranch(branchName)
                    .setCredentialsProvider(credentialsProvider)
                    .setDirectory(new File(gitConfig.getLocalDir() + "/" + projectName))
                    .call();
            repos.add(repo);

            // 2. update the POM version
            for (UpgradeVersionConfigLine upgradeVersionConfig : projectConfig.getUpgradeVersion()) {
                File versionFile = new File(upgradeVersionConfig.getVersionFile());
                VersionBumper versionBumper = appCtx.getBean(upgradeVersionConfig.getVersionBumper(), VersionBumper.class);

                versionBumper.bump(versionFile, newVersion);
            }
        }

        // 3. deploy
        deploy(config, Sets.newHashSetWithExpectedSize(config.size() << 1));

        // 4. commit & push
        String commitMsg = "Bump version to " + newVersion;
        for (Git repo : repos) {
            repo.add().addFilepattern(".").call();
            repo.commit()
                    .setAuthor(gitConfig.getAuthor(), gitConfig.getEmail())
                    .setMessage(commitMsg)
                    .call();
            repo.push()
                    .setCredentialsProvider(credentialsProvider)
                    .call();
        }
    }

    @Override
    public Set<String> argNames() {
        return CMD_ARG_NAMES;
    }

    private void deploy(Map<String, ProjectConfigLine> configs, Set<DeployConfigLine.Depend> deployed) {
        for (Map.Entry<String, ProjectConfigLine> projConfig : configs.entrySet()) {
            for (DeployConfigLine deployConfigLine : projConfig.getValue().getDeploy()) {
                deploy(projConfig.getKey(), deployConfigLine, deployed);
            }
        }
    }

    private void deploy(String projectName, DeployConfigLine deployConfigLine, Set<DeployConfigLine.Depend> deployed) {
        List<DeployConfigLine.Depend> depends = deployConfigLine.getDepends();
        if (CollectionUtils.isEmpty(depends)) {
            Deployer deployer = appCtx.getBean(deployConfigLine.getDeployer(), Deployer.class);
            deployer.deploy(new File(deployConfigLine.getDeployFile()));
            deployed.add(new DeployConfigLine.Depend(projectName, deployConfigLine.getDeployFile()));
            return;
        }

        for (DeployConfigLine.Depend depend : depends) {
            if (deployed.contains(depend)) {
                continue;
            }
            deploy(
                    depend.getProjectName(),
                    // todo 性能优化
                    config.get(depend.getProjectName())
                            .getDeploy().stream()
                            .filter(d -> StringUtils.equals(d.getDeployFile(), depend.getDeployFile()))
                            .findFirst()
                            .get(),
                    deployed
            );
        }
    }
}
