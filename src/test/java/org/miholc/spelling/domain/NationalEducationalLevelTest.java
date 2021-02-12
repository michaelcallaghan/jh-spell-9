package org.miholc.spelling.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.miholc.spelling.web.rest.TestUtil;

public class NationalEducationalLevelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NationalEducationalLevel.class);
        NationalEducationalLevel nationalEducationalLevel1 = new NationalEducationalLevel();
        nationalEducationalLevel1.setId(1L);
        NationalEducationalLevel nationalEducationalLevel2 = new NationalEducationalLevel();
        nationalEducationalLevel2.setId(nationalEducationalLevel1.getId());
        assertThat(nationalEducationalLevel1).isEqualTo(nationalEducationalLevel2);
        nationalEducationalLevel2.setId(2L);
        assertThat(nationalEducationalLevel1).isNotEqualTo(nationalEducationalLevel2);
        nationalEducationalLevel1.setId(null);
        assertThat(nationalEducationalLevel1).isNotEqualTo(nationalEducationalLevel2);
    }
}
