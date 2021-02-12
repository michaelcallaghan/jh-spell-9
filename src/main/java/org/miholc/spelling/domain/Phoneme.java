package org.miholc.spelling.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Phoneme.
 */
@Entity
@Table(name = "phoneme")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Phoneme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ipa", nullable = false)
    private String ipa;

    @Column(name = "aka")
    private String aka;

    @Column(name = "informal_uk_gov")
    private String informalUkGov;

    @Column(name = "wiki_ipa")
    private String wikiIpa;

    @ManyToOne
    @JsonIgnoreProperties(value = "phonemes", allowSetters = true)
    private NationalEducationalLevel firstIntroducedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpa() {
        return ipa;
    }

    public Phoneme ipa(String ipa) {
        this.ipa = ipa;
        return this;
    }

    public void setIpa(String ipa) {
        this.ipa = ipa;
    }

    public String getAka() {
        return aka;
    }

    public Phoneme aka(String aka) {
        this.aka = aka;
        return this;
    }

    public void setAka(String aka) {
        this.aka = aka;
    }

    public String getInformalUkGov() {
        return informalUkGov;
    }

    public Phoneme informalUkGov(String informalUkGov) {
        this.informalUkGov = informalUkGov;
        return this;
    }

    public void setInformalUkGov(String informalUkGov) {
        this.informalUkGov = informalUkGov;
    }

    public String getWikiIpa() {
        return wikiIpa;
    }

    public Phoneme wikiIpa(String wikiIpa) {
        this.wikiIpa = wikiIpa;
        return this;
    }

    public void setWikiIpa(String wikiIpa) {
        this.wikiIpa = wikiIpa;
    }

    public NationalEducationalLevel getFirstIntroducedAt() {
        return firstIntroducedAt;
    }

    public Phoneme firstIntroducedAt(NationalEducationalLevel nationalEducationalLevel) {
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
        if (!(o instanceof Phoneme)) {
            return false;
        }
        return id != null && id.equals(((Phoneme) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Phoneme{" +
            "id=" + getId() +
            ", ipa='" + getIpa() + "'" +
            ", aka='" + getAka() + "'" +
            ", informalUkGov='" + getInformalUkGov() + "'" +
            ", wikiIpa='" + getWikiIpa() + "'" +
            "}";
    }
}
