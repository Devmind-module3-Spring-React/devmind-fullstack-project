package ro.devmind.devmind_fullstack_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.devmind.devmind_fullstack_project.model.VendorServices;

import java.util.List;

@Repository
public interface VendorServicesRepository extends JpaRepository<VendorServices, Integer> {
    List<VendorServices> findByVendor_Id(Integer id);
}
