package ro.devmind.devmind_fullstack_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.devmind.devmind_fullstack_project.assembler.user.UserResponseAssembler;
import ro.devmind.devmind_fullstack_project.dto.user.UserRegisterDto;
import ro.devmind.devmind_fullstack_project.dto.user.UserResponseDto;
import ro.devmind.devmind_fullstack_project.model.User;
import ro.devmind.devmind_fullstack_project.service.user.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserResponseAssembler userResponseAssembler;



    @PostMapping("/api/v1/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        User registeredUser = userService.registerUser(userRegisterDto);
        UserResponseDto userResponseDto = userResponseAssembler.toModel(registeredUser);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

}
