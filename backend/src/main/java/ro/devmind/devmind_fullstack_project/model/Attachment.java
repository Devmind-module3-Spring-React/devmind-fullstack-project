package ro.devmind.devmind_fullstack_project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ro.devmind.devmind_fullstack_project.enums.AttachmentType;

import java.time.LocalDateTime;

@Entity
@Table(name = "attachments")
@Data
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String fileData; // Base64 encoded file

    @Column(nullable = false, length = 50)
    private String fileType; // E.g.: "image/png"

    @Column(nullable = false)
    private Long fileSize;

    @Column
    private String originalFileName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private AttachmentType attachmentType; // PROOF_OF_USE, VENDOR_PORTFOLIO, USER_UPLOADED_IMAGE

    @ManyToOne
    @JoinColumn (name = "vendor_service_id")
    @JsonBackReference
    private VendorServices vendorServices; //populate this column if vendor Portfolio attachment

    @ManyToOne
    @JoinColumn(name = "user_to_services_id", nullable = true)
    @JsonBackReference
    private UserToServices userToServices; //populate this column if Proof of use

    @ManyToOne
    @JoinColumn(name = "service_review_id", nullable = true)
    private ServiceReview serviceReview; //populate this column if user Review attachment

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User uploadedBy;

    @Column
    @NotNull
    @Size(min = 1, max = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
