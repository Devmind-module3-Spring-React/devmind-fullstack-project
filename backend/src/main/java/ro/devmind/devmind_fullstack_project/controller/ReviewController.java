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
import ro.devmind.devmind_fullstack_project.model.VendorServices;
import ro.devmind.devmind_fullstack_project.repository.ReviewRepository;
import ro.devmind.devmind_fullstack_project.repository.ServicesRepository;
import ro.devmind.devmind_fullstack_project.repository.UsersRepository;

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
    private ServicesRepository vendorServicesRepository;

//    Extract the username from SecurityContext ->> see JwtFilter SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), null, parseAuthorities(user.getRoles())));
    @PostMapping("/create-new-review")
    public ResponseEntity<ServiceReviewDto> writeReview(@RequestBody ServiceReviewDto serviceReviewDto, @AuthenticationPrincipal String userName) {
        ServiceReview serviceReview = new ServiceReview();
        User userEntity = userRepository.findByUsername(userName).get();

        serviceReview.setUser(userEntity);
        serviceReview.setRating(serviceReviewDto.getRating());
        serviceReview.setTitle(serviceReviewDto.getTitle());
        serviceReview.setBody(serviceReviewDto.getBody());
        VendorServices vendorServices = vendorServicesRepository.findById(serviceReviewDto.getServiceId()).get();
        serviceReview.setVendorServices(vendorServices);

        reviewRepository.save(serviceReview);

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
