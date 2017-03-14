package com.leekli.demo.py4j;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.Map;

public class ProcessBuilderDemo {
    public static void main(String args[]) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("python", "myArg1", "myArg2");
        Map<String, String> env = pb.environment();
        env.put("VAR1", "myValue");
        env.remove("OTHERVAR");
        env.put("VAR2", env.get("VAR1") + "suffix");
        File  dir = new File("mydir");
        System.out.println(dir.exists());
        pb.directory();
        File log = new File("log");
        pb.redirectErrorStream(true);
        pb.redirectOutput(Redirect.appendTo(log));
        Process p = pb.start();
        assert pb.redirectInput() == Redirect.PIPE;
        assert pb.redirectOutput().file() == log;
        assert p.getInputStream().read() == -1;
    }
}
