package org.miholc.spelling.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Word.
 */
@Entity
@Table(name = "word")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Word implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "ipa_text")
    private String ipaText;

    @Column(name = "sound_file")
    private String soundFile;

    @Column(name = "usage_sound_file")
    private String usageSoundFile;

    @Column(name = "alt_ipa_text")
    private String altIpaText;

    @Column(name = "alt_sound_file")
    private String altSoundFile;

    @Column(name = "alt_usage_sound_file")
    private String altUsageSoundFile;

    @ManyToOne
    @JsonIgnoreProperties(value = "words", allowSetters = true)
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

    public Word text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIpaText() {
        return ipaText;
    }

    public Word ipaText(String ipaText) {
        this.ipaText = ipaText;
        return this;
    }

    public void setIpaText(String ipaText) {
        this.ipaText = ipaText;
    }

    public String getSoundFile() {
        return soundFile;
    }

    public Word soundFile(String soundFile) {
        this.soundFile = soundFile;
        return this;
    }

    public void setSoundFile(String soundFile) {
        this.soundFile = soundFile;
    }

    public String getUsageSoundFile() {
        return usageSoundFile;
    }

    public Word usageSoundFile(String usageSoundFile) {
        this.usageSoundFile = usageSoundFile;
        return this;
    }

    public void setUsageSoundFile(String usageSoundFile) {
        this.usageSoundFile = usageSoundFile;
    }

    public String getAltIpaText() {
        return altIpaText;
    }

    public Word altIpaText(String altIpaText) {
        this.altIpaText = altIpaText;
        return this;
    }

    public void setAltIpaText(String altIpaText) {
        this.altIpaText = altIpaText;
    }

    public String getAltSoundFile() {
        return altSoundFile;
    }

    public Word altSoundFile(String altSoundFile) {
        this.altSoundFile = altSoundFile;
        return this;
    }

    public void setAltSoundFile(String altSoundFile) {
        this.altSoundFile = altSoundFile;
    }

    public String getAltUsageSoundFile() {
        return altUsageSoundFile;
    }

    public Word altUsageSoundFile(String altUsageSoundFile) {
        this.altUsageSoundFile = altUsageSoundFile;
        return this;
    }

    public void setAltUsageSoundFile(String altUsageSoundFile) {
        this.altUsageSoundFile = altUsageSoundFile;
    }

    public NationalEducationalLevel getFirstIntroducedAt() {
        return firstIntroducedAt;
    }

    public Word firstIntroducedAt(NationalEducationalLevel nationalEducationalLevel) {
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
        if (!(o instanceof Word)) {
            return false;
        }
        return id != null && id.equals(((Word) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Word{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", ipaText='" + getIpaText() + "'" +
            ", soundFile='" + getSoundFile() + "'" +
            ", usageSoundFile='" + getUsageSoundFile() + "'" +
            ", altIpaText='" + getAltIpaText() + "'" +
            ", altSoundFile='" + getAltSoundFile() + "'" +
            ", altUsageSoundFile='" + getAltUsageSoundFile() + "'" +
            "}";
    }
}
