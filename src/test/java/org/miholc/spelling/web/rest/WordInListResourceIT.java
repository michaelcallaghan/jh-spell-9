package org.miholc.spelling.web.rest;

import org.miholc.spelling.JhSpell9App;
import org.miholc.spelling.domain.WordInList;
import org.miholc.spelling.repository.WordInListRepository;

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
 * Integration tests for the {@link WordInListResource} REST controller.
 */
@SpringBootTest(classes = JhSpell9App.class)
@AutoConfigureMockMvc
@WithMockUser
public class WordInListResourceIT {

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    @Autowired
    private WordInListRepository wordInListRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWordInListMockMvc;

    private WordInList wordInList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WordInList createEntity(EntityManager em) {
        WordInList wordInList = new WordInList()
            .position(DEFAULT_POSITION);
        return wordInList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WordInList createUpdatedEntity(EntityManager em) {
        WordInList wordInList = new WordInList()
            .position(UPDATED_POSITION);
        return wordInList;
    }

    @BeforeEach
    public void initTest() {
        wordInList = createEntity(em);
    }

    @Test
    @Transactional
    public void createWordInList() throws Exception {
        int databaseSizeBeforeCreate = wordInListRepository.findAll().size();
        // Create the WordInList
        restWordInListMockMvc.perform(post("/api/word-in-lists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordInList)))
            .andExpect(status().isCreated());

        // Validate the WordInList in the database
        List<WordInList> wordInListList = wordInListRepository.findAll();
        assertThat(wordInListList).hasSize(databaseSizeBeforeCreate + 1);
        WordInList testWordInList = wordInListList.get(wordInListList.size() - 1);
        assertThat(testWordInList.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    public void createWordInListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wordInListRepository.findAll().size();

        // Create the WordInList with an existing ID
        wordInList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWordInListMockMvc.perform(post("/api/word-in-lists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordInList)))
            .andExpect(status().isBadRequest());

        // Validate the WordInList in the database
        List<WordInList> wordInListList = wordInListRepository.findAll();
        assertThat(wordInListList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWordInLists() throws Exception {
        // Initialize the database
        wordInListRepository.saveAndFlush(wordInList);

        // Get all the wordInListList
        restWordInListMockMvc.perform(get("/api/word-in-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wordInList.getId().intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));
    }
    
    @Test
    @Transactional
    public void getWordInList() throws Exception {
        // Initialize the database
        wordInListRepository.saveAndFlush(wordInList);

        // Get the wordInList
        restWordInListMockMvc.perform(get("/api/word-in-lists/{id}", wordInList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wordInList.getId().intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION));
    }
    @Test
    @Transactional
    public void getNonExistingWordInList() throws Exception {
        // Get the wordInList
        restWordInListMockMvc.perform(get("/api/word-in-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWordInList() throws Exception {
        // Initialize the database
        wordInListRepository.saveAndFlush(wordInList);

        int databaseSizeBeforeUpdate = wordInListRepository.findAll().size();

        // Update the wordInList
        WordInList updatedWordInList = wordInListRepository.findById(wordInList.getId()).get();
        // Disconnect from session so that the updates on updatedWordInList are not directly saved in db
        em.detach(updatedWordInList);
        updatedWordInList
            .position(UPDATED_POSITION);

        restWordInListMockMvc.perform(put("/api/word-in-lists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWordInList)))
            .andExpect(status().isOk());

        // Validate the WordInList in the database
        List<WordInList> wordInListList = wordInListRepository.findAll();
        assertThat(wordInListList).hasSize(databaseSizeBeforeUpdate);
        WordInList testWordInList = wordInListList.get(wordInListList.size() - 1);
        assertThat(testWordInList.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void updateNonExistingWordInList() throws Exception {
        int databaseSizeBeforeUpdate = wordInListRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWordInListMockMvc.perform(put("/api/word-in-lists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordInList)))
            .andExpect(status().isBadRequest());

        // Validate the WordInList in the database
        List<WordInList> wordInListList = wordInListRepository.findAll();
        assertThat(wordInListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWordInList() throws Exception {
        // Initialize the database
        wordInListRepository.saveAndFlush(wordInList);

        int databaseSizeBeforeDelete = wordInListRepository.findAll().size();

        // Delete the wordInList
        restWordInListMockMvc.perform(delete("/api/word-in-lists/{id}", wordInList.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WordInList> wordInListList = wordInListRepository.findAll();
        assertThat(wordInListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
