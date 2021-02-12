package org.miholc.spelling.web.rest;

import org.miholc.spelling.JhSpell9App;
import org.miholc.spelling.domain.RuleExample;
import org.miholc.spelling.repository.RuleExampleRepository;

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
 * Integration tests for the {@link RuleExampleResource} REST controller.
 */
@SpringBootTest(classes = JhSpell9App.class)
@AutoConfigureMockMvc
@WithMockUser
public class RuleExampleResourceIT {

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    @Autowired
    private RuleExampleRepository ruleExampleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRuleExampleMockMvc;

    private RuleExample ruleExample;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RuleExample createEntity(EntityManager em) {
        RuleExample ruleExample = new RuleExample()
            .info(DEFAULT_INFO);
        return ruleExample;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RuleExample createUpdatedEntity(EntityManager em) {
        RuleExample ruleExample = new RuleExample()
            .info(UPDATED_INFO);
        return ruleExample;
    }

    @BeforeEach
    public void initTest() {
        ruleExample = createEntity(em);
    }

    @Test
    @Transactional
    public void createRuleExample() throws Exception {
        int databaseSizeBeforeCreate = ruleExampleRepository.findAll().size();
        // Create the RuleExample
        restRuleExampleMockMvc.perform(post("/api/rule-examples").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleExample)))
            .andExpect(status().isCreated());

        // Validate the RuleExample in the database
        List<RuleExample> ruleExampleList = ruleExampleRepository.findAll();
        assertThat(ruleExampleList).hasSize(databaseSizeBeforeCreate + 1);
        RuleExample testRuleExample = ruleExampleList.get(ruleExampleList.size() - 1);
        assertThat(testRuleExample.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    public void createRuleExampleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ruleExampleRepository.findAll().size();

        // Create the RuleExample with an existing ID
        ruleExample.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuleExampleMockMvc.perform(post("/api/rule-examples").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleExample)))
            .andExpect(status().isBadRequest());

        // Validate the RuleExample in the database
        List<RuleExample> ruleExampleList = ruleExampleRepository.findAll();
        assertThat(ruleExampleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRuleExamples() throws Exception {
        // Initialize the database
        ruleExampleRepository.saveAndFlush(ruleExample);

        // Get all the ruleExampleList
        restRuleExampleMockMvc.perform(get("/api/rule-examples?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ruleExample.getId().intValue())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }
    
    @Test
    @Transactional
    public void getRuleExample() throws Exception {
        // Initialize the database
        ruleExampleRepository.saveAndFlush(ruleExample);

        // Get the ruleExample
        restRuleExampleMockMvc.perform(get("/api/rule-examples/{id}", ruleExample.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ruleExample.getId().intValue()))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }
    @Test
    @Transactional
    public void getNonExistingRuleExample() throws Exception {
        // Get the ruleExample
        restRuleExampleMockMvc.perform(get("/api/rule-examples/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRuleExample() throws Exception {
        // Initialize the database
        ruleExampleRepository.saveAndFlush(ruleExample);

        int databaseSizeBeforeUpdate = ruleExampleRepository.findAll().size();

        // Update the ruleExample
        RuleExample updatedRuleExample = ruleExampleRepository.findById(ruleExample.getId()).get();
        // Disconnect from session so that the updates on updatedRuleExample are not directly saved in db
        em.detach(updatedRuleExample);
        updatedRuleExample
            .info(UPDATED_INFO);

        restRuleExampleMockMvc.perform(put("/api/rule-examples").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRuleExample)))
            .andExpect(status().isOk());

        // Validate the RuleExample in the database
        List<RuleExample> ruleExampleList = ruleExampleRepository.findAll();
        assertThat(ruleExampleList).hasSize(databaseSizeBeforeUpdate);
        RuleExample testRuleExample = ruleExampleList.get(ruleExampleList.size() - 1);
        assertThat(testRuleExample.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingRuleExample() throws Exception {
        int databaseSizeBeforeUpdate = ruleExampleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuleExampleMockMvc.perform(put("/api/rule-examples").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleExample)))
            .andExpect(status().isBadRequest());

        // Validate the RuleExample in the database
        List<RuleExample> ruleExampleList = ruleExampleRepository.findAll();
        assertThat(ruleExampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRuleExample() throws Exception {
        // Initialize the database
        ruleExampleRepository.saveAndFlush(ruleExample);

        int databaseSizeBeforeDelete = ruleExampleRepository.findAll().size();

        // Delete the ruleExample
        restRuleExampleMockMvc.perform(delete("/api/rule-examples/{id}", ruleExample.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RuleExample> ruleExampleList = ruleExampleRepository.findAll();
        assertThat(ruleExampleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
