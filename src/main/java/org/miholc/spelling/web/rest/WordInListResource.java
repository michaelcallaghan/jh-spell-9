package org.miholc.spelling.web.rest;

import org.miholc.spelling.domain.WordInList;
import org.miholc.spelling.repository.WordInListRepository;
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
 * REST controller for managing {@link org.miholc.spelling.domain.WordInList}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WordInListResource {

    private final Logger log = LoggerFactory.getLogger(WordInListResource.class);

    private static final String ENTITY_NAME = "wordInList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WordInListRepository wordInListRepository;

    public WordInListResource(WordInListRepository wordInListRepository) {
        this.wordInListRepository = wordInListRepository;
    }

    /**
     * {@code POST  /word-in-lists} : Create a new wordInList.
     *
     * @param wordInList the wordInList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wordInList, or with status {@code 400 (Bad Request)} if the wordInList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/word-in-lists")
    public ResponseEntity<WordInList> createWordInList(@RequestBody WordInList wordInList) throws URISyntaxException {
        log.debug("REST request to save WordInList : {}", wordInList);
        if (wordInList.getId() != null) {
            throw new BadRequestAlertException("A new wordInList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WordInList result = wordInListRepository.save(wordInList);
        return ResponseEntity.created(new URI("/api/word-in-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /word-in-lists} : Updates an existing wordInList.
     *
     * @param wordInList the wordInList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wordInList,
     * or with status {@code 400 (Bad Request)} if the wordInList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wordInList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/word-in-lists")
    public ResponseEntity<WordInList> updateWordInList(@RequestBody WordInList wordInList) throws URISyntaxException {
        log.debug("REST request to update WordInList : {}", wordInList);
        if (wordInList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WordInList result = wordInListRepository.save(wordInList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wordInList.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /word-in-lists} : get all the wordInLists.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wordInLists in body.
     */
    @GetMapping("/word-in-lists")
    public ResponseEntity<List<WordInList>> getAllWordInLists(Pageable pageable) {
        log.debug("REST request to get a page of WordInLists");
        Page<WordInList> page = wordInListRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /word-in-lists/:id} : get the "id" wordInList.
     *
     * @param id the id of the wordInList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wordInList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/word-in-lists/{id}")
    public ResponseEntity<WordInList> getWordInList(@PathVariable Long id) {
        log.debug("REST request to get WordInList : {}", id);
        Optional<WordInList> wordInList = wordInListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wordInList);
    }

    /**
     * {@code DELETE  /word-in-lists/:id} : delete the "id" wordInList.
     *
     * @param id the id of the wordInList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/word-in-lists/{id}")
    public ResponseEntity<Void> deleteWordInList(@PathVariable Long id) {
        log.debug("REST request to delete WordInList : {}", id);

        wordInListRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
