package org.miholc.spelling.repository;

import org.miholc.spelling.domain.WordList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WordList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WordListRepository extends JpaRepository<WordList, Long> {
}
