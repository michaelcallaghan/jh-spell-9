package org.miholc.spelling.web.rest;

import org.miholc.spelling.domain.Pgclo;
import org.miholc.spelling.repository.PgcloRepository;
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
 * REST controller for managing {@link org.miholc.spelling.domain.Pgclo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PgcloResource {

    private final Logger log = LoggerFactory.getLogger(PgcloResource.class);

    private static final String ENTITY_NAME = "pgclo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgcloRepository pgcloRepository;

    public PgcloResource(PgcloRepository pgcloRepository) {
        this.pgcloRepository = pgcloRepository;
    }

    /**
     * {@code POST  /pgclos} : Create a new pgclo.
     *
     * @param pgclo the pgclo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgclo, or with status {@code 400 (Bad Request)} if the pgclo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pgclos")
    public ResponseEntity<Pgclo> createPgclo(@RequestBody Pgclo pgclo) throws URISyntaxException {
        log.debug("REST request to save Pgclo : {}", pgclo);
        if (pgclo.getId() != null) {
            throw new BadRequestAlertException("A new pgclo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pgclo result = pgcloRepository.save(pgclo);
        return ResponseEntity.created(new URI("/api/pgclos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pgclos} : Updates an existing pgclo.
     *
     * @param pgclo the pgclo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgclo,
     * or with status {@code 400 (Bad Request)} if the pgclo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgclo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pgclos")
    public ResponseEntity<Pgclo> updatePgclo(@RequestBody Pgclo pgclo) throws URISyntaxException {
        log.debug("REST request to update Pgclo : {}", pgclo);
        if (pgclo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pgclo result = pgcloRepository.save(pgclo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pgclo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pgclos} : get all the pgclos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgclos in body.
     */
    @GetMapping("/pgclos")
    public ResponseEntity<List<Pgclo>> getAllPgclos(Pageable pageable) {
        log.debug("REST request to get a page of Pgclos");
        Page<Pgclo> page = pgcloRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pgclos/:id} : get the "id" pgclo.
     *
     * @param id the id of the pgclo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgclo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pgclos/{id}")
    public ResponseEntity<Pgclo> getPgclo(@PathVariable Long id) {
        log.debug("REST request to get Pgclo : {}", id);
        Optional<Pgclo> pgclo = pgcloRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pgclo);
    }

    /**
     * {@code DELETE  /pgclos/:id} : delete the "id" pgclo.
     *
     * @param id the id of the pgclo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pgclos/{id}")
    public ResponseEntity<Void> deletePgclo(@PathVariable Long id) {
        log.debug("REST request to delete Pgclo : {}", id);

        pgcloRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
