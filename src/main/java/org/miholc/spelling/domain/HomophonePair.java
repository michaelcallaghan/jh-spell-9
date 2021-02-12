package org.miholc.spelling.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A HomophonePair.
 */
@Entity
@Table(name = "homophone_pair")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HomophonePair implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JsonIgnoreProperties(value = "homophonePairs", allowSetters = true)
    private Word word;

    @ManyToOne
    @JsonIgnoreProperties(value = "homophonePairs", allowSetters = true)
    private Word homophone;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public HomophonePair text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Word getWord() {
        return word;
    }

    public HomophonePair word(Word word) {
        this.word = word;
        return this;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Word getHomophone() {
        return homophone;
    }

    public HomophonePair homophone(Word word) {
        this.homophone = word;
        return this;
    }

    public void setHomophone(Word word) {
        this.homophone = word;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HomophonePair)) {
            return false;
        }
        return id != null && id.equals(((HomophonePair) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HomophonePair{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            "}";
    }
}
