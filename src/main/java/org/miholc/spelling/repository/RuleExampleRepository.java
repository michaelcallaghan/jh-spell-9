package org.miholc.spelling.repository;

import org.miholc.spelling.domain.RuleExample;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RuleExample entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RuleExampleRepository extends JpaRepository<RuleExample, Long> {
}
