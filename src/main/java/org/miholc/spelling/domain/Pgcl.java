package org.miholc.spelling.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import org.miholc.spelling.domain.enumeration.Location;

import org.miholc.spelling.domain.enumeration.SyllabicContext;

import org.miholc.spelling.domain.enumeration.Frequency;

/**
 * A Pgcl.
 */
@Entity
@Table(name = "pgcl")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pgcl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "location")
    private Location location;

    @Enumerated(EnumType.STRING)
    @Column(name = "syllabic_context")
    private SyllabicContext syllabicContext;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency")
    private Frequency frequency;

    @ManyToOne
    @JsonIgnoreProperties(value = "pgcls", allowSetters = true)
    private Pgc pgc;

    @ManyToOne
    @JsonIgnoreProperties(value = "pgcls", allowSetters = true)
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

    public Pgcl text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Location getLocation() {
        return location;
    }

    public Pgcl location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public SyllabicContext getSyllabicContext() {
        return syllabicContext;
    }

    public Pgcl syllabicContext(SyllabicContext syllabicContext) {
        this.syllabicContext = syllabicContext;
        return this;
    }

    public void setSyllabicContext(SyllabicContext syllabicContext) {
        this.syllabicContext = syllabicContext;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public Pgcl frequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Pgc getPgc() {
        return pgc;
    }

    public Pgcl pgc(Pgc pgc) {
        this.pgc = pgc;
        return this;
    }

    public void setPgc(Pgc pgc) {
        this.pgc = pgc;
    }

    public NationalEducationalLevel getFirstIntroducedAt() {
        return firstIntroducedAt;
    }

    public Pgcl firstIntroducedAt(NationalEducationalLevel nationalEducationalLevel) {
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
        if (!(o instanceof Pgcl)) {
            return false;
        }
        return id != null && id.equals(((Pgcl) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pgcl{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", location='" + getLocation() + "'" +
            ", syllabicContext='" + getSyllabicContext() + "'" +
            ", frequency='" + getFrequency() + "'" +
            "}";
    }
}
