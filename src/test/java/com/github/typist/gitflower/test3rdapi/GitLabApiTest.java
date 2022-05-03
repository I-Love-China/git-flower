package com.github.typist.gitflower.test3rdapi;

import com.github.typist.gitflower.config.GitLabConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: zhangjl
 * @Date: 22-5-3
 * @Description:
 */
@Slf4j
@SpringBootTest
public class GitLabApiTest {
    @Autowired
    private GitLabConfig gitLabConfig;

    @Autowired
    private GitLabApi gitLabApi;

    @Test
    public void testGitLabConfig() {
        Assertions.assertNotNull(gitLabConfig);
        Assertions.assertTrue(StringUtils.isNotBlank(gitLabConfig.getUrl()));
        Assertions.assertTrue(StringUtils.isNotBlank(gitLabConfig.getToken()));
    }

    @Test
    @SneakyThrows
    public void testFrequentlyApis() {
        List<User> users = gitLabApi.getUserApi().getUsers();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(users));

        List<Project> projects = gitLabApi.getProjectApi().getProjects();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(projects));
    }
}
