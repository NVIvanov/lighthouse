package org.lighthouse;

import org.lighthouse.domain.entities.Country;
import org.lighthouse.domain.entities.SystemUser;
import org.lighthouse.domain.repositories.CountryRepository;
import org.lighthouse.domain.repositories.UserRepository;
import org.lighthouse.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author nivanov
 * on 22.10.2017.
 */

@SpringBootApplication
public class LighthouseWebApplication {
    private final UserService userService;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public LighthouseWebApplication(UserRepository userRepository, UserService userService, CountryRepository countryRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.countryRepository = countryRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(LighthouseWebApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            SystemUser systemUser = new SystemUser();
            systemUser.setUsername("admin");
            systemUser.setPassword("admin");
            if (!userRepository.findByUsername("admin").isPresent()) {
                userService.createUser(systemUser);
            }

            if (!countryRepository.findByName("Болгария").isPresent()) {
                Country country = new Country();
                country.setName("Болгария");
                country.setSiteUrl("http://register.bulnao.government.bg");
                countryRepository.save(country);
            }
        };
    }
}