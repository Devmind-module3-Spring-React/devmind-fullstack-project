package ro.devmind.devmind_fullstack_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.devmind.devmind_fullstack_project.assembler.user.UserRegisterAssembler;
import ro.devmind.devmind_fullstack_project.assembler.user.UserResponseAssembler;
import ro.devmind.devmind_fullstack_project.dto.user.UserRegisterDto;
import ro.devmind.devmind_fullstack_project.dto.user.UserResponseDto;
import ro.devmind.devmind_fullstack_project.model.User;
import ro.devmind.devmind_fullstack_project.service.UserService;

@RestController
public class UserController {

    private UserService userService;
    private UserRegisterAssembler userRegisterAssembler;
    private UserResponseAssembler userResponseAssembler;



    @PostMapping("/api/users/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        User registeredUser = userService.registerUser(userRegisterDto);
        UserResponseDto userResponseDto = userResponseAssembler.toModel(registeredUser);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);

    }



}
