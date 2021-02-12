package org.miholc.spelling.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A WordInList.
 */
@Entity
@Table(name = "word_in_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WordInList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position")
    private Integer position;

    @ManyToOne
    @JsonIgnoreProperties(value = "wordInLists", allowSetters = true)
    private Word word;

    @ManyToOne
    @JsonIgnoreProperties(value = "wordInLists", allowSetters = true)
    private WordList wordList;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public WordInList position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Word getWord() {
        return word;
    }

    public WordInList word(Word word) {
        this.word = word;
        return this;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public WordList getWordList() {
        return wordList;
    }

    public WordInList wordList(WordList wordList) {
        this.wordList = wordList;
        return this;
    }

    public void setWordList(WordList wordList) {
        this.wordList = wordList;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WordInList)) {
            return false;
        }
        return id != null && id.equals(((WordInList) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WordInList{" +
            "id=" + getId() +
            ", position=" + getPosition() +
            "}";
    }
}
