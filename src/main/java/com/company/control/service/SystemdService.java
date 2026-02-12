
package com.company.control.service;

import com.company.control.util.CommandExecutor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SystemdService {

    private final CommandExecutor executor;
    private final List<String> services = List.of("tomcat","jetty","redis","fsib");

    public SystemdService(CommandExecutor executor) {
        this.executor = executor;
    }

    public List<Map<String,String>> getAllDetails() throws Exception {
        List<Map<String,String>> list = new ArrayList<>();

        for(String s : services) {
            Map<String,String> map = new HashMap<>();
            map.put("name", s);

            String status = executor.execute(List.of("systemctl","is-active",s));
            map.put("status", status);

            String pid = executor.execute(List.of("pgrep","-f",s));
            if(pid.isBlank()) {
                map.put("pid", "-");
                map.put("cpu", "0");
            } else {
                String firstPid = pid.split("\n")[0];
                map.put("pid", firstPid);

                String cpu = executor.execute(
                    List.of("ps","-p",firstPid,"-o","%cpu=")
                );
                map.put("cpu", cpu.trim());
            }

            list.add(map);
        }
        return list;
    }

    public void start(String name) throws Exception {
        executor.execute(List.of("sudo","systemctl","start",name));
    }

    public void stop(String name) throws Exception {
        executor.execute(List.of("sudo","systemctl","stop",name));
    }
}
