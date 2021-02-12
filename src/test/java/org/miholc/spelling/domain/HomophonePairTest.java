package org.miholc.spelling.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.miholc.spelling.web.rest.TestUtil;

public class HomophonePairTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HomophonePair.class);
        HomophonePair homophonePair1 = new HomophonePair();
        homophonePair1.setId(1L);
        HomophonePair homophonePair2 = new HomophonePair();
        homophonePair2.setId(homophonePair1.getId());
        assertThat(homophonePair1).isEqualTo(homophonePair2);
        homophonePair2.setId(2L);
        assertThat(homophonePair1).isNotEqualTo(homophonePair2);
        homophonePair1.setId(null);
        assertThat(homophonePair1).isNotEqualTo(homophonePair2);
    }
}
