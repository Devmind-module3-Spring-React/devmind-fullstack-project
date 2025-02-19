package ro.devmind.devmind_fullstack_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.devmind.devmind_fullstack_project.model.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

        Optional<User> findByUsername(String username);
        Optional<User> findByEmail(String email);


}
