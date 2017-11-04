package org.lighthouse.indexer.services;

import org.lighthouse.domain.entities.Declaration;

import java.util.Collection;

/**
 * @author nivanov
 * on 22.10.2017.
 */
public interface SolrIndexerService {
    void index(Declaration declaration);
    Collection<String> findDeclaration(String nameLike);
    Collection<String> findDeclaration(String nameLike, String countryName);
}
