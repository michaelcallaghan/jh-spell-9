package org.miholc.spelling.repository;

import org.miholc.spelling.domain.Phoneme;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Phoneme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhonemeRepository extends JpaRepository<Phoneme, Long> {
}
