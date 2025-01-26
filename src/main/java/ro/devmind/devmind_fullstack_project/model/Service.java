package ro.devmind.devmind_fullstack_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="services")
@RequiredArgsConstructor
@Data
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="service_name", nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String category;

    @Column
    private Double rating;

    @Lob
    @Column
    private Blob proofOfUse;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @Column
    private LocalDateTime createdAt;


    @ManyToMany(mappedBy = "chosenServices")
    @JsonIgnore
    public Set<User> users;

    @OneToMany(mappedBy = "service")
    @JsonIgnore
    private Set<UserToServices> serviceRegistrationDetails;



}
