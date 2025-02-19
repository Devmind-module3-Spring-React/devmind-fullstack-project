package ro.devmind.devmind_fullstack_project.assembler;

import lombok.NonNull;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ro.devmind.devmind_fullstack_project.dto.UserDto;
import ro.devmind.devmind_fullstack_project.model.User;
import java.util.List;

@Component
public class UserAssembler implements RepresentationModelAssembler<User, UserDto> {

    //toModel == convert an entity into a representation model -> Spring HATEOAS naming convention
    @Override
    public @NonNull UserDto toModel(@NonNull User entity) {
        UserDto userDto = new UserDto();

        userDto.setUsername(entity.getUsername());
        userDto.setEmail(entity.getEmail());
        userDto.setPassword(entity.getPassword());
        userDto.setName(entity.getName());

        if (null != entity.getFirstName()) {
            userDto.setFirstName(entity.getFirstName());
        }

        return userDto;
    }

    public List<UserDto> toModelList(@NonNull List<User> entities) {
       return entities.stream().map(this::toModel).toList();
    }
}
