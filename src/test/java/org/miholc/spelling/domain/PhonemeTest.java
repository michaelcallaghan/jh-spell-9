package org.miholc.spelling.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.miholc.spelling.web.rest.TestUtil;

public class PhonemeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Phoneme.class);
        Phoneme phoneme1 = new Phoneme();
        phoneme1.setId(1L);
        Phoneme phoneme2 = new Phoneme();
        phoneme2.setId(phoneme1.getId());
        assertThat(phoneme1).isEqualTo(phoneme2);
        phoneme2.setId(2L);
        assertThat(phoneme1).isNotEqualTo(phoneme2);
        phoneme1.setId(null);
        assertThat(phoneme1).isNotEqualTo(phoneme2);
    }
}
