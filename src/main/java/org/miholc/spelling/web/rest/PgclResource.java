package org.miholc.spelling.web.rest;

import org.miholc.spelling.domain.Pgcl;
import org.miholc.spelling.repository.PgclRepository;
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
 * REST controller for managing {@link org.miholc.spelling.domain.Pgcl}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PgclResource {

    private final Logger log = LoggerFactory.getLogger(PgclResource.class);

    private static final String ENTITY_NAME = "pgcl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgclRepository pgclRepository;

    public PgclResource(PgclRepository pgclRepository) {
        this.pgclRepository = pgclRepository;
    }

    /**
     * {@code POST  /pgcls} : Create a new pgcl.
     *
     * @param pgcl the pgcl to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgcl, or with status {@code 400 (Bad Request)} if the pgcl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pgcls")
    public ResponseEntity<Pgcl> createPgcl(@RequestBody Pgcl pgcl) throws URISyntaxException {
        log.debug("REST request to save Pgcl : {}", pgcl);
        if (pgcl.getId() != null) {
            throw new BadRequestAlertException("A new pgcl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pgcl result = pgclRepository.save(pgcl);
        return ResponseEntity.created(new URI("/api/pgcls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pgcls} : Updates an existing pgcl.
     *
     * @param pgcl the pgcl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgcl,
     * or with status {@code 400 (Bad Request)} if the pgcl is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgcl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pgcls")
    public ResponseEntity<Pgcl> updatePgcl(@RequestBody Pgcl pgcl) throws URISyntaxException {
        log.debug("REST request to update Pgcl : {}", pgcl);
        if (pgcl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pgcl result = pgclRepository.save(pgcl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pgcl.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pgcls} : get all the pgcls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgcls in body.
     */
    @GetMapping("/pgcls")
    public ResponseEntity<List<Pgcl>> getAllPgcls(Pageable pageable) {
        log.debug("REST request to get a page of Pgcls");
        Page<Pgcl> page = pgclRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pgcls/:id} : get the "id" pgcl.
     *
     * @param id the id of the pgcl to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgcl, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pgcls/{id}")
    public ResponseEntity<Pgcl> getPgcl(@PathVariable Long id) {
        log.debug("REST request to get Pgcl : {}", id);
        Optional<Pgcl> pgcl = pgclRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pgcl);
    }

    /**
     * {@code DELETE  /pgcls/:id} : delete the "id" pgcl.
     *
     * @param id the id of the pgcl to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pgcls/{id}")
    public ResponseEntity<Void> deletePgcl(@PathVariable Long id) {
        log.debug("REST request to delete Pgcl : {}", id);

        pgclRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
