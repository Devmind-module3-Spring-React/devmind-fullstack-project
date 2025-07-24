package ro.devmind.devmind_fullstack_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ro.devmind.devmind_fullstack_project.assembler.user.UserResponseAssembler;
import ro.devmind.devmind_fullstack_project.dto.user.UserLoginRequestDto;
import ro.devmind.devmind_fullstack_project.dto.user.UserLoginResponseDto;
import ro.devmind.devmind_fullstack_project.dto.user.UserResponseDto;
import ro.devmind.devmind_fullstack_project.model.User;
import ro.devmind.devmind_fullstack_project.service.user.UserService;

@RestController
@RequestMapping("api/v1/")
public class SecurityController {
    @Autowired
    UserService userService;

    @Autowired
    private UserResponseAssembler userResponseAssembler;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> loginUser(@RequestBody UserLoginRequestDto loginDto) {
        String token = userService.authenticate(loginDto.getUsername(), loginDto.getPassword());
        User user = userService.validateUser(token);
        UserResponseDto userResponseDto = userResponseAssembler.toModel(user);
        return new ResponseEntity<>(new UserLoginResponseDto(token, userResponseDto), HttpStatus.OK);

    }

    // persist user login in multiple tabs - validate jwt token and return the user again
    @GetMapping("/validate-token")
    public ResponseEntity<UserResponseDto> validateToken(@RequestHeader("Authorization") String authHeader) {
        // Extract token from "Bearer <token>"
        String token = authHeader.substring(7);
        User user = userService.validateUser(token);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserResponseDto userResponseDto = userResponseAssembler.toModel(user);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/whoami")
    public String protectedWhoami() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "You are: " + authentication.getName() + " with authorities: " + authentication.getAuthorities().toString();
    }
}
