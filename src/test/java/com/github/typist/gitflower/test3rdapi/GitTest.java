package com.github.typist.gitflower.test3rdapi;

import com.github.typist.gitflower.config.GitConfig;
import com.github.typist.gitflower.config.GitLabConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

/**
 * @author: zhangjl
 * @Date: 22-5-3
 * @Description:
 */
@Slf4j
@SpringBootTest
public class GitTest {
    @Autowired
    private GitConfig gitConfig;

    @Autowired
    private GitLabConfig gitLabConfig;

    // https://www.baeldung.com/jgit
    // https://stackoverflow.com/questions/51526733/spring-cloud-config-clone-on-start-http-postbuffer-issue
    @SneakyThrows
    @Test
    public void testClone() {
        Git repo = Git.cloneRepository()
                .setCloneAllBranches(true)
                .setURI("http://10.1.140.2/oms/oms-mobile-ui")
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitLabConfig.getUsername(), gitLabConfig.getPassword()))
                .setDirectory(new File(gitConfig.getLocalDir() + "/oms-mobile-ui"))
                .call();
        System.out.println(1);
    }
}
