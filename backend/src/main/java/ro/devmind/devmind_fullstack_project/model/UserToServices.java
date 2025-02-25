package ro.devmind.devmind_fullstack_project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.devmind.devmind_fullstack_project.enums.ServiceStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users_to_services")
@Getter
@Setter
public class UserToServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private VendorServices vendorServices;

    @Column
    private Date dateOfService;

    @Enumerated(EnumType.STRING)
    @Column
    private ServiceStatus status; //BOOKED, USED, CANCELLED

    @OneToMany(mappedBy = "userToServices", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Attachment> proofOfUse = new ArrayList<>();

}
