package ro.devmind.devmind_fullstack_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.devmind.devmind_fullstack_project.model.Vendor;
import ro.devmind.devmind_fullstack_project.model.VendorService;
import ro.devmind.devmind_fullstack_project.repository.VendorsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class VendorController {

    @Autowired
    private VendorsRepository vendorsRepository;

    @GetMapping("/vendors")
    public List<Vendor> getAllVendors() {
        List<Vendor> vendors = new ArrayList<>();
        vendorsRepository.findAll().forEach(vendor -> vendors.add(vendor));
        return vendors;
    }

    @GetMapping("/vendors/{vendorId}")
    public ResponseEntity<List<VendorService>> getVendorServices(@PathVariable Integer vendorId) {
        Optional<Vendor> vendorOptional = vendorsRepository.findById(vendorId);
        if (!vendorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Vendor vendor = vendorOptional.get();
        List<VendorService> vendorServices = vendor.getVendorServices();
        return ResponseEntity.ok(vendorServices);
    }
}
