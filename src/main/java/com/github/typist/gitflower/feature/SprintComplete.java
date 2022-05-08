package com.github.typist.gitflower.feature;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: zhangjl
 * @Date: 22-5-3
 * @Description:
 */
@Component
public class SprintComplete {
    @Autowired
    private GitLabApi gitLabApi;

    public void process(Project project) {
        // 1. release 分支打 tag 备份

        // 2. master 分支打 tag 备份

        // 3. merge release into master --no-ff

        // 4. 升级 POM 版本

        // 5. deploy api
    }
}
