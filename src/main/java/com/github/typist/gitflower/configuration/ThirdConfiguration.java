package com.github.typist.gitflower.configuration;

import com.alibaba.fastjson.JSONObject;
import com.github.typist.gitflower.config.GitLabConfig;
import com.github.typist.gitflower.model.ProjectConfigLine;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.gitlab4j.api.GitLabApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author: zhangjl
 * @Date: 22-5-3
 * @Description:
 */
@Configuration
public class ThirdConfiguration {
    @Autowired
    private GitLabConfig gitLabConfig;

    @Value("classpath:config.json")
    private Resource configJson;

    @Bean
    public GitLabApi gitLabApi() {
        return new GitLabApi(gitLabConfig.getUrl(), gitLabConfig.getToken());
    }

    @Bean
    public CredentialsProvider credentialsProvider() {
        return new UsernamePasswordCredentialsProvider(gitLabConfig.getUsername(), gitLabConfig.getPassword());
    }

    @Bean("projectConfig")
    @SneakyThrows
    public Map<String, ProjectConfigLine> projectConfig() {
        try (InputStream in = configJson.getInputStream()) {
            JSONObject jsonObject = JSONObject.parseObject(IOUtils.toString(in, StandardCharsets.UTF_8));

            Map<String, ProjectConfigLine> ret = Maps.newHashMapWithExpectedSize(jsonObject.size());
            for (String key : jsonObject.keySet()) {
                ret.put(key, jsonObject.getObject(key, ProjectConfigLine.class));
            }
            return ret;
        }
    }
}
