package org.lighthouse.indexer.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lighthouse.domain.entities.Country;
import org.lighthouse.domain.entities.Declaration;
import org.lighthouse.domain.entities.Official;
import org.lighthouse.domain.repositories.CountryRepository;
import org.lighthouse.domain.repositories.DeclarationRepository;
import org.lighthouse.domain.repositories.OfficialRepository;
import org.lighthouse.indexer.domain.repositories.DeclarationElasticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

/**
 * @author nivanov
 * on 22.10.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SolrIndexerServiceTest {

    @Autowired
    private SolrIndexerService indexerService;

    @Autowired
    private DeclarationElasticRepository solrRepository;

    @Autowired
    private DeclarationRepository declarationRepository;

    @Autowired
    private OfficialRepository officialRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Test
    @Rollback
    public void index() throws Exception {
        Declaration declaration = new Declaration();
        Country country = new Country("country_name", "site", LocalDateTime.now());
        countryRepository.save(country);
        Official official = new Official("name", country);
        declaration.setOfficial(official);
        declaration.setUrl("url");
        officialRepository.save(official);
        declarationRepository.save(declaration);
        official.setDeclaration(declaration);
        officialRepository.save(official);
        indexerService.index(declaration);
        assertTrue(solrRepository.findByOfficialStartingWith("name").isPresent());
    }

    @Test
    @Rollback
    public void findDeclaration() throws Exception {
    }

    @Test
    @Rollback
    public void findDeclaration1() throws Exception {
    }

}