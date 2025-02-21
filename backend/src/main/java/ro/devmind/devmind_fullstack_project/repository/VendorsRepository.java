package ro.devmind.devmind_fullstack_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.devmind.devmind_fullstack_project.model.Vendor;
import java.util.Optional;

public interface VendorsRepository extends JpaRepository<Vendor, Integer> {
    Optional<Vendor> findById(Integer id);
    boolean existsByCompanyName(String companyName);
    boolean existsByCompanyEmail(String companyEmail);
}
