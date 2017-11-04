package org.lighthouse.indexer.services.impl;

import org.lighthouse.domain.entities.Declaration;
import org.lighthouse.indexer.domain.entities.DeclarationDocument;
import org.lighthouse.indexer.domain.repositories.DeclarationElasticRepository;
import org.lighthouse.indexer.services.SolrIndexerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author nivanov
 * on 22.10.2017.
 */

@Service
public class SolrIndexerServiceImpl implements SolrIndexerService {
    private final DeclarationElasticRepository repository;

    @Autowired
    public SolrIndexerServiceImpl(DeclarationElasticRepository repository) {
        this.repository = repository;
    }

    @Override
    public void index(Declaration declaration) {
        DeclarationDocument declarationDocument = new DeclarationDocument();
        declarationDocument.setCountry(declaration.getOfficial().getCountry().getName());
        declarationDocument.setFunction(declaration.getOfficial().getFunction());
        declarationDocument.setId(declaration.getId().toString());
        declarationDocument.setLastUpdate(declaration.getOfficial().getCountry().getLastUpdate());
        declarationDocument.setOfficial(declaration.getOfficial().getName());
        declarationDocument.setLink(declaration.getUrl());
        declarationDocument.setYear(declaration.getYear());
        declarationDocument.setType(declaration.getType().toString());
        repository.save(declarationDocument);
    }

    @Override
    public Collection<String> findDeclaration(String nameLike) {
        return repository.findByOfficialStartingWith(nameLike).stream()
                .map(DeclarationDocument::getOfficial)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<String> findDeclaration(String nameLike, String countryName) {
        Optional<List<String>> result = Stream.of(nameLike.split("\\s+"))
                .map(s -> repository.findByOfficialStartingWithAndCountry(s, countryName).stream()
                        .map(DeclarationDocument::getOfficial)
                        .collect(Collectors.toList()))
                .reduce((list1, list2) -> {
                    list1.retainAll(list2);
                    return list1;
                });
        return result.orElse(Collections.emptyList());
    }
}
