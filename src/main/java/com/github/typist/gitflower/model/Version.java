package com.github.typist.gitflower.model;

import lombok.Data;

/**
 * @author: zhangjl
 * @Date: 22-5-4
 * @Description:
 */
@Data
public class Version {
    private int major;

    private int minor;

    private String patch;
}
