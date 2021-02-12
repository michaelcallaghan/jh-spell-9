package org.miholc.spelling.web.rest;

import org.miholc.spelling.JhSpell9App;
import org.miholc.spelling.domain.Pgc;
import org.miholc.spelling.repository.PgcRepository;

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

import org.miholc.spelling.domain.enumeration.Frequency;
/**
 * Integration tests for the {@link PgcResource} REST controller.
 */
@SpringBootTest(classes = JhSpell9App.class)
@AutoConfigureMockMvc
@WithMockUser
public class PgcResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Frequency DEFAULT_FREQUENCY = Frequency.REGULAR;
    private static final Frequency UPDATED_FREQUENCY = Frequency.VERY_COMMON;

    @Autowired
    private PgcRepository pgcRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPgcMockMvc;

    private Pgc pgc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pgc createEntity(EntityManager em) {
        Pgc pgc = new Pgc()
            .text(DEFAULT_TEXT)
            .frequency(DEFAULT_FREQUENCY);
        return pgc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pgc createUpdatedEntity(EntityManager em) {
        Pgc pgc = new Pgc()
            .text(UPDATED_TEXT)
            .frequency(UPDATED_FREQUENCY);
        return pgc;
    }

    @BeforeEach
    public void initTest() {
        pgc = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgc() throws Exception {
        int databaseSizeBeforeCreate = pgcRepository.findAll().size();
        // Create the Pgc
        restPgcMockMvc.perform(post("/api/pgcs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pgc)))
            .andExpect(status().isCreated());

        // Validate the Pgc in the database
        List<Pgc> pgcList = pgcRepository.findAll();
        assertThat(pgcList).hasSize(databaseSizeBeforeCreate + 1);
        Pgc testPgc = pgcList.get(pgcList.size() - 1);
        assertThat(testPgc.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testPgc.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
    }

    @Test
    @Transactional
    public void createPgcWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgcRepository.findAll().size();

        // Create the Pgc with an existing ID
        pgc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgcMockMvc.perform(post("/api/pgcs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pgc)))
            .andExpect(status().isBadRequest());

        // Validate the Pgc in the database
        List<Pgc> pgcList = pgcRepository.findAll();
        assertThat(pgcList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPgcs() throws Exception {
        // Initialize the database
        pgcRepository.saveAndFlush(pgc);

        // Get all the pgcList
        restPgcMockMvc.perform(get("/api/pgcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgc.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.toString())));
    }
    
    @Test
    @Transactional
    public void getPgc() throws Exception {
        // Initialize the database
        pgcRepository.saveAndFlush(pgc);

        // Get the pgc
        restPgcMockMvc.perform(get("/api/pgcs/{id}", pgc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pgc.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPgc() throws Exception {
        // Get the pgc
        restPgcMockMvc.perform(get("/api/pgcs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgc() throws Exception {
        // Initialize the database
        pgcRepository.saveAndFlush(pgc);

        int databaseSizeBeforeUpdate = pgcRepository.findAll().size();

        // Update the pgc
        Pgc updatedPgc = pgcRepository.findById(pgc.getId()).get();
        // Disconnect from session so that the updates on updatedPgc are not directly saved in db
        em.detach(updatedPgc);
        updatedPgc
            .text(UPDATED_TEXT)
            .frequency(UPDATED_FREQUENCY);

        restPgcMockMvc.perform(put("/api/pgcs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPgc)))
            .andExpect(status().isOk());

        // Validate the Pgc in the database
        List<Pgc> pgcList = pgcRepository.findAll();
        assertThat(pgcList).hasSize(databaseSizeBeforeUpdate);
        Pgc testPgc = pgcList.get(pgcList.size() - 1);
        assertThat(testPgc.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testPgc.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    public void updateNonExistingPgc() throws Exception {
        int databaseSizeBeforeUpdate = pgcRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgcMockMvc.perform(put("/api/pgcs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pgc)))
            .andExpect(status().isBadRequest());

        // Validate the Pgc in the database
        List<Pgc> pgcList = pgcRepository.findAll();
        assertThat(pgcList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgc() throws Exception {
        // Initialize the database
        pgcRepository.saveAndFlush(pgc);

        int databaseSizeBeforeDelete = pgcRepository.findAll().size();

        // Delete the pgc
        restPgcMockMvc.perform(delete("/api/pgcs/{id}", pgc.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pgc> pgcList = pgcRepository.findAll();
        assertThat(pgcList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
