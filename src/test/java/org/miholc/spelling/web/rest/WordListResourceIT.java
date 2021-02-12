package org.miholc.spelling.web.rest;

import org.miholc.spelling.JhSpell9App;
import org.miholc.spelling.domain.WordList;
import org.miholc.spelling.repository.WordListRepository;

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
 * Integration tests for the {@link WordListResource} REST controller.
 */
@SpringBootTest(classes = JhSpell9App.class)
@AutoConfigureMockMvc
@WithMockUser
public class WordListResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private WordListRepository wordListRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWordListMockMvc;

    private WordList wordList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WordList createEntity(EntityManager em) {
        WordList wordList = new WordList()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return wordList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WordList createUpdatedEntity(EntityManager em) {
        WordList wordList = new WordList()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return wordList;
    }

    @BeforeEach
    public void initTest() {
        wordList = createEntity(em);
    }

    @Test
    @Transactional
    public void createWordList() throws Exception {
        int databaseSizeBeforeCreate = wordListRepository.findAll().size();
        // Create the WordList
        restWordListMockMvc.perform(post("/api/word-lists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordList)))
            .andExpect(status().isCreated());

        // Validate the WordList in the database
        List<WordList> wordListList = wordListRepository.findAll();
        assertThat(wordListList).hasSize(databaseSizeBeforeCreate + 1);
        WordList testWordList = wordListList.get(wordListList.size() - 1);
        assertThat(testWordList.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWordList.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createWordListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wordListRepository.findAll().size();

        // Create the WordList with an existing ID
        wordList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWordListMockMvc.perform(post("/api/word-lists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordList)))
            .andExpect(status().isBadRequest());

        // Validate the WordList in the database
        List<WordList> wordListList = wordListRepository.findAll();
        assertThat(wordListList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWordLists() throws Exception {
        // Initialize the database
        wordListRepository.saveAndFlush(wordList);

        // Get all the wordListList
        restWordListMockMvc.perform(get("/api/word-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wordList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getWordList() throws Exception {
        // Initialize the database
        wordListRepository.saveAndFlush(wordList);

        // Get the wordList
        restWordListMockMvc.perform(get("/api/word-lists/{id}", wordList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wordList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingWordList() throws Exception {
        // Get the wordList
        restWordListMockMvc.perform(get("/api/word-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWordList() throws Exception {
        // Initialize the database
        wordListRepository.saveAndFlush(wordList);

        int databaseSizeBeforeUpdate = wordListRepository.findAll().size();

        // Update the wordList
        WordList updatedWordList = wordListRepository.findById(wordList.getId()).get();
        // Disconnect from session so that the updates on updatedWordList are not directly saved in db
        em.detach(updatedWordList);
        updatedWordList
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restWordListMockMvc.perform(put("/api/word-lists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWordList)))
            .andExpect(status().isOk());

        // Validate the WordList in the database
        List<WordList> wordListList = wordListRepository.findAll();
        assertThat(wordListList).hasSize(databaseSizeBeforeUpdate);
        WordList testWordList = wordListList.get(wordListList.size() - 1);
        assertThat(testWordList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWordList.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingWordList() throws Exception {
        int databaseSizeBeforeUpdate = wordListRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWordListMockMvc.perform(put("/api/word-lists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordList)))
            .andExpect(status().isBadRequest());

        // Validate the WordList in the database
        List<WordList> wordListList = wordListRepository.findAll();
        assertThat(wordListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWordList() throws Exception {
        // Initialize the database
        wordListRepository.saveAndFlush(wordList);

        int databaseSizeBeforeDelete = wordListRepository.findAll().size();

        // Delete the wordList
        restWordListMockMvc.perform(delete("/api/word-lists/{id}", wordList.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WordList> wordListList = wordListRepository.findAll();
        assertThat(wordListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
