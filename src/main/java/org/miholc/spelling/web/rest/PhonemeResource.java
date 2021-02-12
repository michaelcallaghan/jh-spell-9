package org.miholc.spelling.web.rest;

import org.miholc.spelling.domain.Phoneme;
import org.miholc.spelling.repository.PhonemeRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.miholc.spelling.domain.Phoneme}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PhonemeResource {

    private final Logger log = LoggerFactory.getLogger(PhonemeResource.class);

    private static final String ENTITY_NAME = "phoneme";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhonemeRepository phonemeRepository;

    public PhonemeResource(PhonemeRepository phonemeRepository) {
        this.phonemeRepository = phonemeRepository;
    }

    /**
     * {@code POST  /phonemes} : Create a new phoneme.
     *
     * @param phoneme the phoneme to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phoneme, or with status {@code 400 (Bad Request)} if the phoneme has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phonemes")
    public ResponseEntity<Phoneme> createPhoneme(@Valid @RequestBody Phoneme phoneme) throws URISyntaxException {
        log.debug("REST request to save Phoneme : {}", phoneme);
        if (phoneme.getId() != null) {
            throw new BadRequestAlertException("A new phoneme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Phoneme result = phonemeRepository.save(phoneme);
        return ResponseEntity.created(new URI("/api/phonemes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phonemes} : Updates an existing phoneme.
     *
     * @param phoneme the phoneme to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phoneme,
     * or with status {@code 400 (Bad Request)} if the phoneme is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phoneme couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phonemes")
    public ResponseEntity<Phoneme> updatePhoneme(@Valid @RequestBody Phoneme phoneme) throws URISyntaxException {
        log.debug("REST request to update Phoneme : {}", phoneme);
        if (phoneme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Phoneme result = phonemeRepository.save(phoneme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, phoneme.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /phonemes} : get all the phonemes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phonemes in body.
     */
    @GetMapping("/phonemes")
    public ResponseEntity<List<Phoneme>> getAllPhonemes(Pageable pageable) {
        log.debug("REST request to get a page of Phonemes");
        Page<Phoneme> page = phonemeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /phonemes/:id} : get the "id" phoneme.
     *
     * @param id the id of the phoneme to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phoneme, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phonemes/{id}")
    public ResponseEntity<Phoneme> getPhoneme(@PathVariable Long id) {
        log.debug("REST request to get Phoneme : {}", id);
        Optional<Phoneme> phoneme = phonemeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(phoneme);
    }

    /**
     * {@code DELETE  /phonemes/:id} : delete the "id" phoneme.
     *
     * @param id the id of the phoneme to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phonemes/{id}")
    public ResponseEntity<Void> deletePhoneme(@PathVariable Long id) {
        log.debug("REST request to delete Phoneme : {}", id);

        phonemeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
