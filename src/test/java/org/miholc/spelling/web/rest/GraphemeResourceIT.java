package org.miholc.spelling.web.rest;

import org.miholc.spelling.JhSpell9App;
import org.miholc.spelling.domain.Grapheme;
import org.miholc.spelling.repository.GraphemeRepository;

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
 * Integration tests for the {@link GraphemeResource} REST controller.
 */
@SpringBootTest(classes = JhSpell9App.class)
@AutoConfigureMockMvc
@WithMockUser
public class GraphemeResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private GraphemeRepository graphemeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGraphemeMockMvc;

    private Grapheme grapheme;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grapheme createEntity(EntityManager em) {
        Grapheme grapheme = new Grapheme()
            .text(DEFAULT_TEXT);
        return grapheme;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grapheme createUpdatedEntity(EntityManager em) {
        Grapheme grapheme = new Grapheme()
            .text(UPDATED_TEXT);
        return grapheme;
    }

    @BeforeEach
    public void initTest() {
        grapheme = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrapheme() throws Exception {
        int databaseSizeBeforeCreate = graphemeRepository.findAll().size();
        // Create the Grapheme
        restGraphemeMockMvc.perform(post("/api/graphemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grapheme)))
            .andExpect(status().isCreated());

        // Validate the Grapheme in the database
        List<Grapheme> graphemeList = graphemeRepository.findAll();
        assertThat(graphemeList).hasSize(databaseSizeBeforeCreate + 1);
        Grapheme testGrapheme = graphemeList.get(graphemeList.size() - 1);
        assertThat(testGrapheme.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createGraphemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = graphemeRepository.findAll().size();

        // Create the Grapheme with an existing ID
        grapheme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGraphemeMockMvc.perform(post("/api/graphemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grapheme)))
            .andExpect(status().isBadRequest());

        // Validate the Grapheme in the database
        List<Grapheme> graphemeList = graphemeRepository.findAll();
        assertThat(graphemeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = graphemeRepository.findAll().size();
        // set the field null
        grapheme.setText(null);

        // Create the Grapheme, which fails.


        restGraphemeMockMvc.perform(post("/api/graphemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grapheme)))
            .andExpect(status().isBadRequest());

        List<Grapheme> graphemeList = graphemeRepository.findAll();
        assertThat(graphemeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGraphemes() throws Exception {
        // Initialize the database
        graphemeRepository.saveAndFlush(grapheme);

        // Get all the graphemeList
        restGraphemeMockMvc.perform(get("/api/graphemes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grapheme.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }
    
    @Test
    @Transactional
    public void getGrapheme() throws Exception {
        // Initialize the database
        graphemeRepository.saveAndFlush(grapheme);

        // Get the grapheme
        restGraphemeMockMvc.perform(get("/api/graphemes/{id}", grapheme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grapheme.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
    }
    @Test
    @Transactional
    public void getNonExistingGrapheme() throws Exception {
        // Get the grapheme
        restGraphemeMockMvc.perform(get("/api/graphemes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrapheme() throws Exception {
        // Initialize the database
        graphemeRepository.saveAndFlush(grapheme);

        int databaseSizeBeforeUpdate = graphemeRepository.findAll().size();

        // Update the grapheme
        Grapheme updatedGrapheme = graphemeRepository.findById(grapheme.getId()).get();
        // Disconnect from session so that the updates on updatedGrapheme are not directly saved in db
        em.detach(updatedGrapheme);
        updatedGrapheme
            .text(UPDATED_TEXT);

        restGraphemeMockMvc.perform(put("/api/graphemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrapheme)))
            .andExpect(status().isOk());

        // Validate the Grapheme in the database
        List<Grapheme> graphemeList = graphemeRepository.findAll();
        assertThat(graphemeList).hasSize(databaseSizeBeforeUpdate);
        Grapheme testGrapheme = graphemeList.get(graphemeList.size() - 1);
        assertThat(testGrapheme.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingGrapheme() throws Exception {
        int databaseSizeBeforeUpdate = graphemeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGraphemeMockMvc.perform(put("/api/graphemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grapheme)))
            .andExpect(status().isBadRequest());

        // Validate the Grapheme in the database
        List<Grapheme> graphemeList = graphemeRepository.findAll();
        assertThat(graphemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGrapheme() throws Exception {
        // Initialize the database
        graphemeRepository.saveAndFlush(grapheme);

        int databaseSizeBeforeDelete = graphemeRepository.findAll().size();

        // Delete the grapheme
        restGraphemeMockMvc.perform(delete("/api/graphemes/{id}", grapheme.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Grapheme> graphemeList = graphemeRepository.findAll();
        assertThat(graphemeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
