package org.miholc.spelling.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.miholc.spelling.web.rest.TestUtil;

public class WordListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WordList.class);
        WordList wordList1 = new WordList();
        wordList1.setId(1L);
        WordList wordList2 = new WordList();
        wordList2.setId(wordList1.getId());
        assertThat(wordList1).isEqualTo(wordList2);
        wordList2.setId(2L);
        assertThat(wordList1).isNotEqualTo(wordList2);
        wordList1.setId(null);
        assertThat(wordList1).isNotEqualTo(wordList2);
    }
}
