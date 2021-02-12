package org.miholc.spelling.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Grapheme.
 */
@Entity
@Table(name = "grapheme")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Grapheme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne
    @JsonIgnoreProperties(value = "graphemes", allowSetters = true)
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

    public Grapheme text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NationalEducationalLevel getFirstIntroducedAt() {
        return firstIntroducedAt;
    }

    public Grapheme firstIntroducedAt(NationalEducationalLevel nationalEducationalLevel) {
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
        if (!(o instanceof Grapheme)) {
            return false;
        }
        return id != null && id.equals(((Grapheme) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Grapheme{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            "}";
    }
}
