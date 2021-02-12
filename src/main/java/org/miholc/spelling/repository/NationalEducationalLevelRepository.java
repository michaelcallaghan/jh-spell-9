package org.miholc.spelling.repository;

import org.miholc.spelling.domain.NationalEducationalLevel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NationalEducationalLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NationalEducationalLevelRepository extends JpaRepository<NationalEducationalLevel, Long> {
}
