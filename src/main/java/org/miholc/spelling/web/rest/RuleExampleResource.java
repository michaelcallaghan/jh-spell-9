package org.miholc.spelling.web.rest;

import org.miholc.spelling.domain.RuleExample;
import org.miholc.spelling.repository.RuleExampleRepository;
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
 * REST controller for managing {@link org.miholc.spelling.domain.RuleExample}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RuleExampleResource {

    private final Logger log = LoggerFactory.getLogger(RuleExampleResource.class);

    private static final String ENTITY_NAME = "ruleExample";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RuleExampleRepository ruleExampleRepository;

    public RuleExampleResource(RuleExampleRepository ruleExampleRepository) {
        this.ruleExampleRepository = ruleExampleRepository;
    }

    /**
     * {@code POST  /rule-examples} : Create a new ruleExample.
     *
     * @param ruleExample the ruleExample to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ruleExample, or with status {@code 400 (Bad Request)} if the ruleExample has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rule-examples")
    public ResponseEntity<RuleExample> createRuleExample(@RequestBody RuleExample ruleExample) throws URISyntaxException {
        log.debug("REST request to save RuleExample : {}", ruleExample);
        if (ruleExample.getId() != null) {
            throw new BadRequestAlertException("A new ruleExample cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RuleExample result = ruleExampleRepository.save(ruleExample);
        return ResponseEntity.created(new URI("/api/rule-examples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rule-examples} : Updates an existing ruleExample.
     *
     * @param ruleExample the ruleExample to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ruleExample,
     * or with status {@code 400 (Bad Request)} if the ruleExample is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ruleExample couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rule-examples")
    public ResponseEntity<RuleExample> updateRuleExample(@RequestBody RuleExample ruleExample) throws URISyntaxException {
        log.debug("REST request to update RuleExample : {}", ruleExample);
        if (ruleExample.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RuleExample result = ruleExampleRepository.save(ruleExample);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ruleExample.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rule-examples} : get all the ruleExamples.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ruleExamples in body.
     */
    @GetMapping("/rule-examples")
    public ResponseEntity<List<RuleExample>> getAllRuleExamples(Pageable pageable) {
        log.debug("REST request to get a page of RuleExamples");
        Page<RuleExample> page = ruleExampleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rule-examples/:id} : get the "id" ruleExample.
     *
     * @param id the id of the ruleExample to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ruleExample, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rule-examples/{id}")
    public ResponseEntity<RuleExample> getRuleExample(@PathVariable Long id) {
        log.debug("REST request to get RuleExample : {}", id);
        Optional<RuleExample> ruleExample = ruleExampleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ruleExample);
    }

    /**
     * {@code DELETE  /rule-examples/:id} : delete the "id" ruleExample.
     *
     * @param id the id of the ruleExample to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rule-examples/{id}")
    public ResponseEntity<Void> deleteRuleExample(@PathVariable Long id) {
        log.debug("REST request to delete RuleExample : {}", id);

        ruleExampleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
