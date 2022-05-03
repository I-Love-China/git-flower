package com.github.typist.gitflower.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: zhangjl
 * @Date: 22-5-3
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "gitlab")
public class GitLabConfig {
    private String url;

    // 通过环境变量设置
    private String token;
}
