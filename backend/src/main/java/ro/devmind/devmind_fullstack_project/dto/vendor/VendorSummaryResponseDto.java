package ro.devmind.devmind_fullstack_project.dto.vendor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null values in response
public class VendorSummaryResponseDto extends RepresentationModel<VendorSummaryResponseDto> {
    private Integer id;
    private String companyName;
    private String location;
    private Double rating;
    private String websiteUrl;
    private String phoneNumber;
    private String profilePicture;
}
