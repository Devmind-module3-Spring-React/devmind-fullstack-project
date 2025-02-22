package ro.devmind.devmind_fullstack_project.assembler.user;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import ro.devmind.devmind_fullstack_project.controller.UserController;
import ro.devmind.devmind_fullstack_project.dto.user.UserResponseDto;
import ro.devmind.devmind_fullstack_project.model.User;

@Component
public class UserResponseAssembler extends RepresentationModelAssemblerSupport<User, UserResponseDto> {

    public UserResponseAssembler() {
        super(UserController.class, UserResponseDto.class);
    }

    @Override
    public UserResponseDto toModel(User user) {
        UserResponseDto userResponseDto = instantiateModel(user);

        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setName(user.getName());

        return userResponseDto;
    }
}

