package org.miholc.spelling.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.miholc.spelling.web.rest.TestUtil;

public class WordInListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WordInList.class);
        WordInList wordInList1 = new WordInList();
        wordInList1.setId(1L);
        WordInList wordInList2 = new WordInList();
        wordInList2.setId(wordInList1.getId());
        assertThat(wordInList1).isEqualTo(wordInList2);
        wordInList2.setId(2L);
        assertThat(wordInList1).isNotEqualTo(wordInList2);
        wordInList1.setId(null);
        assertThat(wordInList1).isNotEqualTo(wordInList2);
    }
}
