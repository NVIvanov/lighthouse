package org.lighthouse.domain.repositories;

import org.lighthouse.domain.entities.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author nivanov
 * on 22.10.2017.
 */

@Repository
public interface CountryRepository extends CrudRepository<Country, Long>{
    Optional<Country> findByName(String name);
}
