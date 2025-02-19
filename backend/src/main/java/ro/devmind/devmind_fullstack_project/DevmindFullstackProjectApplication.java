package ro.devmind.devmind_fullstack_project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ro.devmind.devmind_fullstack_project.enums.ReviewStatus;
import ro.devmind.devmind_fullstack_project.enums.UserRoles;
import ro.devmind.devmind_fullstack_project.model.*;
import ro.devmind.devmind_fullstack_project.repository.RoleRepository;
import ro.devmind.devmind_fullstack_project.repository.UsersRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class DevmindFullstackProjectApplication implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsersRepository usersRepository;

    public static void main(String[] args) {
        SpringApplication.run(DevmindFullstackProjectApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        // Create a user
//        User user = new User();
//        user.setUsername("testuser3");
//        user.setEmail("testuser3@example3.com");
//        user.setFirstName("FirstNameTest3");
//        user.setName("User3");
//        user.setPassword("password3");
//        user.setCreatedAt(LocalDateTime.now());
//
//        Role adminRole = roleRepository.findByName(UserRoles.ADMIN);
//        Role vendorRole = roleRepository.findByName(UserRoles.VENDOR);
//
//        // Set user roles
//        Set<Role> roles = new HashSet<>();
//        roles.add(adminRole);
//        roles.add(vendorRole);
//        user.setRoles(roles);
//
//
//        // Create a vendor
//        Vendor vendor = new Vendor();
//        vendor.setUser(user);
//        vendor.setCompanyName("Test Vendor33");
//        vendor.setRating(3.3);
//
//
//        // Create a vendorService
//        VendorService vendorService = new VendorService();
//        vendorService.setName("Test VendorService3");
//        vendorService.setDescription("This is a test vendorService3.");
//        vendorService.setCategory("Test Category3");
//        vendorService.setVendor(vendor);
//        vendorService.setRating(1.3);
////      vendorService.setCreatedAt(LocalDateTime.now());
//
//        // Set chosen vendor services
//        Set<VendorService> chosenVendorServices = new HashSet<>();
//        chosenVendorServices.add(vendorService);
//        user.setChosenVendorServices(chosenVendorServices);
//
//        // Persist the user, vendor, and vendorService ->> see cascading from User, UserToServices, Vendor, VendorServices
//        entityManager.persist(user);
//
//        // Create a vendorService review
//        ServiceReview review = new ServiceReview();
//        review.setVendorService(vendorService);
//        review.setUser(user);
//        review.setTitle("NOT Great VendorService3!");
//        review.setBody("The vendorService3 was BAD.");
//        review.setRating(5);
//        review.setProofOfUse(null);
//        review.setProofFilename(null);
//        review.setProofContentType(null);
//        review.setServiceDate(LocalDateTime.now());
//        review.setStatus(ReviewStatus.PENDING);
//        review.setCreatedAt(LocalDateTime.now());
//        review.setUpdatedAt(LocalDateTime.now());


//        entityManager.persist(review);


    }

}
