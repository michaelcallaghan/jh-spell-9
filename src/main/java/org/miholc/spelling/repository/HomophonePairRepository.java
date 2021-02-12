package org.miholc.spelling.repository;

import org.miholc.spelling.domain.HomophonePair;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HomophonePair entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HomophonePairRepository extends JpaRepository<HomophonePair, Long> {
}
