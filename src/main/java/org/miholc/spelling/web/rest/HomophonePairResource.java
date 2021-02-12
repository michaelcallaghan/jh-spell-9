package org.miholc.spelling.web.rest;

import org.miholc.spelling.domain.HomophonePair;
import org.miholc.spelling.repository.HomophonePairRepository;
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
 * REST controller for managing {@link org.miholc.spelling.domain.HomophonePair}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HomophonePairResource {

    private final Logger log = LoggerFactory.getLogger(HomophonePairResource.class);

    private static final String ENTITY_NAME = "homophonePair";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HomophonePairRepository homophonePairRepository;

    public HomophonePairResource(HomophonePairRepository homophonePairRepository) {
        this.homophonePairRepository = homophonePairRepository;
    }

    /**
     * {@code POST  /homophone-pairs} : Create a new homophonePair.
     *
     * @param homophonePair the homophonePair to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new homophonePair, or with status {@code 400 (Bad Request)} if the homophonePair has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/homophone-pairs")
    public ResponseEntity<HomophonePair> createHomophonePair(@RequestBody HomophonePair homophonePair) throws URISyntaxException {
        log.debug("REST request to save HomophonePair : {}", homophonePair);
        if (homophonePair.getId() != null) {
            throw new BadRequestAlertException("A new homophonePair cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HomophonePair result = homophonePairRepository.save(homophonePair);
        return ResponseEntity.created(new URI("/api/homophone-pairs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /homophone-pairs} : Updates an existing homophonePair.
     *
     * @param homophonePair the homophonePair to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated homophonePair,
     * or with status {@code 400 (Bad Request)} if the homophonePair is not valid,
     * or with status {@code 500 (Internal Server Error)} if the homophonePair couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/homophone-pairs")
    public ResponseEntity<HomophonePair> updateHomophonePair(@RequestBody HomophonePair homophonePair) throws URISyntaxException {
        log.debug("REST request to update HomophonePair : {}", homophonePair);
        if (homophonePair.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HomophonePair result = homophonePairRepository.save(homophonePair);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, homophonePair.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /homophone-pairs} : get all the homophonePairs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of homophonePairs in body.
     */
    @GetMapping("/homophone-pairs")
    public ResponseEntity<List<HomophonePair>> getAllHomophonePairs(Pageable pageable) {
        log.debug("REST request to get a page of HomophonePairs");
        Page<HomophonePair> page = homophonePairRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /homophone-pairs/:id} : get the "id" homophonePair.
     *
     * @param id the id of the homophonePair to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the homophonePair, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/homophone-pairs/{id}")
    public ResponseEntity<HomophonePair> getHomophonePair(@PathVariable Long id) {
        log.debug("REST request to get HomophonePair : {}", id);
        Optional<HomophonePair> homophonePair = homophonePairRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(homophonePair);
    }

    /**
     * {@code DELETE  /homophone-pairs/:id} : delete the "id" homophonePair.
     *
     * @param id the id of the homophonePair to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/homophone-pairs/{id}")
    public ResponseEntity<Void> deleteHomophonePair(@PathVariable Long id) {
        log.debug("REST request to delete HomophonePair : {}", id);

        homophonePairRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
