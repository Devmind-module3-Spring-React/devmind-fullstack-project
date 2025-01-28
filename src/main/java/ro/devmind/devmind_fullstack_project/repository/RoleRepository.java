package ro.devmind.devmind_fullstack_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.devmind.devmind_fullstack_project.enums.UserRoles;
import ro.devmind.devmind_fullstack_project.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findByName(UserRoles name);
}
