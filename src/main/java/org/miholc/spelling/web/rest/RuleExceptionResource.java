package org.miholc.spelling.web.rest;

import org.miholc.spelling.domain.RuleException;
import org.miholc.spelling.repository.RuleExceptionRepository;
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
 * REST controller for managing {@link org.miholc.spelling.domain.RuleException}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RuleExceptionResource {

    private final Logger log = LoggerFactory.getLogger(RuleExceptionResource.class);

    private static final String ENTITY_NAME = "ruleException";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RuleExceptionRepository ruleExceptionRepository;

    public RuleExceptionResource(RuleExceptionRepository ruleExceptionRepository) {
        this.ruleExceptionRepository = ruleExceptionRepository;
    }

    /**
     * {@code POST  /rule-exceptions} : Create a new ruleException.
     *
     * @param ruleException the ruleException to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ruleException, or with status {@code 400 (Bad Request)} if the ruleException has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rule-exceptions")
    public ResponseEntity<RuleException> createRuleException(@RequestBody RuleException ruleException) throws URISyntaxException {
        log.debug("REST request to save RuleException : {}", ruleException);
        if (ruleException.getId() != null) {
            throw new BadRequestAlertException("A new ruleException cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RuleException result = ruleExceptionRepository.save(ruleException);
        return ResponseEntity.created(new URI("/api/rule-exceptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rule-exceptions} : Updates an existing ruleException.
     *
     * @param ruleException the ruleException to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ruleException,
     * or with status {@code 400 (Bad Request)} if the ruleException is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ruleException couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rule-exceptions")
    public ResponseEntity<RuleException> updateRuleException(@RequestBody RuleException ruleException) throws URISyntaxException {
        log.debug("REST request to update RuleException : {}", ruleException);
        if (ruleException.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RuleException result = ruleExceptionRepository.save(ruleException);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ruleException.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rule-exceptions} : get all the ruleExceptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ruleExceptions in body.
     */
    @GetMapping("/rule-exceptions")
    public ResponseEntity<List<RuleException>> getAllRuleExceptions(Pageable pageable) {
        log.debug("REST request to get a page of RuleExceptions");
        Page<RuleException> page = ruleExceptionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rule-exceptions/:id} : get the "id" ruleException.
     *
     * @param id the id of the ruleException to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ruleException, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rule-exceptions/{id}")
    public ResponseEntity<RuleException> getRuleException(@PathVariable Long id) {
        log.debug("REST request to get RuleException : {}", id);
        Optional<RuleException> ruleException = ruleExceptionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ruleException);
    }

    /**
     * {@code DELETE  /rule-exceptions/:id} : delete the "id" ruleException.
     *
     * @param id the id of the ruleException to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rule-exceptions/{id}")
    public ResponseEntity<Void> deleteRuleException(@PathVariable Long id) {
        log.debug("REST request to delete RuleException : {}", id);

        ruleExceptionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
