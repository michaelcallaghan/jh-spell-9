package org.miholc.spelling.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.miholc.spelling.web.rest.TestUtil;

public class GraphemeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grapheme.class);
        Grapheme grapheme1 = new Grapheme();
        grapheme1.setId(1L);
        Grapheme grapheme2 = new Grapheme();
        grapheme2.setId(grapheme1.getId());
        assertThat(grapheme1).isEqualTo(grapheme2);
        grapheme2.setId(2L);
        assertThat(grapheme1).isNotEqualTo(grapheme2);
        grapheme1.setId(null);
        assertThat(grapheme1).isNotEqualTo(grapheme2);
    }
}
