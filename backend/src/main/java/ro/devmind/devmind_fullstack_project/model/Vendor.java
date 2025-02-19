package ro.devmind.devmind_fullstack_project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name="vendors")
@Getter
@Setter
@RequiredArgsConstructor
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // The vendor's account (once claimed it will be linked to and user)
    @OneToOne(cascade = CascadeType.MERGE) //don't insert unnecessary users -> CascadeType.MERGE
    @JoinColumn(name="user_id", nullable = true)
    private User claimedByUser; // Null until vendor is claimed

    @Column(name = "company_name", nullable = false, unique = true)
    private String companyName;

    @Column(length = 255, unique = true)
    private String companyEmail;

    @Column(length = 255)
    private String location;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private Double rating;

    @Column(name = "profile_picture", columnDefinition = "TEXT") // Base64 profile picture
    private String profilePicture;

    @Column(name = "website_url", unique = true)
    private String websiteUrl;


    @Column(name = "phone_number", unique = true)
    private String phoneNumber;


    @Column(name = "claimed", nullable = false)
    private boolean claimed = false; // False until a vendor claims a profile created by user - true if vendor created

    @ManyToOne
    @JoinColumn(name = "created_by_userid", nullable = false)
    @JsonIgnore
    private User createdByUser;  // Track the user who initially created the vendor profile

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<VendorService> vendorServices;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
