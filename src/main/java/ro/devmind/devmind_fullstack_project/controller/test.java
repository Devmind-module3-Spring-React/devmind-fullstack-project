package ro.devmind.devmind_fullstack_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class test {

    @GetMapping("/test")
    public String test() {
        return "home";
    }
}
