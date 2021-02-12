package org.miholc.spelling.web.rest;

import org.miholc.spelling.JhSpell9App;
import org.miholc.spelling.domain.Word;
import org.miholc.spelling.repository.WordRepository;

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
 * Integration tests for the {@link WordResource} REST controller.
 */
@SpringBootTest(classes = JhSpell9App.class)
@AutoConfigureMockMvc
@WithMockUser
public class WordResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_IPA_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_IPA_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SOUND_FILE = "AAAAAAAAAA";
    private static final String UPDATED_SOUND_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_USAGE_SOUND_FILE = "AAAAAAAAAA";
    private static final String UPDATED_USAGE_SOUND_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_IPA_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ALT_IPA_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_SOUND_FILE = "AAAAAAAAAA";
    private static final String UPDATED_ALT_SOUND_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_USAGE_SOUND_FILE = "AAAAAAAAAA";
    private static final String UPDATED_ALT_USAGE_SOUND_FILE = "BBBBBBBBBB";

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWordMockMvc;

    private Word word;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Word createEntity(EntityManager em) {
        Word word = new Word()
            .text(DEFAULT_TEXT)
            .ipaText(DEFAULT_IPA_TEXT)
            .soundFile(DEFAULT_SOUND_FILE)
            .usageSoundFile(DEFAULT_USAGE_SOUND_FILE)
            .altIpaText(DEFAULT_ALT_IPA_TEXT)
            .altSoundFile(DEFAULT_ALT_SOUND_FILE)
            .altUsageSoundFile(DEFAULT_ALT_USAGE_SOUND_FILE);
        return word;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Word createUpdatedEntity(EntityManager em) {
        Word word = new Word()
            .text(UPDATED_TEXT)
            .ipaText(UPDATED_IPA_TEXT)
            .soundFile(UPDATED_SOUND_FILE)
            .usageSoundFile(UPDATED_USAGE_SOUND_FILE)
            .altIpaText(UPDATED_ALT_IPA_TEXT)
            .altSoundFile(UPDATED_ALT_SOUND_FILE)
            .altUsageSoundFile(UPDATED_ALT_USAGE_SOUND_FILE);
        return word;
    }

    @BeforeEach
    public void initTest() {
        word = createEntity(em);
    }

    @Test
    @Transactional
    public void createWord() throws Exception {
        int databaseSizeBeforeCreate = wordRepository.findAll().size();
        // Create the Word
        restWordMockMvc.perform(post("/api/words").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(word)))
            .andExpect(status().isCreated());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeCreate + 1);
        Word testWord = wordList.get(wordList.size() - 1);
        assertThat(testWord.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testWord.getIpaText()).isEqualTo(DEFAULT_IPA_TEXT);
        assertThat(testWord.getSoundFile()).isEqualTo(DEFAULT_SOUND_FILE);
        assertThat(testWord.getUsageSoundFile()).isEqualTo(DEFAULT_USAGE_SOUND_FILE);
        assertThat(testWord.getAltIpaText()).isEqualTo(DEFAULT_ALT_IPA_TEXT);
        assertThat(testWord.getAltSoundFile()).isEqualTo(DEFAULT_ALT_SOUND_FILE);
        assertThat(testWord.getAltUsageSoundFile()).isEqualTo(DEFAULT_ALT_USAGE_SOUND_FILE);
    }

    @Test
    @Transactional
    public void createWordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wordRepository.findAll().size();

        // Create the Word with an existing ID
        word.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWordMockMvc.perform(post("/api/words").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(word)))
            .andExpect(status().isBadRequest());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = wordRepository.findAll().size();
        // set the field null
        word.setText(null);

        // Create the Word, which fails.


        restWordMockMvc.perform(post("/api/words").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(word)))
            .andExpect(status().isBadRequest());

        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWords() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList
        restWordMockMvc.perform(get("/api/words?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(word.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].ipaText").value(hasItem(DEFAULT_IPA_TEXT)))
            .andExpect(jsonPath("$.[*].soundFile").value(hasItem(DEFAULT_SOUND_FILE)))
            .andExpect(jsonPath("$.[*].usageSoundFile").value(hasItem(DEFAULT_USAGE_SOUND_FILE)))
            .andExpect(jsonPath("$.[*].altIpaText").value(hasItem(DEFAULT_ALT_IPA_TEXT)))
            .andExpect(jsonPath("$.[*].altSoundFile").value(hasItem(DEFAULT_ALT_SOUND_FILE)))
            .andExpect(jsonPath("$.[*].altUsageSoundFile").value(hasItem(DEFAULT_ALT_USAGE_SOUND_FILE)));
    }
    
    @Test
    @Transactional
    public void getWord() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get the word
        restWordMockMvc.perform(get("/api/words/{id}", word.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(word.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.ipaText").value(DEFAULT_IPA_TEXT))
            .andExpect(jsonPath("$.soundFile").value(DEFAULT_SOUND_FILE))
            .andExpect(jsonPath("$.usageSoundFile").value(DEFAULT_USAGE_SOUND_FILE))
            .andExpect(jsonPath("$.altIpaText").value(DEFAULT_ALT_IPA_TEXT))
            .andExpect(jsonPath("$.altSoundFile").value(DEFAULT_ALT_SOUND_FILE))
            .andExpect(jsonPath("$.altUsageSoundFile").value(DEFAULT_ALT_USAGE_SOUND_FILE));
    }
    @Test
    @Transactional
    public void getNonExistingWord() throws Exception {
        // Get the word
        restWordMockMvc.perform(get("/api/words/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWord() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        int databaseSizeBeforeUpdate = wordRepository.findAll().size();

        // Update the word
        Word updatedWord = wordRepository.findById(word.getId()).get();
        // Disconnect from session so that the updates on updatedWord are not directly saved in db
        em.detach(updatedWord);
        updatedWord
            .text(UPDATED_TEXT)
            .ipaText(UPDATED_IPA_TEXT)
            .soundFile(UPDATED_SOUND_FILE)
            .usageSoundFile(UPDATED_USAGE_SOUND_FILE)
            .altIpaText(UPDATED_ALT_IPA_TEXT)
            .altSoundFile(UPDATED_ALT_SOUND_FILE)
            .altUsageSoundFile(UPDATED_ALT_USAGE_SOUND_FILE);

        restWordMockMvc.perform(put("/api/words").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWord)))
            .andExpect(status().isOk());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeUpdate);
        Word testWord = wordList.get(wordList.size() - 1);
        assertThat(testWord.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testWord.getIpaText()).isEqualTo(UPDATED_IPA_TEXT);
        assertThat(testWord.getSoundFile()).isEqualTo(UPDATED_SOUND_FILE);
        assertThat(testWord.getUsageSoundFile()).isEqualTo(UPDATED_USAGE_SOUND_FILE);
        assertThat(testWord.getAltIpaText()).isEqualTo(UPDATED_ALT_IPA_TEXT);
        assertThat(testWord.getAltSoundFile()).isEqualTo(UPDATED_ALT_SOUND_FILE);
        assertThat(testWord.getAltUsageSoundFile()).isEqualTo(UPDATED_ALT_USAGE_SOUND_FILE);
    }

    @Test
    @Transactional
    public void updateNonExistingWord() throws Exception {
        int databaseSizeBeforeUpdate = wordRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWordMockMvc.perform(put("/api/words").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(word)))
            .andExpect(status().isBadRequest());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWord() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        int databaseSizeBeforeDelete = wordRepository.findAll().size();

        // Delete the word
        restWordMockMvc.perform(delete("/api/words/{id}", word.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
