package ro.devmind.devmind_fullstack_project.service.vendor;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.devmind.devmind_fullstack_project.dto.vendor.VendorCreateDto;
import ro.devmind.devmind_fullstack_project.exception.DuplicateResourceException;
import ro.devmind.devmind_fullstack_project.exception.ResourceNotFoundException;
import ro.devmind.devmind_fullstack_project.model.User;
import ro.devmind.devmind_fullstack_project.model.Vendor;
import ro.devmind.devmind_fullstack_project.model.VendorServices;
import ro.devmind.devmind_fullstack_project.repository.UsersRepository;
import ro.devmind.devmind_fullstack_project.repository.VendorsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VendorService {
    //fields are final so they do not require Autowired, injection is handled by RequiredArgsConstructor
    private final VendorsRepository vendorRepository;
    private final UsersRepository userRepository;

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor getVendorById(Integer id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
    }

    public List<VendorServices> getVendorServices(Integer vendorId) {
        Vendor vendor = getVendorById(vendorId);
        return vendor.getVendorServices();
    }


    public Vendor createVendor(VendorCreateDto createDto) {
        if (vendorRepository.existsByCompanyName(createDto.getCompanyName())) {
            throw new DuplicateResourceException("Company name already exists");
        }

        if (createDto.getCompanyEmail() != null &&
                vendorRepository.existsByCompanyEmail(createDto.getCompanyEmail())) {
            throw new DuplicateResourceException("Company email already exists");
        }
        if (createDto.getPhoneNumber() != null &&
                vendorRepository.existsByPhoneNumber(createDto.getPhoneNumber())) {
            throw new DuplicateResourceException("Phone number already exists");
        }

        Vendor vendor = new Vendor();
        vendor.setCompanyName(createDto.getCompanyName());
        vendor.setCompanyEmail(createDto.getCompanyEmail());
        vendor.setLocation(createDto.getLocation());
//        vendor.setDescription(createDto.getDescription());
        vendor.setWebsiteUrl(createDto.getWebsiteUrl());
        vendor.setPhoneNumber(createDto.getPhoneNumber());
        User user = userRepository.findById(createDto.getCreatedByUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        vendor.setCreatedByUser(user);
        vendor.setCreatedAt(LocalDateTime.now());

        return vendorRepository.save(vendor);
    }


//    public Vendor updateVendor(Integer id, VendorResponseDto updateDto) {
//        Vendor vendor = getVendorById(id);
//
//        if (!vendor.getCompanyName().equals(updateDto.getCompanyName()) &&
//                vendorRepository.existsByCompanyName(updateDto.getCompanyName())) {
//            throw new DuplicateResourceException("Company name already exists");
//        }
//
//        if (updateDto.getCompanyEmail() != null &&
//                !vendor.getCompanyEmail().equals(updateDto.getCompanyEmail()) &&
//                vendorRepository.existsByCompanyEmail(updateDto.getCompanyEmail())) {
//            throw new DuplicateResourceException("Company email already exists");
//        }
//
//        vendor.setCompanyName(updateDto.getCompanyName());
//        vendor.setCompanyEmail(updateDto.getCompanyEmail());
//        vendor.setLocation(updateDto.getLocation());
//        vendor.setDescription(updateDto.getDescription());
//        vendor.setRating(updateDto.getRating());
//        vendor.setProfilePicture(updateDto.getProfilePicture());
//        vendor.setWebsiteUrl(updateDto.getWebsiteUrl());
//        vendor.setPhoneNumber(updateDto.getPhoneNumber());
//        vendor.setUpdatedAt(LocalDateTime.now());
//
//        return vendorRepository.save(vendor);
//    }

//    public void deleteVendor(Integer id) {
//        Vendor vendor = getVendorById(id);
//        vendorRepository.delete(vendor);
//    }

}