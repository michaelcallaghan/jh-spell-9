package org.miholc.spelling.web.rest;

import org.miholc.spelling.JhSpell9App;
import org.miholc.spelling.domain.NationalEducationalLevel;
import org.miholc.spelling.repository.NationalEducationalLevelRepository;

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
 * Integration tests for the {@link NationalEducationalLevelResource} REST controller.
 */
@SpringBootTest(classes = JhSpell9App.class)
@AutoConfigureMockMvc
@WithMockUser
public class NationalEducationalLevelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    @Autowired
    private NationalEducationalLevelRepository nationalEducationalLevelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNationalEducationalLevelMockMvc;

    private NationalEducationalLevel nationalEducationalLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NationalEducationalLevel createEntity(EntityManager em) {
        NationalEducationalLevel nationalEducationalLevel = new NationalEducationalLevel()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .level(DEFAULT_LEVEL);
        return nationalEducationalLevel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NationalEducationalLevel createUpdatedEntity(EntityManager em) {
        NationalEducationalLevel nationalEducationalLevel = new NationalEducationalLevel()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .level(UPDATED_LEVEL);
        return nationalEducationalLevel;
    }

    @BeforeEach
    public void initTest() {
        nationalEducationalLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createNationalEducationalLevel() throws Exception {
        int databaseSizeBeforeCreate = nationalEducationalLevelRepository.findAll().size();
        // Create the NationalEducationalLevel
        restNationalEducationalLevelMockMvc.perform(post("/api/national-educational-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nationalEducationalLevel)))
            .andExpect(status().isCreated());

        // Validate the NationalEducationalLevel in the database
        List<NationalEducationalLevel> nationalEducationalLevelList = nationalEducationalLevelRepository.findAll();
        assertThat(nationalEducationalLevelList).hasSize(databaseSizeBeforeCreate + 1);
        NationalEducationalLevel testNationalEducationalLevel = nationalEducationalLevelList.get(nationalEducationalLevelList.size() - 1);
        assertThat(testNationalEducationalLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNationalEducationalLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNationalEducationalLevel.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createNationalEducationalLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nationalEducationalLevelRepository.findAll().size();

        // Create the NationalEducationalLevel with an existing ID
        nationalEducationalLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNationalEducationalLevelMockMvc.perform(post("/api/national-educational-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nationalEducationalLevel)))
            .andExpect(status().isBadRequest());

        // Validate the NationalEducationalLevel in the database
        List<NationalEducationalLevel> nationalEducationalLevelList = nationalEducationalLevelRepository.findAll();
        assertThat(nationalEducationalLevelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNationalEducationalLevels() throws Exception {
        // Initialize the database
        nationalEducationalLevelRepository.saveAndFlush(nationalEducationalLevel);

        // Get all the nationalEducationalLevelList
        restNationalEducationalLevelMockMvc.perform(get("/api/national-educational-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nationalEducationalLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }
    
    @Test
    @Transactional
    public void getNationalEducationalLevel() throws Exception {
        // Initialize the database
        nationalEducationalLevelRepository.saveAndFlush(nationalEducationalLevel);

        // Get the nationalEducationalLevel
        restNationalEducationalLevelMockMvc.perform(get("/api/national-educational-levels/{id}", nationalEducationalLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nationalEducationalLevel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL));
    }
    @Test
    @Transactional
    public void getNonExistingNationalEducationalLevel() throws Exception {
        // Get the nationalEducationalLevel
        restNationalEducationalLevelMockMvc.perform(get("/api/national-educational-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNationalEducationalLevel() throws Exception {
        // Initialize the database
        nationalEducationalLevelRepository.saveAndFlush(nationalEducationalLevel);

        int databaseSizeBeforeUpdate = nationalEducationalLevelRepository.findAll().size();

        // Update the nationalEducationalLevel
        NationalEducationalLevel updatedNationalEducationalLevel = nationalEducationalLevelRepository.findById(nationalEducationalLevel.getId()).get();
        // Disconnect from session so that the updates on updatedNationalEducationalLevel are not directly saved in db
        em.detach(updatedNationalEducationalLevel);
        updatedNationalEducationalLevel
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .level(UPDATED_LEVEL);

        restNationalEducationalLevelMockMvc.perform(put("/api/national-educational-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNationalEducationalLevel)))
            .andExpect(status().isOk());

        // Validate the NationalEducationalLevel in the database
        List<NationalEducationalLevel> nationalEducationalLevelList = nationalEducationalLevelRepository.findAll();
        assertThat(nationalEducationalLevelList).hasSize(databaseSizeBeforeUpdate);
        NationalEducationalLevel testNationalEducationalLevel = nationalEducationalLevelList.get(nationalEducationalLevelList.size() - 1);
        assertThat(testNationalEducationalLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNationalEducationalLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNationalEducationalLevel.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingNationalEducationalLevel() throws Exception {
        int databaseSizeBeforeUpdate = nationalEducationalLevelRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNationalEducationalLevelMockMvc.perform(put("/api/national-educational-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nationalEducationalLevel)))
            .andExpect(status().isBadRequest());

        // Validate the NationalEducationalLevel in the database
        List<NationalEducationalLevel> nationalEducationalLevelList = nationalEducationalLevelRepository.findAll();
        assertThat(nationalEducationalLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNationalEducationalLevel() throws Exception {
        // Initialize the database
        nationalEducationalLevelRepository.saveAndFlush(nationalEducationalLevel);

        int databaseSizeBeforeDelete = nationalEducationalLevelRepository.findAll().size();

        // Delete the nationalEducationalLevel
        restNationalEducationalLevelMockMvc.perform(delete("/api/national-educational-levels/{id}", nationalEducationalLevel.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NationalEducationalLevel> nationalEducationalLevelList = nationalEducationalLevelRepository.findAll();
        assertThat(nationalEducationalLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
