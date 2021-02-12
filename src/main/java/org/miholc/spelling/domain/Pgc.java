package org.miholc.spelling.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import org.miholc.spelling.domain.enumeration.Frequency;

/**
 * A Pgc.
 */
@Entity
@Table(name = "pgc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pgc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency")
    private Frequency frequency;

    @ManyToOne
    @JsonIgnoreProperties(value = "pgcs", allowSetters = true)
    private Phoneme phoneme;

    @ManyToOne
    @JsonIgnoreProperties(value = "pgcs", allowSetters = true)
    private Grapheme grapheme;

    @ManyToOne
    @JsonIgnoreProperties(value = "pgcs", allowSetters = true)
    private NationalEducationalLevel firstIntroducedAt;

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

    public Pgc text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public Pgc frequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Phoneme getPhoneme() {
        return phoneme;
    }

    public Pgc phoneme(Phoneme phoneme) {
        this.phoneme = phoneme;
        return this;
    }

    public void setPhoneme(Phoneme phoneme) {
        this.phoneme = phoneme;
    }

    public Grapheme getGrapheme() {
        return grapheme;
    }

    public Pgc grapheme(Grapheme grapheme) {
        this.grapheme = grapheme;
        return this;
    }

    public void setGrapheme(Grapheme grapheme) {
        this.grapheme = grapheme;
    }

    public NationalEducationalLevel getFirstIntroducedAt() {
        return firstIntroducedAt;
    }

    public Pgc firstIntroducedAt(NationalEducationalLevel nationalEducationalLevel) {
        this.firstIntroducedAt = nationalEducationalLevel;
        return this;
    }

    public void setFirstIntroducedAt(NationalEducationalLevel nationalEducationalLevel) {
        this.firstIntroducedAt = nationalEducationalLevel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pgc)) {
            return false;
        }
        return id != null && id.equals(((Pgc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pgc{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", frequency='" + getFrequency() + "'" +
            "}";
    }
}
