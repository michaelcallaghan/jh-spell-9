package org.miholc.spelling.repository;

import org.miholc.spelling.domain.RuleException;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RuleException entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RuleExceptionRepository extends JpaRepository<RuleException, Long> {
}
