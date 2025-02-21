package ro.devmind.devmind_fullstack_project.dto.vendor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null values in response
public class VendorResponseDto extends RepresentationModel<VendorResponseDto> {
    private Integer id;
    private Integer claimedByUserId; // Integer, not User as in entity
    private String companyName;
    private String companyEmail;
    private String location;
    private String description;
    private Double rating;
    private String profilePicture;
    private String websiteUrl;
    private String phoneNumber;
    private boolean claimed;
    private Integer createdByUserId; // Integer, not User

}
