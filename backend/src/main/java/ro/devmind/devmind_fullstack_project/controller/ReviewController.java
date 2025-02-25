package ro.devmind.devmind_fullstack_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.devmind.devmind_fullstack_project.dto.review.ServiceReviewDto;
import ro.devmind.devmind_fullstack_project.model.ServiceReview;
import ro.devmind.devmind_fullstack_project.model.User;
import ro.devmind.devmind_fullstack_project.model.Vendor;
import ro.devmind.devmind_fullstack_project.model.VendorServices;
import ro.devmind.devmind_fullstack_project.repository.ReviewRepository;
import ro.devmind.devmind_fullstack_project.repository.VendorServicesRepository;
import ro.devmind.devmind_fullstack_project.repository.UsersRepository;
import ro.devmind.devmind_fullstack_project.repository.VendorsRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private VendorServicesRepository vendorServicesRepository;
    @Autowired
    private VendorsRepository vendorRepository;

//    Extract the username from SecurityContext ->> see JwtFilter SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), null, parseAuthorities(user.getRoles())));
    @PostMapping("/create-new-review")
    public ResponseEntity<ServiceReviewDto> writeReview(@RequestBody ServiceReviewDto serviceReviewDto, @AuthenticationPrincipal String userName) {
        ServiceReview serviceReview = new ServiceReview();
        User userEntity = userRepository.findByUsername(userName).get();

        serviceReview.setUser(userEntity);
        serviceReview.setRating(serviceReviewDto.getRating());
        serviceReview.setTitle(serviceReviewDto.getTitle());
        serviceReview.setBody(serviceReviewDto.getBody());
        VendorServices vendorService = vendorServicesRepository.findById(serviceReviewDto.getServiceId()).get();
        serviceReview.setVendorServices(vendorService);

        reviewRepository.save(serviceReview);

        // calculate service rating based on average rating from reviews
        List<ServiceReview> reviews = reviewRepository.findByVendorServices_Id(vendorService.getId());

        if (!reviews.isEmpty()) {
            double averageRating = reviews.stream()
                    .mapToDouble(ServiceReview::getRating)
                    .average()
                    .orElse(0.0);

            vendorService.setRating(averageRating); // Set average rating
            vendorServicesRepository.save(vendorService);
        }
        Vendor currentVendor = vendorService.getVendor();

        //Set Average rating for vendor depending on all its service reviews

        List<VendorServices> services = vendorServicesRepository.findByVendor_Id(currentVendor.getId());

        // Use only the services that have at least one review
        List<VendorServices> ratedServices = services.stream()
                .filter(service -> service.getRating() != null)
                .toList();

        if (!ratedServices.isEmpty()) {
            double overallVendorRating = ratedServices.stream()
                    .mapToDouble(VendorServices::getRating)
                    .average()
                    .orElse(0.0);

            currentVendor.setRating(overallVendorRating); // Assuming Vendor has a 'rating' field
            vendorRepository.save(currentVendor);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(serviceReviewDto);
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<ServiceReviewDto>> getServiceReviews(@PathVariable("serviceId") Integer serviceId) {
        List<ServiceReview> reviews = reviewRepository.findByVendorServices_Id(serviceId);
        List<ServiceReviewDto> reviewsDto= new ArrayList<>();
        for (ServiceReview review : reviews) {
            ServiceReviewDto reviewDto = new ServiceReviewDto();
            reviewDto.setId(review.getId());
            reviewDto.setTitle(review.getTitle());
            reviewDto.setBody(review.getBody());
            reviewDto.setRating(review.getRating());
            reviewDto.setUsername(review.getUser().getUsername());
            reviewDto.setCreatedAt(review.getCreatedAt());
            reviewsDto.add(reviewDto);
        }
        return ResponseEntity.ok(reviewsDto);

    }

}
