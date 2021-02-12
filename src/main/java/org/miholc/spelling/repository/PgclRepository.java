package org.miholc.spelling.repository;

import org.miholc.spelling.domain.Pgcl;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pgcl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PgclRepository extends JpaRepository<Pgcl, Long> {
}
