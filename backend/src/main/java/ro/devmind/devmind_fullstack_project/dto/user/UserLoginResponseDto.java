package ro.devmind.devmind_fullstack_project.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponseDto {
    private String token;
    private UserResponseDto userResponseDto;
}
