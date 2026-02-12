
package com.company.control.util;

import org.springframework.stereotype.Component;
import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class CommandExecutor {

    public String execute(List<String> command) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        Process process = builder.start();

        if (!process.waitFor(30, TimeUnit.SECONDS)) {
            process.destroy();
            throw new RuntimeException("Command timeout");
        }

        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
