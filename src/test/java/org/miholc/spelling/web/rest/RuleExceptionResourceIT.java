package org.miholc.spelling.web.rest;

import org.miholc.spelling.JhSpell9App;
import org.miholc.spelling.domain.RuleException;
import org.miholc.spelling.repository.RuleExceptionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RuleExceptionResource} REST controller.
 */
@SpringBootTest(classes = JhSpell9App.class)
@AutoConfigureMockMvc
@WithMockUser
public class RuleExceptionResourceIT {

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    @Autowired
    private RuleExceptionRepository ruleExceptionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRuleExceptionMockMvc;

    private RuleException ruleException;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RuleException createEntity(EntityManager em) {
        RuleException ruleException = new RuleException()
            .info(DEFAULT_INFO);
        return ruleException;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RuleException createUpdatedEntity(EntityManager em) {
        RuleException ruleException = new RuleException()
            .info(UPDATED_INFO);
        return ruleException;
    }

    @BeforeEach
    public void initTest() {
        ruleException = createEntity(em);
    }

    @Test
    @Transactional
    public void createRuleException() throws Exception {
        int databaseSizeBeforeCreate = ruleExceptionRepository.findAll().size();
        // Create the RuleException
        restRuleExceptionMockMvc.perform(post("/api/rule-exceptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleException)))
            .andExpect(status().isCreated());

        // Validate the RuleException in the database
        List<RuleException> ruleExceptionList = ruleExceptionRepository.findAll();
        assertThat(ruleExceptionList).hasSize(databaseSizeBeforeCreate + 1);
        RuleException testRuleException = ruleExceptionList.get(ruleExceptionList.size() - 1);
        assertThat(testRuleException.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    public void createRuleExceptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ruleExceptionRepository.findAll().size();

        // Create the RuleException with an existing ID
        ruleException.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuleExceptionMockMvc.perform(post("/api/rule-exceptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleException)))
            .andExpect(status().isBadRequest());

        // Validate the RuleException in the database
        List<RuleException> ruleExceptionList = ruleExceptionRepository.findAll();
        assertThat(ruleExceptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRuleExceptions() throws Exception {
        // Initialize the database
        ruleExceptionRepository.saveAndFlush(ruleException);

        // Get all the ruleExceptionList
        restRuleExceptionMockMvc.perform(get("/api/rule-exceptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ruleException.getId().intValue())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }
    
    @Test
    @Transactional
    public void getRuleException() throws Exception {
        // Initialize the database
        ruleExceptionRepository.saveAndFlush(ruleException);

        // Get the ruleException
        restRuleExceptionMockMvc.perform(get("/api/rule-exceptions/{id}", ruleException.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ruleException.getId().intValue()))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }
    @Test
    @Transactional
    public void getNonExistingRuleException() throws Exception {
        // Get the ruleException
        restRuleExceptionMockMvc.perform(get("/api/rule-exceptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRuleException() throws Exception {
        // Initialize the database
        ruleExceptionRepository.saveAndFlush(ruleException);

        int databaseSizeBeforeUpdate = ruleExceptionRepository.findAll().size();

        // Update the ruleException
        RuleException updatedRuleException = ruleExceptionRepository.findById(ruleException.getId()).get();
        // Disconnect from session so that the updates on updatedRuleException are not directly saved in db
        em.detach(updatedRuleException);
        updatedRuleException
            .info(UPDATED_INFO);

        restRuleExceptionMockMvc.perform(put("/api/rule-exceptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRuleException)))
            .andExpect(status().isOk());

        // Validate the RuleException in the database
        List<RuleException> ruleExceptionList = ruleExceptionRepository.findAll();
        assertThat(ruleExceptionList).hasSize(databaseSizeBeforeUpdate);
        RuleException testRuleException = ruleExceptionList.get(ruleExceptionList.size() - 1);
        assertThat(testRuleException.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingRuleException() throws Exception {
        int databaseSizeBeforeUpdate = ruleExceptionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuleExceptionMockMvc.perform(put("/api/rule-exceptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleException)))
            .andExpect(status().isBadRequest());

        // Validate the RuleException in the database
        List<RuleException> ruleExceptionList = ruleExceptionRepository.findAll();
        assertThat(ruleExceptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRuleException() throws Exception {
        // Initialize the database
        ruleExceptionRepository.saveAndFlush(ruleException);

        int databaseSizeBeforeDelete = ruleExceptionRepository.findAll().size();

        // Delete the ruleException
        restRuleExceptionMockMvc.perform(delete("/api/rule-exceptions/{id}", ruleException.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RuleException> ruleExceptionList = ruleExceptionRepository.findAll();
        assertThat(ruleExceptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
