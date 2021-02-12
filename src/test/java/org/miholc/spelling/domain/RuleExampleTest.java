package org.miholc.spelling.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.miholc.spelling.web.rest.TestUtil;

public class RuleExampleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuleExample.class);
        RuleExample ruleExample1 = new RuleExample();
        ruleExample1.setId(1L);
        RuleExample ruleExample2 = new RuleExample();
        ruleExample2.setId(ruleExample1.getId());
        assertThat(ruleExample1).isEqualTo(ruleExample2);
        ruleExample2.setId(2L);
        assertThat(ruleExample1).isNotEqualTo(ruleExample2);
        ruleExample1.setId(null);
        assertThat(ruleExample1).isNotEqualTo(ruleExample2);
    }
}
