package org.lighthouse.domain.repositories;

import org.lighthouse.domain.entities.Official;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author nivanov
 * on 22.10.2017.
 */

@Repository
public interface OfficialRepository extends CrudRepository<Official, Long>{
    Optional<Official> findOfficialByCountryNameAndName(String countryName, String name);
    Optional<Official> findOfficialByName(String name);
}
