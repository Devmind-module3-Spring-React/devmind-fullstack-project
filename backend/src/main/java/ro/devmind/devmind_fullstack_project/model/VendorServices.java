package ro.devmind.devmind_fullstack_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="services")
@RequiredArgsConstructor
@Data
public class VendorServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="service_name", nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String category;

    @Column
    private Double rating;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @Column
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "chosenVendorServices")
    @JsonIgnore
    public Set<User> users;

    @OneToMany(mappedBy = "vendorServices")
    @JsonIgnore
    private Set<UserToServices> serviceRegistrationDetails;

    // Portfolio items for this service. Can be added only by Vendors
    @OneToMany(mappedBy = "vendorServices", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    //  User reviews for this service.
    @OneToMany(mappedBy = "vendorServices", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceReview> reviews;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
