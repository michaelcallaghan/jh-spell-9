package org.miholc.spelling.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.miholc.spelling.web.rest.TestUtil;

public class PgclTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pgcl.class);
        Pgcl pgcl1 = new Pgcl();
        pgcl1.setId(1L);
        Pgcl pgcl2 = new Pgcl();
        pgcl2.setId(pgcl1.getId());
        assertThat(pgcl1).isEqualTo(pgcl2);
        pgcl2.setId(2L);
        assertThat(pgcl1).isNotEqualTo(pgcl2);
        pgcl1.setId(null);
        assertThat(pgcl1).isNotEqualTo(pgcl2);
    }
}
