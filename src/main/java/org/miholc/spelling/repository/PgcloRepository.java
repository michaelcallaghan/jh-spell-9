package org.miholc.spelling.repository;

import org.miholc.spelling.domain.Pgclo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pgclo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgcloRepository extends JpaRepository<Pgclo, Long> {
}
