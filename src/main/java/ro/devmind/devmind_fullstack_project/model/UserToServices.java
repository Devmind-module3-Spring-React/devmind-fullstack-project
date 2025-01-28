package ro.devmind.devmind_fullstack_project.model;

import jakarta.persistence.*;
import ro.devmind.devmind_fullstack_project.enums.ServiceStatus;

import java.util.Date;

@Entity
@Table(name="users_to_services")
public class UserToServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private VendorService vendorService;

    @Column
    private Date serviceDate;

    @Column
    private ServiceStatus status;


}
