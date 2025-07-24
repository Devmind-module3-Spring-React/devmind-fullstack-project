package ro.devmind.devmind_fullstack_project.assembler.review;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ro.devmind.devmind_fullstack_project.dto.review.ServiceReviewDto;
import ro.devmind.devmind_fullstack_project.model.ServiceReview;

@Component
public class ServiceReviewAssembler {

    public @NonNull ServiceReviewDto toDto(@NonNull ServiceReview serviceReview) {
        ServiceReviewDto serviceReviewDto = new ServiceReviewDto();
        serviceReviewDto.setId(serviceReview.getId());
        serviceReviewDto.setTitle(serviceReview.getTitle());
        serviceReviewDto.setBody(serviceReview.getBody());
        serviceReviewDto.setUsername(serviceReview.getUser().getUsername());
        serviceReviewDto.setRating(serviceReview.getRating());
        serviceReviewDto.setCreatedAt(serviceReview.getCreatedAt());
        serviceReviewDto.setServiceId(serviceReview.getVendorServices().getId());
        serviceReviewDto.setUserId(serviceReview.getUser().getId());

        return serviceReviewDto;
    }
}
