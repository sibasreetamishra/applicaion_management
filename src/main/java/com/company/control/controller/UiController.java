
package com.company.control.controller;

import com.company.control.service.SystemdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UiController {

    private final SystemdService service;

    public UiController(SystemdService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String dashboard(Model model) throws Exception {
        model.addAttribute("apps", service.getAllDetails());
        return "dashboard";
    }

    @PostMapping("/start/{name}")
    public String start(@PathVariable String name) throws Exception {
        service.start(name);
        return "redirect:/";
    }

    @PostMapping("/stop/{name}")
    public String stop(@PathVariable String name) throws Exception {
        service.stop(name);
        return "redirect:/";
    }
}
