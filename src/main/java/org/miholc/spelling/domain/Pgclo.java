package org.miholc.spelling.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Pgclo.
 */
@Entity
@Table(name = "pgclo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pgclo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "start")
    private Integer start;

    @Column(name = "end")
    private Integer end;

    @ManyToOne
    @JsonIgnoreProperties(value = "pgclos", allowSetters = true)
    private Pgcl pgcl;

    @ManyToOne
    @JsonIgnoreProperties(value = "pgclos", allowSetters = true)
    private Word word;

    @ManyToOne
    @JsonIgnoreProperties(value = "pgclos", allowSetters = true)
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

    public Pgclo text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getStart() {
        return start;
    }

    public Pgclo start(Integer start) {
        this.start = start;
        return this;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public Pgclo end(Integer end) {
        this.end = end;
        return this;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Pgcl getPgcl() {
        return pgcl;
    }

    public Pgclo pgcl(Pgcl pgcl) {
        this.pgcl = pgcl;
        return this;
    }

    public void setPgcl(Pgcl pgcl) {
        this.pgcl = pgcl;
    }

    public Word getWord() {
        return word;
    }

    public Pgclo word(Word word) {
        this.word = word;
        return this;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public NationalEducationalLevel getFirstIntroducedAt() {
        return firstIntroducedAt;
    }

    public Pgclo firstIntroducedAt(NationalEducationalLevel nationalEducationalLevel) {
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
        if (!(o instanceof Pgclo)) {
            return false;
        }
        return id != null && id.equals(((Pgclo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pgclo{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", start=" + getStart() +
            ", end=" + getEnd() +
            "}";
    }
}
