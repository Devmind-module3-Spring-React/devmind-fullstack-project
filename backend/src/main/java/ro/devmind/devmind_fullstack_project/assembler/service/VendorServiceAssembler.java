package ro.devmind.devmind_fullstack_project.assembler.service;

import org.springframework.stereotype.Component;
import ro.devmind.devmind_fullstack_project.dto.vendorService.VendorServicesDto;
import ro.devmind.devmind_fullstack_project.model.Vendor;
import ro.devmind.devmind_fullstack_project.model.VendorServices;

@Component
public class VendorServiceAssembler {

    public VendorServicesDto toDto(VendorServices vendorService) {
        VendorServicesDto dto = new VendorServicesDto();
        dto.setId(vendorService.getId());
        dto.setName(vendorService.getName());
        dto.setDescription(vendorService.getDescription());
        dto.setVendorId(vendorService.getVendor().getId());
        return dto;

    }

    public VendorServices toEntity(VendorServicesDto vendorServiceDto, Vendor vendor) {
        VendorServices service = new VendorServices();
        service.setName(vendorServiceDto.getName());
        service.setDescription(vendorServiceDto.getDescription());
        service.setVendor(vendor);
        return service;

    }
}
