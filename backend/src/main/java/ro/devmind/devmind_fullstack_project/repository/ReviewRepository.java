package ro.devmind.devmind_fullstack_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.devmind.devmind_fullstack_project.model.ServiceReview;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ServiceReview, Integer> {
    public List<ServiceReview> findByVendorServices_Id(int id);
}
