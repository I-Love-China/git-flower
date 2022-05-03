package com.github.typist.gitflower.configuration;

import com.github.typist.gitflower.config.GitLabConfig;
import org.gitlab4j.api.GitLabApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhangjl
 * @Date: 22-5-3
 * @Description:
 */
@Configuration
public class ThirdConfiguration {
    @Autowired
    private GitLabConfig gitLabConfig;

    @Bean
    public GitLabApi gitLabApi() {
        return new GitLabApi(gitLabConfig.getUrl(), gitLabConfig.getToken());
    }
}
