package com.github.typist.gitflower.controller;

import com.github.typist.gitflower.feature.DeployHotfix;
import com.github.typist.gitflower.model.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangjl
 * @Date: 22-5-5
 * @Description:
 */
@RestController
@RequestMapping("/")
public class TestController {
    @Autowired
    private DeployHotfix deployHotfix;

    @GetMapping("deploy")
    public void testDeploy() {
        Map<String, Object> args = new HashMap<>();
        args.put(DeployHotfix.KEY_BRANCH, "master");
        args.put(DeployHotfix.KEY_NEW_VERSION, new Version(1000, 4, "-yyy-SNAPSHOT"));
        deployHotfix.execute(args);
    }
}
