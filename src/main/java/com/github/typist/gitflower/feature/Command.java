package com.github.typist.gitflower.feature;

import java.util.Map;
import java.util.Set;

public interface Command {
    void execute(Map<String, Object> cmdArgs);

    Set<String> argNames();
}
