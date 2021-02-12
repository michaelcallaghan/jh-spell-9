package org.miholc.spelling.repository;

import org.miholc.spelling.domain.WordInList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WordInList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WordInListRepository extends JpaRepository<WordInList, Long> {
}
