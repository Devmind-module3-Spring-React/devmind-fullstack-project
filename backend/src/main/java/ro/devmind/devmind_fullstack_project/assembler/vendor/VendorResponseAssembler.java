package ro.devmind.devmind_fullstack_project.assembler.vendor;

import lombok.NonNull;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ro.devmind.devmind_fullstack_project.controller.VendorController;
import ro.devmind.devmind_fullstack_project.dto.vendor.VendorResponseDto;
import ro.devmind.devmind_fullstack_project.model.Vendor;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VendorResponseAssembler implements RepresentationModelAssembler<Vendor, VendorResponseDto> {
    //toModel == convert an entity into a representation model -> Spring HATEOAS naming convention
    @Override
    public @NonNull VendorResponseDto toModel(@NonNull Vendor vendor) {
        VendorResponseDto vendorResponseDto = new VendorResponseDto();

        vendorResponseDto.setId(vendor.getId());

        if (null != vendor.getClaimedByUser()) {
            vendorResponseDto.setClaimedByUserId(vendor.getClaimedByUser().getId());
        }

        vendorResponseDto.setCompanyName(vendor.getCompanyName());

        vendorResponseDto.setCompanyEmail(vendor.getCompanyEmail());

        if (null != vendor.getLocation()) {
            vendorResponseDto.setLocation(vendor.getLocation());
        }

        if (null != vendor.getDescription()) {
            vendorResponseDto.setDescription(vendor.getDescription());
        }

        vendorResponseDto.setRating(vendor.getRating());

        if (null != vendor.getProfilePicture()) {
            vendorResponseDto.setProfilePicture(vendor.getProfilePicture());
        }

        vendorResponseDto.setWebsiteUrl(vendor.getWebsiteUrl());

        if (null != vendor.getPhoneNumber()) {
            vendorResponseDto.setDescription(vendor.getPhoneNumber());
        }

        vendorResponseDto.setClaimed(vendor.isClaimed());

        vendorResponseDto.setCreatedByUserId(vendor.getCreatedByUser().getId());

        //Add HATEOAS links on each vendorResponseDto -> link to the vendor itself, link to all vendors list, link to vendor's services
        vendorResponseDto.add(
                linkTo(methodOn(VendorController.class).getVendorById(vendor.getId())).withSelfRel(),
                linkTo(methodOn(VendorController.class).getAllVendors()).withRel("vendors"),
                linkTo(methodOn(VendorController.class).getVendorServices(vendor.getId())).withRel("services")
        );

        return vendorResponseDto;
    }

    public List<VendorResponseDto> toModelList(@NonNull List<Vendor> entities) {
        return entities.stream().map(this::toModel).toList();
    }
}
