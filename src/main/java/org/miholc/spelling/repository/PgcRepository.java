package org.miholc.spelling.repository;

import org.miholc.spelling.domain.Pgc;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pgc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgcRepository extends JpaRepository<Pgc, Long> {
}
