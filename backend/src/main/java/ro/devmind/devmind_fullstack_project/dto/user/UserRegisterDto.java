package ro.devmind.devmind_fullstack_project.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class UserRegisterDto extends RepresentationModel<UserRegisterDto> {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String name;
}
