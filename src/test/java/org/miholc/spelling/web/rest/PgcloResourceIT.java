package org.miholc.spelling.web.rest;

import org.miholc.spelling.JhSpell9App;
import org.miholc.spelling.domain.Pgclo;
import org.miholc.spelling.repository.PgcloRepository;

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
 * Integration tests for the {@link PgcloResource} REST controller.
 */
@SpringBootTest(classes = JhSpell9App.class)
@AutoConfigureMockMvc
@WithMockUser
public class PgcloResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Integer DEFAULT_START = 1;
    private static final Integer UPDATED_START = 2;

    private static final Integer DEFAULT_END = 1;
    private static final Integer UPDATED_END = 2;

    @Autowired
    private PgcloRepository pgcloRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPgcloMockMvc;

    private Pgclo pgclo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pgclo createEntity(EntityManager em) {
        Pgclo pgclo = new Pgclo()
            .text(DEFAULT_TEXT)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return pgclo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pgclo createUpdatedEntity(EntityManager em) {
        Pgclo pgclo = new Pgclo()
            .text(UPDATED_TEXT)
            .start(UPDATED_START)
            .end(UPDATED_END);
        return pgclo;
    }

    @BeforeEach
    public void initTest() {
        pgclo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgclo() throws Exception {
        int databaseSizeBeforeCreate = pgcloRepository.findAll().size();
        // Create the Pgclo
        restPgcloMockMvc.perform(post("/api/pgclos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pgclo)))
            .andExpect(status().isCreated());

        // Validate the Pgclo in the database
        List<Pgclo> pgcloList = pgcloRepository.findAll();
        assertThat(pgcloList).hasSize(databaseSizeBeforeCreate + 1);
        Pgclo testPgclo = pgcloList.get(pgcloList.size() - 1);
        assertThat(testPgclo.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testPgclo.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testPgclo.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createPgcloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgcloRepository.findAll().size();

        // Create the Pgclo with an existing ID
        pgclo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgcloMockMvc.perform(post("/api/pgclos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pgclo)))
            .andExpect(status().isBadRequest());

        // Validate the Pgclo in the database
        List<Pgclo> pgcloList = pgcloRepository.findAll();
        assertThat(pgcloList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPgclos() throws Exception {
        // Initialize the database
        pgcloRepository.saveAndFlush(pgclo);

        // Get all the pgcloList
        restPgcloMockMvc.perform(get("/api/pgclos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgclo.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START)))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END)));
    }
    
    @Test
    @Transactional
    public void getPgclo() throws Exception {
        // Initialize the database
        pgcloRepository.saveAndFlush(pgclo);

        // Get the pgclo
        restPgcloMockMvc.perform(get("/api/pgclos/{id}", pgclo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pgclo.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.start").value(DEFAULT_START))
            .andExpect(jsonPath("$.end").value(DEFAULT_END));
    }
    @Test
    @Transactional
    public void getNonExistingPgclo() throws Exception {
        // Get the pgclo
        restPgcloMockMvc.perform(get("/api/pgclos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgclo() throws Exception {
        // Initialize the database
        pgcloRepository.saveAndFlush(pgclo);

        int databaseSizeBeforeUpdate = pgcloRepository.findAll().size();

        // Update the pgclo
        Pgclo updatedPgclo = pgcloRepository.findById(pgclo.getId()).get();
        // Disconnect from session so that the updates on updatedPgclo are not directly saved in db
        em.detach(updatedPgclo);
        updatedPgclo
            .text(UPDATED_TEXT)
            .start(UPDATED_START)
            .end(UPDATED_END);

        restPgcloMockMvc.perform(put("/api/pgclos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPgclo)))
            .andExpect(status().isOk());

        // Validate the Pgclo in the database
        List<Pgclo> pgcloList = pgcloRepository.findAll();
        assertThat(pgcloList).hasSize(databaseSizeBeforeUpdate);
        Pgclo testPgclo = pgcloList.get(pgcloList.size() - 1);
        assertThat(testPgclo.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testPgclo.getStart()).isEqualTo(UPDATED_START);
        assertThat(testPgclo.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingPgclo() throws Exception {
        int databaseSizeBeforeUpdate = pgcloRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgcloMockMvc.perform(put("/api/pgclos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pgclo)))
            .andExpect(status().isBadRequest());

        // Validate the Pgclo in the database
        List<Pgclo> pgcloList = pgcloRepository.findAll();
        assertThat(pgcloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgclo() throws Exception {
        // Initialize the database
        pgcloRepository.saveAndFlush(pgclo);

        int databaseSizeBeforeDelete = pgcloRepository.findAll().size();

        // Delete the pgclo
        restPgcloMockMvc.perform(delete("/api/pgclos/{id}", pgclo.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pgclo> pgcloList = pgcloRepository.findAll();
        assertThat(pgcloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
