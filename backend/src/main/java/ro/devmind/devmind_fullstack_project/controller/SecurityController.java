package ro.devmind.devmind_fullstack_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ro.devmind.devmind_fullstack_project.service.user.UserService;

@RestController
@RequestMapping("api/v1/")
public class SecurityController {
    @Autowired
    UserService userService;

//    @PostMapping("/login")
//    public String login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
//        return userService.authenticate(userLoginRequestDto.getUsername(), userLoginRequestDto.getPassword());
//    }

    @GetMapping("/whoami")
    public String protectedWhoami() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "You are: " + authentication.getName() + " with authorities: " + authentication.getAuthorities().toString();
    }
}
