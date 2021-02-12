package org.miholc.spelling.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.miholc.spelling.web.rest.TestUtil;

public class RuleExceptionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuleException.class);
        RuleException ruleException1 = new RuleException();
        ruleException1.setId(1L);
        RuleException ruleException2 = new RuleException();
        ruleException2.setId(ruleException1.getId());
        assertThat(ruleException1).isEqualTo(ruleException2);
        ruleException2.setId(2L);
        assertThat(ruleException1).isNotEqualTo(ruleException2);
        ruleException1.setId(null);
        assertThat(ruleException1).isNotEqualTo(ruleException2);
    }
}
