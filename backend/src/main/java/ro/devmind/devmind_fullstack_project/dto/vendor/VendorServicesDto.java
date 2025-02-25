package ro.devmind.devmind_fullstack_project.dto.vendor;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VendorServicesDto {
    private Integer id;
    private String name;
    private String description;
    private String category;
    private Double rating;
    private VendorSummaryResponseDto vendor;
    private Integer reviewCount;
    private Double averageRating;
    private LocalDateTime createdAt;
}
