package org.miholc.spelling.web.rest;

import org.miholc.spelling.JhSpell9App;
import org.miholc.spelling.domain.Pgcl;
import org.miholc.spelling.repository.PgclRepository;

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

import org.miholc.spelling.domain.enumeration.Location;
import org.miholc.spelling.domain.enumeration.SyllabicContext;
import org.miholc.spelling.domain.enumeration.Frequency;
/**
 * Integration tests for the {@link PgclResource} REST controller.
 */
@SpringBootTest(classes = JhSpell9App.class)
@AutoConfigureMockMvc
@WithMockUser
public class PgclResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Location DEFAULT_LOCATION = Location.ANYWHERE;
    private static final Location UPDATED_LOCATION = Location.INITIAL;

    private static final SyllabicContext DEFAULT_SYLLABIC_CONTEXT = SyllabicContext.MONO;
    private static final SyllabicContext UPDATED_SYLLABIC_CONTEXT = SyllabicContext.FINAL;

    private static final Frequency DEFAULT_FREQUENCY = Frequency.REGULAR;
    private static final Frequency UPDATED_FREQUENCY = Frequency.VERY_COMMON;

    @Autowired
    private PgclRepository pgclRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPgclMockMvc;

    private Pgcl pgcl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pgcl createEntity(EntityManager em) {
        Pgcl pgcl = new Pgcl()
            .text(DEFAULT_TEXT)
            .location(DEFAULT_LOCATION)
            .syllabicContext(DEFAULT_SYLLABIC_CONTEXT)
            .frequency(DEFAULT_FREQUENCY);
        return pgcl;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pgcl createUpdatedEntity(EntityManager em) {
        Pgcl pgcl = new Pgcl()
            .text(UPDATED_TEXT)
            .location(UPDATED_LOCATION)
            .syllabicContext(UPDATED_SYLLABIC_CONTEXT)
            .frequency(UPDATED_FREQUENCY);
        return pgcl;
    }

    @BeforeEach
    public void initTest() {
        pgcl = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgcl() throws Exception {
        int databaseSizeBeforeCreate = pgclRepository.findAll().size();
        // Create the Pgcl
        restPgclMockMvc.perform(post("/api/pgcls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pgcl)))
            .andExpect(status().isCreated());

        // Validate the Pgcl in the database
        List<Pgcl> pgclList = pgclRepository.findAll();
        assertThat(pgclList).hasSize(databaseSizeBeforeCreate + 1);
        Pgcl testPgcl = pgclList.get(pgclList.size() - 1);
        assertThat(testPgcl.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testPgcl.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testPgcl.getSyllabicContext()).isEqualTo(DEFAULT_SYLLABIC_CONTEXT);
        assertThat(testPgcl.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
    }

    @Test
    @Transactional
    public void createPgclWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgclRepository.findAll().size();

        // Create the Pgcl with an existing ID
        pgcl.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgclMockMvc.perform(post("/api/pgcls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pgcl)))
            .andExpect(status().isBadRequest());

        // Validate the Pgcl in the database
        List<Pgcl> pgclList = pgclRepository.findAll();
        assertThat(pgclList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPgcls() throws Exception {
        // Initialize the database
        pgclRepository.saveAndFlush(pgcl);

        // Get all the pgclList
        restPgclMockMvc.perform(get("/api/pgcls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgcl.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].syllabicContext").value(hasItem(DEFAULT_SYLLABIC_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.toString())));
    }
    
    @Test
    @Transactional
    public void getPgcl() throws Exception {
        // Initialize the database
        pgclRepository.saveAndFlush(pgcl);

        // Get the pgcl
        restPgclMockMvc.perform(get("/api/pgcls/{id}", pgcl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pgcl.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.syllabicContext").value(DEFAULT_SYLLABIC_CONTEXT.toString()))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPgcl() throws Exception {
        // Get the pgcl
        restPgclMockMvc.perform(get("/api/pgcls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgcl() throws Exception {
        // Initialize the database
        pgclRepository.saveAndFlush(pgcl);

        int databaseSizeBeforeUpdate = pgclRepository.findAll().size();

        // Update the pgcl
        Pgcl updatedPgcl = pgclRepository.findById(pgcl.getId()).get();
        // Disconnect from session so that the updates on updatedPgcl are not directly saved in db
        em.detach(updatedPgcl);
        updatedPgcl
            .text(UPDATED_TEXT)
            .location(UPDATED_LOCATION)
            .syllabicContext(UPDATED_SYLLABIC_CONTEXT)
            .frequency(UPDATED_FREQUENCY);

        restPgclMockMvc.perform(put("/api/pgcls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPgcl)))
            .andExpect(status().isOk());

        // Validate the Pgcl in the database
        List<Pgcl> pgclList = pgclRepository.findAll();
        assertThat(pgclList).hasSize(databaseSizeBeforeUpdate);
        Pgcl testPgcl = pgclList.get(pgclList.size() - 1);
        assertThat(testPgcl.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testPgcl.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testPgcl.getSyllabicContext()).isEqualTo(UPDATED_SYLLABIC_CONTEXT);
        assertThat(testPgcl.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    public void updateNonExistingPgcl() throws Exception {
        int databaseSizeBeforeUpdate = pgclRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgclMockMvc.perform(put("/api/pgcls").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pgcl)))
            .andExpect(status().isBadRequest());

        // Validate the Pgcl in the database
        List<Pgcl> pgclList = pgclRepository.findAll();
        assertThat(pgclList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgcl() throws Exception {
        // Initialize the database
        pgclRepository.saveAndFlush(pgcl);

        int databaseSizeBeforeDelete = pgclRepository.findAll().size();

        // Delete the pgcl
        restPgclMockMvc.perform(delete("/api/pgcls/{id}", pgcl.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pgcl> pgclList = pgclRepository.findAll();
        assertThat(pgclList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
