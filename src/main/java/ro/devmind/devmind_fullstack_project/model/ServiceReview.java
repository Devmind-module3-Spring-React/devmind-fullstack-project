package ro.devmind.devmind_fullstack_project.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.devmind.devmind_fullstack_project.enums.ReviewStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_reviews")
@Getter
@Setter
public class ServiceReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private VendorService vendorService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column(nullable = false)
    private Integer rating;

    @Lob
    private byte[] proofOfUse;

    private String proofFilename;

    private String proofContentType;

    private LocalDateTime serviceDate;  // Added field for vendorService date

    @Enumerated(EnumType.STRING)
    private ReviewStatus status = ReviewStatus.PENDING;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
