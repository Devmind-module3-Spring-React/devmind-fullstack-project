package ro.devmind.devmind_fullstack_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ro.devmind.devmind_fullstack_project.springJWTauth.JwtService;

@SpringBootApplication
public class DevmindFullstackProjectApplication {


    public static void main(String[] args) {
        SpringApplication.run(DevmindFullstackProjectApplication.class, args);
    }


}
