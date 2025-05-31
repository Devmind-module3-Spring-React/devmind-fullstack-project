package ro.devmind.devmind_fullstack_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.devmind.devmind_fullstack_project.dto.vendorService.VendorServicesDto;
import ro.devmind.devmind_fullstack_project.model.User;
import ro.devmind.devmind_fullstack_project.service.vendorService.VendorServiceService;

@Controller
@RequestMapping("/api/v1/vendors/{vendorId}/services")
public class VendorServiceController {
    @Autowired
    VendorServiceService vendorServiceService;


    @PostMapping("/add")
    //taking the user from the security context not from frontend
    public ResponseEntity<VendorServicesDto> addVendorService(@RequestBody VendorServicesDto savedService, @AuthenticationPrincipal User user) {
        savedService = vendorServiceService.addVendorService(savedService, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedService);

    }
}
