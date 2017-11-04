package org.lighthouse.domain.entities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lighthouse.domain.TestConfiguration;
import org.lighthouse.domain.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

/**
 * @author nivanov
 * on 22.10.2017.
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
public class CountryDomainTest {

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void createCountryShouldSuccess() {
        Country country = new Country("Новая страна", "сайт", LocalDateTime.now());
        countryRepository.save(country);
        assertTrue(countryRepository.findByName("Новая страна").isPresent());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createCountyShouldFailure() {
        Country country = new Country("Россия", "сайт", LocalDateTime.now());
        countryRepository.save(country);
    }
}
