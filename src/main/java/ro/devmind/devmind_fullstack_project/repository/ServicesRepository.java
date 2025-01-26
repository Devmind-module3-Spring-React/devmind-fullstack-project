package ro.devmind.devmind_fullstack_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.devmind.devmind_fullstack_project.model.Service;

@Repository
public interface ServicesRepository extends JpaRepository<Service, Integer> {
}
