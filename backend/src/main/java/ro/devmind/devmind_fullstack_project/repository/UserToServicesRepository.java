package ro.devmind.devmind_fullstack_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.devmind.devmind_fullstack_project.model.ServiceReview;
import ro.devmind.devmind_fullstack_project.model.UserToServices;

import java.util.List;

public interface UserToServicesRepository extends JpaRepository<UserToServices, Integer> {
}
