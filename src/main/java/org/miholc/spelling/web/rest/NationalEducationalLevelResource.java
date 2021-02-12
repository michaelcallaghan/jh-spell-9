package org.miholc.spelling.web.rest;

import org.miholc.spelling.domain.NationalEducationalLevel;
import org.miholc.spelling.repository.NationalEducationalLevelRepository;
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
 * REST controller for managing {@link org.miholc.spelling.domain.NationalEducationalLevel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NationalEducationalLevelResource {

    private final Logger log = LoggerFactory.getLogger(NationalEducationalLevelResource.class);

    private static final String ENTITY_NAME = "nationalEducationalLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NationalEducationalLevelRepository nationalEducationalLevelRepository;

    public NationalEducationalLevelResource(NationalEducationalLevelRepository nationalEducationalLevelRepository) {
        this.nationalEducationalLevelRepository = nationalEducationalLevelRepository;
    }

    /**
     * {@code POST  /national-educational-levels} : Create a new nationalEducationalLevel.
     *
     * @param nationalEducationalLevel the nationalEducationalLevel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nationalEducationalLevel, or with status {@code 400 (Bad Request)} if the nationalEducationalLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/national-educational-levels")
    public ResponseEntity<NationalEducationalLevel> createNationalEducationalLevel(@RequestBody NationalEducationalLevel nationalEducationalLevel) throws URISyntaxException {
        log.debug("REST request to save NationalEducationalLevel : {}", nationalEducationalLevel);
        if (nationalEducationalLevel.getId() != null) {
            throw new BadRequestAlertException("A new nationalEducationalLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NationalEducationalLevel result = nationalEducationalLevelRepository.save(nationalEducationalLevel);
        return ResponseEntity.created(new URI("/api/national-educational-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /national-educational-levels} : Updates an existing nationalEducationalLevel.
     *
     * @param nationalEducationalLevel the nationalEducationalLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nationalEducationalLevel,
     * or with status {@code 400 (Bad Request)} if the nationalEducationalLevel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nationalEducationalLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/national-educational-levels")
    public ResponseEntity<NationalEducationalLevel> updateNationalEducationalLevel(@RequestBody NationalEducationalLevel nationalEducationalLevel) throws URISyntaxException {
        log.debug("REST request to update NationalEducationalLevel : {}", nationalEducationalLevel);
        if (nationalEducationalLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NationalEducationalLevel result = nationalEducationalLevelRepository.save(nationalEducationalLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nationalEducationalLevel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /national-educational-levels} : get all the nationalEducationalLevels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nationalEducationalLevels in body.
     */
    @GetMapping("/national-educational-levels")
    public ResponseEntity<List<NationalEducationalLevel>> getAllNationalEducationalLevels(Pageable pageable) {
        log.debug("REST request to get a page of NationalEducationalLevels");
        Page<NationalEducationalLevel> page = nationalEducationalLevelRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /national-educational-levels/:id} : get the "id" nationalEducationalLevel.
     *
     * @param id the id of the nationalEducationalLevel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nationalEducationalLevel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/national-educational-levels/{id}")
    public ResponseEntity<NationalEducationalLevel> getNationalEducationalLevel(@PathVariable Long id) {
        log.debug("REST request to get NationalEducationalLevel : {}", id);
        Optional<NationalEducationalLevel> nationalEducationalLevel = nationalEducationalLevelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nationalEducationalLevel);
    }

    /**
     * {@code DELETE  /national-educational-levels/:id} : delete the "id" nationalEducationalLevel.
     *
     * @param id the id of the nationalEducationalLevel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/national-educational-levels/{id}")
    public ResponseEntity<Void> deleteNationalEducationalLevel(@PathVariable Long id) {
        log.debug("REST request to delete NationalEducationalLevel : {}", id);

        nationalEducationalLevelRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
