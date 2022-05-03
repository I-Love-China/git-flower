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
@ConfigurationProperties(prefix = "git")
public class GitConfig {
    private String localDir;

    private String author;

    private String email;
}
