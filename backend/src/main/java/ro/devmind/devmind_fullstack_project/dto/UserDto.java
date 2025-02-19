package ro.devmind.devmind_fullstack_project.dto;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class UserDto extends RepresentationModel<UserDto> {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String name;
}
