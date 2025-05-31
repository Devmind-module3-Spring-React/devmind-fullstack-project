package ro.devmind.devmind_fullstack_project.service.vendorService;


import org.springframework.stereotype.Service;
import ro.devmind.devmind_fullstack_project.assembler.service.VendorServiceAssembler;
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

@Service
public class VendorServiceService {

    private final VendorServicesRepository vendorServicesRepository;
    private final VendorsRepository vendorsRepository;
    private final UserToServicesRepository userToServicesRepository;
    private final VendorServiceAssembler vendorServiceAssembler;

    public VendorServiceService (VendorServicesRepository vendorServicesRepository, UsersRepository userRepository,
                                 VendorsRepository vendorsRepository, UserToServicesRepository
                                         userToServicesRepository, VendorServiceAssembler vendorServiceAssembler) {
        this.vendorServicesRepository = vendorServicesRepository;
        this.vendorsRepository = vendorsRepository;
        this.userToServicesRepository = userToServicesRepository;
        this.vendorServiceAssembler = vendorServiceAssembler;
    }

    public VendorServicesDto addVendorService(VendorServicesDto vendorServicesDto, User user) {
        Vendor vendor = vendorsRepository.findById(vendorServicesDto.getVendorId())
                .orElseThrow(() -> new IllegalArgumentException("Furnizorul nu există"));

        VendorServices vendorService = vendorServiceAssembler.toEntity(vendorServicesDto, vendor);

        //Get the persistent entity in order to obtain the id given when added in database
        vendorService = vendorServicesRepository.save(vendorService);

        //replace the Dto received from front-end with the DTO containing the vendorService ID after database saving
        vendorServicesDto = vendorServiceAssembler.toDto(vendorService);

        UserToServices userToServices = new UserToServices();
        userToServices.setUser(user);
        userToServices.setVendorServices(vendorService);
        userToServices.setStatus(ServiceStatus.USED); // TODO: Do not set default status. take it from frontend
        userToServicesRepository.save(userToServices);
        return vendorServicesDto;
    }
}
