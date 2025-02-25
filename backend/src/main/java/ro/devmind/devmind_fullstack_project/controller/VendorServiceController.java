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
import ro.devmind.devmind_fullstack_project.enums.ServiceStatus;
import ro.devmind.devmind_fullstack_project.model.User;
import ro.devmind.devmind_fullstack_project.model.UserToServices;
import ro.devmind.devmind_fullstack_project.model.Vendor;
import ro.devmind.devmind_fullstack_project.model.VendorServices;
import ro.devmind.devmind_fullstack_project.repository.UserToServicesRepository;
import ro.devmind.devmind_fullstack_project.repository.UsersRepository;
import ro.devmind.devmind_fullstack_project.repository.VendorServicesRepository;
import ro.devmind.devmind_fullstack_project.repository.VendorsRepository;

@Controller
@RequestMapping("/api/v1/vendors/{vendorId}/services")
public class VendorServiceController {
    @Autowired
    private VendorServicesRepository vendorServicesRepository;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private VendorsRepository vendorsRepository;

    @Autowired
    private UserToServicesRepository userToServicesRepository;

    @PostMapping("/add")
    public ResponseEntity<VendorServicesDto> addVendorService(@RequestBody VendorServicesDto vendorServicesDto, @AuthenticationPrincipal String userName) {
        VendorServices vendorService = new VendorServices();
        User user = userRepository.findById(vendorServicesDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Utilizatorul nu există"));
        Vendor vendor = vendorsRepository.findById(vendorServicesDto.getVendorId())
                .orElseThrow(() -> new IllegalArgumentException("Furnizorul nu există"));

        vendorService.setName(vendorServicesDto.getName());
        vendorService.setDescription(vendorServicesDto.getDescription());
        vendorService.setVendor(vendor);
        //Get the persisten entity in order to obtain the id given when added in database
        vendorService = vendorServicesRepository.save(vendorService);

        UserToServices userToServices = new UserToServices();
        userToServices.setUser(user);
        userToServices.setVendorServices(vendorService);
        userToServices.setStatus(ServiceStatus.USED); // TODO: Do not set default status. take it from frontend
        userToServicesRepository.save(userToServices);

        return ResponseEntity.status(HttpStatus.CREATED).body(vendorServicesDto);

    }
}
