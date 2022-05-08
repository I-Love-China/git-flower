package com.github.typist.gitflower.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhangjl
 * @Date: 22-5-4
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Version {
    private int major;

    private int minor;

    private String patch;

    @Override
    public String toString() {
        return String.format("%s.%s%s", major, minor, patch);
    }
}
