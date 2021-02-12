package org.miholc.spelling.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RuleException.
 */
@Entity
@Table(name = "rule_exception")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RuleException implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "info")
    private String info;

    @ManyToOne
    @JsonIgnoreProperties(value = "ruleExceptions", allowSetters = true)
    private Rule rule;

    @ManyToOne
    @JsonIgnoreProperties(value = "ruleExceptions", allowSetters = true)
    private Pgclo pgclo;

    @ManyToOne
    @JsonIgnoreProperties(value = "ruleExceptions", allowSetters = true)
    private NationalEducationalLevel firstIntroducedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public RuleException info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Rule getRule() {
        return rule;
    }

    public RuleException rule(Rule rule) {
        this.rule = rule;
        return this;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public Pgclo getPgclo() {
        return pgclo;
    }

    public RuleException pgclo(Pgclo pgclo) {
        this.pgclo = pgclo;
        return this;
    }

    public void setPgclo(Pgclo pgclo) {
        this.pgclo = pgclo;
    }

    public NationalEducationalLevel getFirstIntroducedAt() {
        return firstIntroducedAt;
    }

    public RuleException firstIntroducedAt(NationalEducationalLevel nationalEducationalLevel) {
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
        if (!(o instanceof RuleException)) {
            return false;
        }
        return id != null && id.equals(((RuleException) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RuleException{" +
            "id=" + getId() +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
