package ro.devmind.devmind_fullstack_project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ro.devmind.devmind_fullstack_project.model.Service;
import ro.devmind.devmind_fullstack_project.model.User;
import ro.devmind.devmind_fullstack_project.model.Vendor;

import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class DevmindFullstackProjectApplication implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(DevmindFullstackProjectApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        User user1 = new User();
//        user1.setFirstName("Johna");
//        user1.setName("Doea");
//        user1.setEmail("blaa@blablablaa.com");
//        user1.setPassword("passworda");
//
////
//        Service service1 = new Service();
//        service1.setName("fotografie_service1");
//        service1.setDescription("ofer servicii de fotografiere");
//        service1.setVendor(entityManager.find(Vendor.class, 1));
//        entityManager.persist(service1);


//        user1.setChosenServices(Set.of(service1));
//        entityManager.persist(user1);
//		user1.setChosenServices(new HashSet<>());
//		user1.getChosenServices().add(service1);
//		System.out.println(user1.getChosenServices().toString());
//
//
//
//
//		entityManager.persist(user1);
//		entityManager.persist(service1);


		// Create and persist a new user
		User user1 = new User();
		user1.setFirstName("John");
		user1.setName("Doe");
		user1.setEmail("johnDoe@blablablaa.com");
		user1.setPassword("password");
		entityManager.persist(user1);

		// Create and persist a new vendor
		Vendor vendor1 = new Vendor();
		vendor1.setUser(user1);
		vendor1.setCompanyName("Doe John Photography");
		entityManager.persist(vendor1);

		// Create a new service and associate it with the vendor
		Service service1 = new Service();
		service1.setName("fotografie_service John Doe");
		service1.setDescription("ofer servicii de fotografiere by John Doe");
		service1.setVendor(vendor1); // Set the vendor for the service

		// Set the relationship between user and service
		user1.setChosenServices(Set.of(service1));

		// Persist the service (cascading will persist the service)
		entityManager.persist(service1);

    }

}
