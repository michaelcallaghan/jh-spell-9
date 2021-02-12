package org.miholc.spelling.web.rest;

import org.miholc.spelling.domain.WordList;
import org.miholc.spelling.repository.WordListRepository;
import org.miholc.spelling.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.miholc.spelling.domain.WordList}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WordListResource {

    private final Logger log = LoggerFactory.getLogger(WordListResource.class);

    private static final String ENTITY_NAME = "wordList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WordListRepository wordListRepository;

    public WordListResource(WordListRepository wordListRepository) {
        this.wordListRepository = wordListRepository;
    }

    /**
     * {@code POST  /word-lists} : Create a new wordList.
     *
     * @param wordList the wordList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wordList, or with status {@code 400 (Bad Request)} if the wordList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/word-lists")
    public ResponseEntity<WordList> createWordList(@RequestBody WordList wordList) throws URISyntaxException {
        log.debug("REST request to save WordList : {}", wordList);
        if (wordList.getId() != null) {
            throw new BadRequestAlertException("A new wordList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WordList result = wordListRepository.save(wordList);
        return ResponseEntity.created(new URI("/api/word-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /word-lists} : Updates an existing wordList.
     *
     * @param wordList the wordList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wordList,
     * or with status {@code 400 (Bad Request)} if the wordList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wordList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/word-lists")
    public ResponseEntity<WordList> updateWordList(@RequestBody WordList wordList) throws URISyntaxException {
        log.debug("REST request to update WordList : {}", wordList);
        if (wordList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WordList result = wordListRepository.save(wordList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wordList.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /word-lists} : get all the wordLists.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wordLists in body.
     */
    @GetMapping("/word-lists")
    public ResponseEntity<List<WordList>> getAllWordLists(Pageable pageable) {
        log.debug("REST request to get a page of WordLists");
        Page<WordList> page = wordListRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /word-lists/:id} : get the "id" wordList.
     *
     * @param id the id of the wordList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wordList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/word-lists/{id}")
    public ResponseEntity<WordList> getWordList(@PathVariable Long id) {
        log.debug("REST request to get WordList : {}", id);
        Optional<WordList> wordList = wordListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wordList);
    }

    /**
     * {@code DELETE  /word-lists/:id} : delete the "id" wordList.
     *
     * @param id the id of the wordList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/word-lists/{id}")
    public ResponseEntity<Void> deleteWordList(@PathVariable Long id) {
        log.debug("REST request to delete WordList : {}", id);

        wordListRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
