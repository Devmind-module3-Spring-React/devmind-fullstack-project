package ro.devmind.devmind_fullstack_project.assembler.vendor;

import lombok.NonNull;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ro.devmind.devmind_fullstack_project.controller.VendorController;
import ro.devmind.devmind_fullstack_project.dto.vendor.VendorResponseDto;
import ro.devmind.devmind_fullstack_project.dto.vendor.VendorSummaryResponseDto;
import ro.devmind.devmind_fullstack_project.model.Vendor;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VendorSummaryAssembler implements RepresentationModelAssembler<Vendor, VendorSummaryResponseDto> {
    @Override
    public @NonNull VendorSummaryResponseDto toModel(@NonNull Vendor vendor) {
        VendorSummaryResponseDto vendorSummaryResponseDto = new VendorSummaryResponseDto();
        vendorSummaryResponseDto.setId(vendor.getId());
        vendorSummaryResponseDto.setCompanyName(vendor.getCompanyName());
        vendorSummaryResponseDto.setLocation(vendor.getLocation());
        vendorSummaryResponseDto.setRating(vendor.getRating());
        vendorSummaryResponseDto.setWebsiteUrl(vendor.getWebsiteUrl());
        if (null != vendor.getPhoneNumber()) {
            vendorSummaryResponseDto.setPhoneNumber(vendor.getPhoneNumber());
        }
        if (null != vendor.getProfilePicture()) {
            vendorSummaryResponseDto.setProfilePicture(vendor.getProfilePicture());
        }

        //Add HATEOAS links on each vendorSummaryResponseDto -> link to the vendor itself and link to vendor services
        vendorSummaryResponseDto.add(
              linkTo(methodOn(VendorController.class).getVendorById(vendor.getId())).withSelfRel()
        );

        vendorSummaryResponseDto.add(
                linkTo(methodOn(VendorController.class).getVendorServices(vendor.getId())).withRel("services")
        );

        return vendorSummaryResponseDto;
    }

    public List<VendorSummaryResponseDto> toModelList(@NonNull List<Vendor> vendorsList) {
        return vendorsList.stream().map(this::toModel).toList();
    }
}
