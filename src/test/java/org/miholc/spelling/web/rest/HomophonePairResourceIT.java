package org.miholc.spelling.web.rest;

import org.miholc.spelling.JhSpell9App;
import org.miholc.spelling.domain.HomophonePair;
import org.miholc.spelling.repository.HomophonePairRepository;

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
 * Integration tests for the {@link HomophonePairResource} REST controller.
 */
@SpringBootTest(classes = JhSpell9App.class)
@AutoConfigureMockMvc
@WithMockUser
public class HomophonePairResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private HomophonePairRepository homophonePairRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHomophonePairMockMvc;

    private HomophonePair homophonePair;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HomophonePair createEntity(EntityManager em) {
        HomophonePair homophonePair = new HomophonePair()
            .text(DEFAULT_TEXT);
        return homophonePair;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HomophonePair createUpdatedEntity(EntityManager em) {
        HomophonePair homophonePair = new HomophonePair()
            .text(UPDATED_TEXT);
        return homophonePair;
    }

    @BeforeEach
    public void initTest() {
        homophonePair = createEntity(em);
    }

    @Test
    @Transactional
    public void createHomophonePair() throws Exception {
        int databaseSizeBeforeCreate = homophonePairRepository.findAll().size();
        // Create the HomophonePair
        restHomophonePairMockMvc.perform(post("/api/homophone-pairs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(homophonePair)))
            .andExpect(status().isCreated());

        // Validate the HomophonePair in the database
        List<HomophonePair> homophonePairList = homophonePairRepository.findAll();
        assertThat(homophonePairList).hasSize(databaseSizeBeforeCreate + 1);
        HomophonePair testHomophonePair = homophonePairList.get(homophonePairList.size() - 1);
        assertThat(testHomophonePair.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createHomophonePairWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = homophonePairRepository.findAll().size();

        // Create the HomophonePair with an existing ID
        homophonePair.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHomophonePairMockMvc.perform(post("/api/homophone-pairs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(homophonePair)))
            .andExpect(status().isBadRequest());

        // Validate the HomophonePair in the database
        List<HomophonePair> homophonePairList = homophonePairRepository.findAll();
        assertThat(homophonePairList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHomophonePairs() throws Exception {
        // Initialize the database
        homophonePairRepository.saveAndFlush(homophonePair);

        // Get all the homophonePairList
        restHomophonePairMockMvc.perform(get("/api/homophone-pairs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(homophonePair.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }
    
    @Test
    @Transactional
    public void getHomophonePair() throws Exception {
        // Initialize the database
        homophonePairRepository.saveAndFlush(homophonePair);

        // Get the homophonePair
        restHomophonePairMockMvc.perform(get("/api/homophone-pairs/{id}", homophonePair.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(homophonePair.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
    }
    @Test
    @Transactional
    public void getNonExistingHomophonePair() throws Exception {
        // Get the homophonePair
        restHomophonePairMockMvc.perform(get("/api/homophone-pairs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHomophonePair() throws Exception {
        // Initialize the database
        homophonePairRepository.saveAndFlush(homophonePair);

        int databaseSizeBeforeUpdate = homophonePairRepository.findAll().size();

        // Update the homophonePair
        HomophonePair updatedHomophonePair = homophonePairRepository.findById(homophonePair.getId()).get();
        // Disconnect from session so that the updates on updatedHomophonePair are not directly saved in db
        em.detach(updatedHomophonePair);
        updatedHomophonePair
            .text(UPDATED_TEXT);

        restHomophonePairMockMvc.perform(put("/api/homophone-pairs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHomophonePair)))
            .andExpect(status().isOk());

        // Validate the HomophonePair in the database
        List<HomophonePair> homophonePairList = homophonePairRepository.findAll();
        assertThat(homophonePairList).hasSize(databaseSizeBeforeUpdate);
        HomophonePair testHomophonePair = homophonePairList.get(homophonePairList.size() - 1);
        assertThat(testHomophonePair.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingHomophonePair() throws Exception {
        int databaseSizeBeforeUpdate = homophonePairRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHomophonePairMockMvc.perform(put("/api/homophone-pairs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(homophonePair)))
            .andExpect(status().isBadRequest());

        // Validate the HomophonePair in the database
        List<HomophonePair> homophonePairList = homophonePairRepository.findAll();
        assertThat(homophonePairList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHomophonePair() throws Exception {
        // Initialize the database
        homophonePairRepository.saveAndFlush(homophonePair);

        int databaseSizeBeforeDelete = homophonePairRepository.findAll().size();

        // Delete the homophonePair
        restHomophonePairMockMvc.perform(delete("/api/homophone-pairs/{id}", homophonePair.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HomophonePair> homophonePairList = homophonePairRepository.findAll();
        assertThat(homophonePairList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
