package ro.devmind.devmind_fullstack_project.service.review;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.devmind.devmind_fullstack_project.assembler.review.ServiceReviewAssembler;
import ro.devmind.devmind_fullstack_project.dto.review.ServiceReviewDto;
import ro.devmind.devmind_fullstack_project.dto.user.CustomUserDetails;
import ro.devmind.devmind_fullstack_project.exception.ResourceNotFoundException;
import ro.devmind.devmind_fullstack_project.exception.UnauthorizedException;
import ro.devmind.devmind_fullstack_project.model.ServiceReview;
import ro.devmind.devmind_fullstack_project.model.User;
import ro.devmind.devmind_fullstack_project.model.Vendor;
import ro.devmind.devmind_fullstack_project.model.VendorServices;
import ro.devmind.devmind_fullstack_project.repository.ReviewRepository;
import ro.devmind.devmind_fullstack_project.repository.UsersRepository;
import ro.devmind.devmind_fullstack_project.repository.VendorServicesRepository;
import ro.devmind.devmind_fullstack_project.repository.VendorsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private VendorServicesRepository vendorServicesRepository;
    @Autowired
    private VendorsRepository vendorRepository;
    @Autowired
    private ServiceReviewAssembler serviceReviewAssembler;

    @Transactional
    public ServiceReviewDto writeReview(ServiceReviewDto serviceReviewDto, CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizatorul nu a fost gasit: " + username));
        VendorServices vendorService = vendorServicesRepository.findById(serviceReviewDto.getServiceId()).get();

        ServiceReview serviceReview = new ServiceReview();
        serviceReview.setUser(userEntity);
        serviceReview.setRating(serviceReviewDto.getRating());
        serviceReview.setTitle(serviceReviewDto.getTitle());
        serviceReview.setBody(serviceReviewDto.getBody());
        serviceReview.setVendorServices(vendorService);

        ServiceReview savedReview = reviewRepository.save(serviceReview);

        // calculate service rating based on average rating from reviews
        updateServiceRating(vendorService);

        // Set Average rating for vendor depending on all its service reviews
        updateVendorOverallRating(vendorService.getVendor());

        ServiceReviewDto result = serviceReviewAssembler.toDto(savedReview);
        return result;
    }

    private void updateServiceRating(VendorServices vendorService) {
        List<ServiceReview> reviews = reviewRepository.findByVendorServices_Id(vendorService.getId());

        if (!reviews.isEmpty()) {
            double averageRating = reviews.stream()
                    .mapToDouble(ServiceReview::getRating)
                    .average()
                    .orElse(0.0);

            vendorService.setRating(averageRating); // Set average rating
            vendorServicesRepository.save(vendorService);
        }
    }

    private void updateVendorOverallRating(Vendor vendor) {
        // Set Average rating for vendor depending on all its service reviews

        List<VendorServices> services = vendorServicesRepository.findByVendor_Id(vendor.getId());

        // Use only the services that have at least one review
        List<VendorServices> ratedServices = services.stream()
                .filter(service -> service.getRating() != null)
                .toList();

        if (!ratedServices.isEmpty()) {
            double overallVendorRating = ratedServices.stream()
                    .mapToDouble(VendorServices::getRating)
                    .average()
                    .orElse(0.0);

            vendor.setRating(overallVendorRating);
            vendorRepository.save(vendor);
        }
    }

    public List<ServiceReviewDto> getServiceReviews(Integer serviceId) {
        List<ServiceReview> reviews = reviewRepository.findByVendorServices_Id(serviceId);
        List<ServiceReviewDto> serviceReviewDtos = new ArrayList<>();

        for (ServiceReview review : reviews) {
            ServiceReviewDto reviewDto = serviceReviewAssembler.toDto(review);
            serviceReviewDtos.add(reviewDto);
        }
        return serviceReviewDtos;
    }

    public ServiceReviewDto updateReview(Integer id, ServiceReviewDto reviewDto,
            CustomUserDetails authenticatedUserDetails) {
        // Get the existing review
        ServiceReview currentReview = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));

        // Check if the current user is the author of the review
        if (!currentReview.getUser().getId().equals(authenticatedUserDetails.getId())) {
            throw new UnauthorizedException("You can only edit your own reviews");
        }

        // Update the review fields
        currentReview.setTitle(reviewDto.getTitle());
        currentReview.setBody(reviewDto.getBody());
        currentReview.setRating(reviewDto.getRating());
        // Don't update the username or userId (should remain the same)
        // Don't update the creation date

        // Save the updated review
        ServiceReview savedReview = reviewRepository.save(currentReview);
        ServiceReviewDto updatedReview = serviceReviewAssembler.toDto(savedReview);

        //Recalculate ratings ->> TODO: Recalculate ONLY IF the number of stars was changed after editing review
        updateServiceRating(currentReview.getVendorServices());
        updateVendorOverallRating(currentReview.getVendorServices().getVendor());
        return updatedReview;

    }

    public ServiceReviewDto deleteReview(Integer id, CustomUserDetails userDetails) {
        ServiceReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));

        if (!review.getUser().getId().equals(userDetails.getId())) {
            throw new UnauthorizedException("You can only delete your own reviews");
        }

        reviewRepository.delete(review);
        updateServiceRating(review.getVendorServices());
        updateVendorOverallRating(review.getVendorServices().getVendor());
        return serviceReviewAssembler.toDto(review);
    }

}
