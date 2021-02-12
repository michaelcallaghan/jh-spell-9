package org.miholc.spelling.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.miholc.spelling.web.rest.TestUtil;

public class PgcTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pgc.class);
        Pgc pgc1 = new Pgc();
        pgc1.setId(1L);
        Pgc pgc2 = new Pgc();
        pgc2.setId(pgc1.getId());
        assertThat(pgc1).isEqualTo(pgc2);
        pgc2.setId(2L);
        assertThat(pgc1).isNotEqualTo(pgc2);
        pgc1.setId(null);
        assertThat(pgc1).isNotEqualTo(pgc2);
    }
}
