package ro.devmind.devmind_fullstack_project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.devmind.devmind_fullstack_project.assembler.vendor.VendorResponseAssembler;
import ro.devmind.devmind_fullstack_project.assembler.vendor.VendorSummaryAssembler;
import ro.devmind.devmind_fullstack_project.dto.vendor.VendorCreateDto;
import ro.devmind.devmind_fullstack_project.dto.vendor.VendorResponseDto;
import ro.devmind.devmind_fullstack_project.dto.vendor.VendorSummaryResponseDto;
import ro.devmind.devmind_fullstack_project.model.Vendor;
import ro.devmind.devmind_fullstack_project.model.VendorServices;
import ro.devmind.devmind_fullstack_project.service.VendorService;
import java.util.List;


@RestController
@RequestMapping("/api/v1/vendors")
@RequiredArgsConstructor
public class VendorController {
    private final VendorService vendorService; //final field - RequiredArgsConstructor -> autowired does not work because it happens after the object is created
    private final VendorSummaryAssembler vendorSummaryAssembler;
    private final VendorResponseAssembler vendorResponseAssembler;

    @GetMapping("/{id}")
    public ResponseEntity<VendorResponseDto> getVendorById(@PathVariable Integer id) {
        Vendor vendor = vendorService.getVendorById(id);
        return ResponseEntity.ok(vendorResponseAssembler.toModel(vendor));
    }

    @GetMapping
    public ResponseEntity<List<VendorSummaryResponseDto>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendorSummaryAssembler.toModelList(vendors));
    }

    @PostMapping("/add")
    public ResponseEntity<Vendor> addVendor(@Valid @RequestBody VendorCreateDto vendtorCreateDto) {
        Vendor newVendor = vendorService.createVendor(vendtorCreateDto);
        return ResponseEntity.ok(newVendor);
    }

    @GetMapping("/{vendorId}/services")
    public ResponseEntity<List<VendorServices>> getVendorServices(@PathVariable Integer vendorId) {
        List<VendorServices> vendorServices = vendorService.getVendorServices(vendorId);
        return ResponseEntity.ok(vendorServices);
    }
}
