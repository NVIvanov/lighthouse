package org.lighthouse.domain.repositories;

import org.lighthouse.domain.entities.Declaration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author nivanov
 * on 22.10.2017.
 */

@Repository
public interface DeclarationRepository extends CrudRepository<Declaration, Long> {
    Collection<Declaration> findDeclarationByOfficialNameAndOfficialCountryName(String officialName, String countryName);
    Optional<Declaration> findDeclarationByOfficialNameAndOfficialCountryNameAndYearAndType
            (String officialName, String countryName, Integer year, Declaration.Type type);
    Collection<Declaration> findDeclarationByOfficialCountryName(String countryName);
}
