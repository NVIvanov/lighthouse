package org.lighthouse.domain.repositories;

import org.lighthouse.domain.entities.SystemUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author nivanov
 * on 02.11.2017.
 */

@Repository
public interface UserRepository extends CrudRepository<SystemUser, Long>{
    Optional<SystemUser> findByUsername(String username);
}
