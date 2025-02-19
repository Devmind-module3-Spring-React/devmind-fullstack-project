package ro.devmind.devmind_fullstack_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.devmind.devmind_fullstack_project.dto.UserDto;
import ro.devmind.devmind_fullstack_project.model.User;
import ro.devmind.devmind_fullstack_project.repository.UsersRepository;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UsersRepository userRepository;

    public User registerUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setName(userDto.getName());

        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}

