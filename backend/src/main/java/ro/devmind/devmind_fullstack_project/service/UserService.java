package ro.devmind.devmind_fullstack_project.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.devmind.devmind_fullstack_project.dto.user.UserRegisterDto;
import ro.devmind.devmind_fullstack_project.enums.UserRoles;
import ro.devmind.devmind_fullstack_project.model.Role;
import ro.devmind.devmind_fullstack_project.model.User;
import ro.devmind.devmind_fullstack_project.repository.UsersRepository;
import ro.devmind.devmind_fullstack_project.springJWTauth.JwtService;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public User registerUser(UserRegisterDto userRegisterDto) {
        if (userRepository.findByUsername(userRegisterDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(userRegisterDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setEmail(userRegisterDto.getEmail());
        user.setFirstName(userRegisterDto.getFirstName());
        // TODO: remove Set default role to user
        Role defaultRole = new Role();
        defaultRole.setName(UserRoles.USER); // Set the default role
        user.setRoles(Set.of(defaultRole));


        return userRepository.save(user);
    }

    public String authenticate(@NotBlank String username, @NotBlank String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtService.createToken(username);
        }
        throw new UsernameNotFoundException("Username or password is incorrect");
    }

    public User validateUser(String bearerToken) {
        String username = jwtService.validateToken(bearerToken);
        return userRepository.findByUsername(username).orElse(null);
    }
}

