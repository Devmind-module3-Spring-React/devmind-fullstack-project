package ro.devmind.devmind_fullstack_project.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.devmind.devmind_fullstack_project.enums.ReviewStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service_reviews")
@Getter
@Setter
public class ServiceReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private VendorServices vendorServices;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column(nullable = false)
    private Integer rating;

    @Column(name="upvotes")
    private Integer upvotes;

    @Column(name="downvotes")
    private Integer downvotes;

    @OneToMany(mappedBy = "serviceReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ReviewStatus status = ReviewStatus.PENDING;

    //Instead of permanently deleting - add deleted column for softDeleting reviews
    @Column
    private boolean deleted = false;


    public void softDelete() {
        this.deleted = true;
    }

    // Can restore review
    public void restore() {
        this.deleted = false;
    }

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
