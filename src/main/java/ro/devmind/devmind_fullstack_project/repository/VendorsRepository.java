package ro.devmind.devmind_fullstack_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.devmind.devmind_fullstack_project.model.Vendor;

public interface VendorsRepository extends JpaRepository<Vendor, Integer> {
}
