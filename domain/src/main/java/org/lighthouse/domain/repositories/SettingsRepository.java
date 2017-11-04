package org.lighthouse.domain.repositories;

import org.lighthouse.domain.entities.Settings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author nivanov
 * on 02.11.2017.
 */
@Repository
public interface SettingsRepository extends CrudRepository<Settings, Long>{
    Optional<Settings> findByType(Settings.Type type);
}
