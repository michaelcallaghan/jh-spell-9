package org.miholc.spelling.web.rest;

import org.miholc.spelling.JhSpell9App;
import org.miholc.spelling.domain.Rule;
import org.miholc.spelling.repository.RuleRepository;

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
 * Integration tests for the {@link RuleResource} REST controller.
 */
@SpringBootTest(classes = JhSpell9App.class)
@AutoConfigureMockMvc
@WithMockUser
public class RuleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRuleMockMvc;

    private Rule rule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rule createEntity(EntityManager em) {
        Rule rule = new Rule()
            .name(DEFAULT_NAME)
            .text(DEFAULT_TEXT);
        return rule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rule createUpdatedEntity(EntityManager em) {
        Rule rule = new Rule()
            .name(UPDATED_NAME)
            .text(UPDATED_TEXT);
        return rule;
    }

    @BeforeEach
    public void initTest() {
        rule = createEntity(em);
    }

    @Test
    @Transactional
    public void createRule() throws Exception {
        int databaseSizeBeforeCreate = ruleRepository.findAll().size();
        // Create the Rule
        restRuleMockMvc.perform(post("/api/rules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rule)))
            .andExpect(status().isCreated());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeCreate + 1);
        Rule testRule = ruleList.get(ruleList.size() - 1);
        assertThat(testRule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRule.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ruleRepository.findAll().size();

        // Create the Rule with an existing ID
        rule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuleMockMvc.perform(post("/api/rules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rule)))
            .andExpect(status().isBadRequest());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleRepository.findAll().size();
        // set the field null
        rule.setName(null);

        // Create the Rule, which fails.


        restRuleMockMvc.perform(post("/api/rules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rule)))
            .andExpect(status().isBadRequest());

        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleRepository.findAll().size();
        // set the field null
        rule.setText(null);

        // Create the Rule, which fails.


        restRuleMockMvc.perform(post("/api/rules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rule)))
            .andExpect(status().isBadRequest());

        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRules() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList
        restRuleMockMvc.perform(get("/api/rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rule.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }
    
    @Test
    @Transactional
    public void getRule() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get the rule
        restRuleMockMvc.perform(get("/api/rules/{id}", rule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rule.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
    }
    @Test
    @Transactional
    public void getNonExistingRule() throws Exception {
        // Get the rule
        restRuleMockMvc.perform(get("/api/rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRule() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        int databaseSizeBeforeUpdate = ruleRepository.findAll().size();

        // Update the rule
        Rule updatedRule = ruleRepository.findById(rule.getId()).get();
        // Disconnect from session so that the updates on updatedRule are not directly saved in db
        em.detach(updatedRule);
        updatedRule
            .name(UPDATED_NAME)
            .text(UPDATED_TEXT);

        restRuleMockMvc.perform(put("/api/rules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRule)))
            .andExpect(status().isOk());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeUpdate);
        Rule testRule = ruleList.get(ruleList.size() - 1);
        assertThat(testRule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRule.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingRule() throws Exception {
        int databaseSizeBeforeUpdate = ruleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuleMockMvc.perform(put("/api/rules").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rule)))
            .andExpect(status().isBadRequest());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRule() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        int databaseSizeBeforeDelete = ruleRepository.findAll().size();

        // Delete the rule
        restRuleMockMvc.perform(delete("/api/rules/{id}", rule.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
