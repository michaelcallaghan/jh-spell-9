package org.miholc.spelling.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.miholc.spelling.web.rest.TestUtil;

public class PgcloTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pgclo.class);
        Pgclo pgclo1 = new Pgclo();
        pgclo1.setId(1L);
        Pgclo pgclo2 = new Pgclo();
        pgclo2.setId(pgclo1.getId());
        assertThat(pgclo1).isEqualTo(pgclo2);
        pgclo2.setId(2L);
        assertThat(pgclo1).isNotEqualTo(pgclo2);
        pgclo1.setId(null);
        assertThat(pgclo1).isNotEqualTo(pgclo2);
    }
}
