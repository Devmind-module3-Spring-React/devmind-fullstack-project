package ro.devmind.devmind_fullstack_project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.devmind.devmind_fullstack_project.dto.review.ServiceReviewDto;
import ro.devmind.devmind_fullstack_project.dto.user.CustomUserDetails;
import ro.devmind.devmind_fullstack_project.repository.ReviewRepository;
import ro.devmind.devmind_fullstack_project.service.review.ReviewService;
import java.util.List;

@Controller
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewService reviewService;

//    Extract the username from SecurityContext ->> see JwtFilter SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), null, parseAuthorities(user.getRoles())));
    @PostMapping("/create-new-review")
    public ResponseEntity<ServiceReviewDto> writeReview(@RequestBody ServiceReviewDto serviceReviewDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        ServiceReviewDto savedReview = reviewService.writeReview(serviceReviewDto, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<ServiceReviewDto>> getServiceReviews(@PathVariable("serviceId") Integer serviceId) {
        List<ServiceReviewDto> serviceReviewsDtos = reviewService.getServiceReviews(serviceId);
        return ResponseEntity.ok(serviceReviewsDtos);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<ServiceReviewDto> updateReview(@PathVariable Integer id,
                                                  @Valid @RequestBody ServiceReviewDto reviewDto,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        ServiceReviewDto updatedReview = reviewService.updateReview(id, reviewDto, userDetails);
        return ResponseEntity.ok(updatedReview);
    }

}
