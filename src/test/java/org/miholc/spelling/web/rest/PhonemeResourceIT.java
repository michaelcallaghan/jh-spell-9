package org.miholc.spelling.web.rest;

import org.miholc.spelling.JhSpell9App;
import org.miholc.spelling.domain.Phoneme;
import org.miholc.spelling.repository.PhonemeRepository;

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
 * Integration tests for the {@link PhonemeResource} REST controller.
 */
@SpringBootTest(classes = JhSpell9App.class)
@AutoConfigureMockMvc
@WithMockUser
public class PhonemeResourceIT {

    private static final String DEFAULT_IPA = "AAAAAAAAAA";
    private static final String UPDATED_IPA = "BBBBBBBBBB";

    private static final String DEFAULT_AKA = "AAAAAAAAAA";
    private static final String UPDATED_AKA = "BBBBBBBBBB";

    private static final String DEFAULT_INFORMAL_UK_GOV = "AAAAAAAAAA";
    private static final String UPDATED_INFORMAL_UK_GOV = "BBBBBBBBBB";

    private static final String DEFAULT_WIKI_IPA = "AAAAAAAAAA";
    private static final String UPDATED_WIKI_IPA = "BBBBBBBBBB";

    @Autowired
    private PhonemeRepository phonemeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhonemeMockMvc;

    private Phoneme phoneme;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phoneme createEntity(EntityManager em) {
        Phoneme phoneme = new Phoneme()
            .ipa(DEFAULT_IPA)
            .aka(DEFAULT_AKA)
            .informalUkGov(DEFAULT_INFORMAL_UK_GOV)
            .wikiIpa(DEFAULT_WIKI_IPA);
        return phoneme;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phoneme createUpdatedEntity(EntityManager em) {
        Phoneme phoneme = new Phoneme()
            .ipa(UPDATED_IPA)
            .aka(UPDATED_AKA)
            .informalUkGov(UPDATED_INFORMAL_UK_GOV)
            .wikiIpa(UPDATED_WIKI_IPA);
        return phoneme;
    }

    @BeforeEach
    public void initTest() {
        phoneme = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhoneme() throws Exception {
        int databaseSizeBeforeCreate = phonemeRepository.findAll().size();
        // Create the Phoneme
        restPhonemeMockMvc.perform(post("/api/phonemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneme)))
            .andExpect(status().isCreated());

        // Validate the Phoneme in the database
        List<Phoneme> phonemeList = phonemeRepository.findAll();
        assertThat(phonemeList).hasSize(databaseSizeBeforeCreate + 1);
        Phoneme testPhoneme = phonemeList.get(phonemeList.size() - 1);
        assertThat(testPhoneme.getIpa()).isEqualTo(DEFAULT_IPA);
        assertThat(testPhoneme.getAka()).isEqualTo(DEFAULT_AKA);
        assertThat(testPhoneme.getInformalUkGov()).isEqualTo(DEFAULT_INFORMAL_UK_GOV);
        assertThat(testPhoneme.getWikiIpa()).isEqualTo(DEFAULT_WIKI_IPA);
    }

    @Test
    @Transactional
    public void createPhonemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phonemeRepository.findAll().size();

        // Create the Phoneme with an existing ID
        phoneme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhonemeMockMvc.perform(post("/api/phonemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneme)))
            .andExpect(status().isBadRequest());

        // Validate the Phoneme in the database
        List<Phoneme> phonemeList = phonemeRepository.findAll();
        assertThat(phonemeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIpaIsRequired() throws Exception {
        int databaseSizeBeforeTest = phonemeRepository.findAll().size();
        // set the field null
        phoneme.setIpa(null);

        // Create the Phoneme, which fails.


        restPhonemeMockMvc.perform(post("/api/phonemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneme)))
            .andExpect(status().isBadRequest());

        List<Phoneme> phonemeList = phonemeRepository.findAll();
        assertThat(phonemeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhonemes() throws Exception {
        // Initialize the database
        phonemeRepository.saveAndFlush(phoneme);

        // Get all the phonemeList
        restPhonemeMockMvc.perform(get("/api/phonemes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phoneme.getId().intValue())))
            .andExpect(jsonPath("$.[*].ipa").value(hasItem(DEFAULT_IPA)))
            .andExpect(jsonPath("$.[*].aka").value(hasItem(DEFAULT_AKA)))
            .andExpect(jsonPath("$.[*].informalUkGov").value(hasItem(DEFAULT_INFORMAL_UK_GOV)))
            .andExpect(jsonPath("$.[*].wikiIpa").value(hasItem(DEFAULT_WIKI_IPA)));
    }
    
    @Test
    @Transactional
    public void getPhoneme() throws Exception {
        // Initialize the database
        phonemeRepository.saveAndFlush(phoneme);

        // Get the phoneme
        restPhonemeMockMvc.perform(get("/api/phonemes/{id}", phoneme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phoneme.getId().intValue()))
            .andExpect(jsonPath("$.ipa").value(DEFAULT_IPA))
            .andExpect(jsonPath("$.aka").value(DEFAULT_AKA))
            .andExpect(jsonPath("$.informalUkGov").value(DEFAULT_INFORMAL_UK_GOV))
            .andExpect(jsonPath("$.wikiIpa").value(DEFAULT_WIKI_IPA));
    }
    @Test
    @Transactional
    public void getNonExistingPhoneme() throws Exception {
        // Get the phoneme
        restPhonemeMockMvc.perform(get("/api/phonemes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhoneme() throws Exception {
        // Initialize the database
        phonemeRepository.saveAndFlush(phoneme);

        int databaseSizeBeforeUpdate = phonemeRepository.findAll().size();

        // Update the phoneme
        Phoneme updatedPhoneme = phonemeRepository.findById(phoneme.getId()).get();
        // Disconnect from session so that the updates on updatedPhoneme are not directly saved in db
        em.detach(updatedPhoneme);
        updatedPhoneme
            .ipa(UPDATED_IPA)
            .aka(UPDATED_AKA)
            .informalUkGov(UPDATED_INFORMAL_UK_GOV)
            .wikiIpa(UPDATED_WIKI_IPA);

        restPhonemeMockMvc.perform(put("/api/phonemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPhoneme)))
            .andExpect(status().isOk());

        // Validate the Phoneme in the database
        List<Phoneme> phonemeList = phonemeRepository.findAll();
        assertThat(phonemeList).hasSize(databaseSizeBeforeUpdate);
        Phoneme testPhoneme = phonemeList.get(phonemeList.size() - 1);
        assertThat(testPhoneme.getIpa()).isEqualTo(UPDATED_IPA);
        assertThat(testPhoneme.getAka()).isEqualTo(UPDATED_AKA);
        assertThat(testPhoneme.getInformalUkGov()).isEqualTo(UPDATED_INFORMAL_UK_GOV);
        assertThat(testPhoneme.getWikiIpa()).isEqualTo(UPDATED_WIKI_IPA);
    }

    @Test
    @Transactional
    public void updateNonExistingPhoneme() throws Exception {
        int databaseSizeBeforeUpdate = phonemeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhonemeMockMvc.perform(put("/api/phonemes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneme)))
            .andExpect(status().isBadRequest());

        // Validate the Phoneme in the database
        List<Phoneme> phonemeList = phonemeRepository.findAll();
        assertThat(phonemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhoneme() throws Exception {
        // Initialize the database
        phonemeRepository.saveAndFlush(phoneme);

        int databaseSizeBeforeDelete = phonemeRepository.findAll().size();

        // Delete the phoneme
        restPhonemeMockMvc.perform(delete("/api/phonemes/{id}", phoneme.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Phoneme> phonemeList = phonemeRepository.findAll();
        assertThat(phonemeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
