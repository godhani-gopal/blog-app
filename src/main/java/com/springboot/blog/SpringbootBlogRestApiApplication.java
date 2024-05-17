package com.springboot.blog;

import com.springboot.blog.entity.Role;
import com.springboot.blog.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "SpringBoot Blog App REST APIs",
        description = "SpringBoot Blog App REST APIs Documentation",
        version = "v1.0",
        contact = @Contact(name = "Godhani Gopal",
                email = "godhanigopal@outlook.com",
                url = "http://dineshgodhani.pythonanywhere.com/"
        ),
        license = @License(
                name = "Apache 2.0",
                url = "dineshgodhani.pythonanywhere.com/license"
        )),
        externalDocs = @ExternalDocumentation(
                description = "SpringBoot Blog Application Documentation",
                url = "https://github.com/godhani-gopal"
        ))
public class SpringbootBlogRestApiApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);
    }
}