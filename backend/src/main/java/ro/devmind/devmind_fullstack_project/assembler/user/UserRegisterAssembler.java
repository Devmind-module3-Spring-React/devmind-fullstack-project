package ro.devmind.devmind_fullstack_project.assembler.user;

import lombok.NonNull;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ro.devmind.devmind_fullstack_project.dto.user.UserRegisterDto;
import ro.devmind.devmind_fullstack_project.model.User;
import java.util.List;

@Component
public class UserRegisterAssembler implements RepresentationModelAssembler<User, UserRegisterDto> {

    //toModel == convert an entity into a representation model -> Spring HATEOAS naming convention
    @Override
    public @NonNull UserRegisterDto toModel(@NonNull User entity) {
        UserRegisterDto userRegisterDto = new UserRegisterDto();

        userRegisterDto.setUsername(entity.getUsername());
        userRegisterDto.setEmail(entity.getEmail());
        userRegisterDto.setPassword(entity.getPassword());
        userRegisterDto.setName(entity.getName());

        if (null != entity.getFirstName()) {
            userRegisterDto.setFirstName(entity.getFirstName());
        }

        return userRegisterDto;
    }

    public List<UserRegisterDto> toModelList(@NonNull List<User> entities) {
       return entities.stream().map(this::toModel).toList();
    }
}
