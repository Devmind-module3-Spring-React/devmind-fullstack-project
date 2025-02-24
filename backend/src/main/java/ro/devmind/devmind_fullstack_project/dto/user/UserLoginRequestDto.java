package ro.devmind.devmind_fullstack_project.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
