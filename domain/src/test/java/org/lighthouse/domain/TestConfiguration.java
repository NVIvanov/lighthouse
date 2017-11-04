package org.lighthouse.domain;

import org.lighthouse.domain.entities.Country;
import org.lighthouse.domain.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author nivanov
 * on 22.10.2017.
 */

@Configuration
@ComponentScan
public class TestConfiguration {

    @Autowired
    private CountryRepository countryRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            Country country = new Country("Россия", "http://russia.ru", LocalDateTime.now());
            countryRepository.save(country);
        };
    }

}
