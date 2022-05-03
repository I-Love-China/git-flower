package com.github.typist.gitflower.test3rdapi;

import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author: zhangjl
 * @Date: 22-5-3
 * @Description:
 */
public class JProcTest {
    @Test
    public void testEcho() {
        ProcResult result = new ProcBuilder("echo")
                .withArg("Hello World!")
                .run();

        assertEquals("Hello World!\n", result.getOutputString());
        assertEquals(0, result.getExitValue());
        assertEquals("echo 'Hello World!'", result.getProcString());
    }
}
