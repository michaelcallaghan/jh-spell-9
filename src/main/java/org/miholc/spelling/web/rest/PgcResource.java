package org.miholc.spelling.web.rest;

import org.miholc.spelling.domain.Pgc;
import org.miholc.spelling.repository.PgcRepository;
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
 * REST controller for managing {@link org.miholc.spelling.domain.Pgc}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PgcResource {

    private final Logger log = LoggerFactory.getLogger(PgcResource.class);

    private static final String ENTITY_NAME = "pgc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgcRepository pgcRepository;

    public PgcResource(PgcRepository pgcRepository) {
        this.pgcRepository = pgcRepository;
    }

    /**
     * {@code POST  /pgcs} : Create a new pgc.
     *
     * @param pgc the pgc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgc, or with status {@code 400 (Bad Request)} if the pgc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pgcs")
    public ResponseEntity<Pgc> createPgc(@RequestBody Pgc pgc) throws URISyntaxException {
        log.debug("REST request to save Pgc : {}", pgc);
        if (pgc.getId() != null) {
            throw new BadRequestAlertException("A new pgc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pgc result = pgcRepository.save(pgc);
        return ResponseEntity.created(new URI("/api/pgcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pgcs} : Updates an existing pgc.
     *
     * @param pgc the pgc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgc,
     * or with status {@code 400 (Bad Request)} if the pgc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pgcs")
    public ResponseEntity<Pgc> updatePgc(@RequestBody Pgc pgc) throws URISyntaxException {
        log.debug("REST request to update Pgc : {}", pgc);
        if (pgc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pgc result = pgcRepository.save(pgc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pgc.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pgcs} : get all the pgcs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgcs in body.
     */
    @GetMapping("/pgcs")
    public ResponseEntity<List<Pgc>> getAllPgcs(Pageable pageable) {
        log.debug("REST request to get a page of Pgcs");
        Page<Pgc> page = pgcRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pgcs/:id} : get the "id" pgc.
     *
     * @param id the id of the pgc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pgcs/{id}")
    public ResponseEntity<Pgc> getPgc(@PathVariable Long id) {
        log.debug("REST request to get Pgc : {}", id);
        Optional<Pgc> pgc = pgcRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pgc);
    }

    /**
     * {@code DELETE  /pgcs/:id} : delete the "id" pgc.
     *
     * @param id the id of the pgc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pgcs/{id}")
    public ResponseEntity<Void> deletePgc(@PathVariable Long id) {
        log.debug("REST request to delete Pgc : {}", id);

        pgcRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
