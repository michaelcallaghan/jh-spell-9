package org.miholc.spelling.web.rest;

import org.miholc.spelling.domain.Grapheme;
import org.miholc.spelling.repository.GraphemeRepository;
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
 * REST controller for managing {@link org.miholc.spelling.domain.Grapheme}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GraphemeResource {

    private final Logger log = LoggerFactory.getLogger(GraphemeResource.class);

    private static final String ENTITY_NAME = "grapheme";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GraphemeRepository graphemeRepository;

    public GraphemeResource(GraphemeRepository graphemeRepository) {
        this.graphemeRepository = graphemeRepository;
    }

    /**
     * {@code POST  /graphemes} : Create a new grapheme.
     *
     * @param grapheme the grapheme to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grapheme, or with status {@code 400 (Bad Request)} if the grapheme has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/graphemes")
    public ResponseEntity<Grapheme> createGrapheme(@Valid @RequestBody Grapheme grapheme) throws URISyntaxException {
        log.debug("REST request to save Grapheme : {}", grapheme);
        if (grapheme.getId() != null) {
            throw new BadRequestAlertException("A new grapheme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Grapheme result = graphemeRepository.save(grapheme);
        return ResponseEntity.created(new URI("/api/graphemes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /graphemes} : Updates an existing grapheme.
     *
     * @param grapheme the grapheme to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grapheme,
     * or with status {@code 400 (Bad Request)} if the grapheme is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grapheme couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/graphemes")
    public ResponseEntity<Grapheme> updateGrapheme(@Valid @RequestBody Grapheme grapheme) throws URISyntaxException {
        log.debug("REST request to update Grapheme : {}", grapheme);
        if (grapheme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Grapheme result = graphemeRepository.save(grapheme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grapheme.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /graphemes} : get all the graphemes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of graphemes in body.
     */
    @GetMapping("/graphemes")
    public ResponseEntity<List<Grapheme>> getAllGraphemes(Pageable pageable) {
        log.debug("REST request to get a page of Graphemes");
        Page<Grapheme> page = graphemeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /graphemes/:id} : get the "id" grapheme.
     *
     * @param id the id of the grapheme to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grapheme, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/graphemes/{id}")
    public ResponseEntity<Grapheme> getGrapheme(@PathVariable Long id) {
        log.debug("REST request to get Grapheme : {}", id);
        Optional<Grapheme> grapheme = graphemeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(grapheme);
    }

    /**
     * {@code DELETE  /graphemes/:id} : delete the "id" grapheme.
     *
     * @param id the id of the grapheme to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/graphemes/{id}")
    public ResponseEntity<Void> deleteGrapheme(@PathVariable Long id) {
        log.debug("REST request to delete Grapheme : {}", id);

        graphemeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
