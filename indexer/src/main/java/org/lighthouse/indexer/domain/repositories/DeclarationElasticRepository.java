package org.lighthouse.indexer.domain.repositories;

import org.lighthouse.indexer.domain.entities.DeclarationDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.Collection;

/**
 * @author nivanov
 * on 22.10.2017.
 */
public interface DeclarationElasticRepository extends ElasticsearchCrudRepository<DeclarationDocument, String> {
    Collection<DeclarationDocument> findByOfficialStartingWith(String officialName);
    Collection<DeclarationDocument> findByOfficialStartingWithAndCountry(String officialName, String countryName);
}
