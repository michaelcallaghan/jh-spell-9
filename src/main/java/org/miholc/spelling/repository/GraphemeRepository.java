package org.miholc.spelling.repository;

import org.miholc.spelling.domain.Grapheme;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Grapheme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GraphemeRepository extends JpaRepository<Grapheme, Long> {
}
