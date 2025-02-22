package ro.devmind.devmind_fullstack_project.dto.vendor;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// Minimal information for vendor creation request
@Data
public class VendorCreateDto {
    @NotNull
    private String companyName;

    private String companyEmail;

    @NotNull
    private String location;

    @NotNull
    private String websiteUrl;

    private String phoneNumber;

    private Integer createdByUserId;
}
