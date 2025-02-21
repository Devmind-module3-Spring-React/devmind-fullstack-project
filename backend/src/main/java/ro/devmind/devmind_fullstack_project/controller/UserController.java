package ro.devmind.devmind_fullstack_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.devmind.devmind_fullstack_project.assembler.UserAssembler;
import ro.devmind.devmind_fullstack_project.dto.UserDto;
import ro.devmind.devmind_fullstack_project.model.User;
import ro.devmind.devmind_fullstack_project.service.UserService;

@RestController
public class UserController {

    private UserService userService;
    private UserAssembler userAssembler;



    @PostMapping("/api/users/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        User registeredUser = userService.registerUser(userDto);
        UserDto registeredUserDto = userAssembler.toModel(registeredUser);

        return new ResponseEntity<>(registeredUserDto, HttpStatus.CREATED);

    }



}
