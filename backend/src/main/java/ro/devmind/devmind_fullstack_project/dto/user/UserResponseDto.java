package ro.devmind.devmind_fullstack_project.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class UserResponseDto extends RepresentationModel<UserResponseDto> {
    private String username;
    private String email;
    private String firstName;
    private String name;
}

