package com.be4tech.surap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A Fisiometria1.
 */
@Entity
@Table(name = "fisiometria_1")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fisiometria1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ritmo_cardiaco")
    private Integer ritmoCardiaco;

    @Column(name = "ritmo_respiratorio")
    private Integer ritmoRespiratorio;

    @Column(name = "oximetria")
    private Integer oximetria;

    @Column(name = "presion_arterial_sistolica")
    private Integer presionArterialSistolica;

    @Column(name = "presion_arterial_diastolica")
    private Integer presionArterialDiastolica;

    @Column(name = "temperatura")
    private Float temperatura;

    @Column(name = "time_instant")
    private Instant timeInstant;

    @ManyToOne
    @JsonIgnoreProperties("fisiometria1S")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRitmoCardiaco() {
        return ritmoCardiaco;
    }

    public Fisiometria1 ritmoCardiaco(Integer ritmoCardiaco) {
        this.ritmoCardiaco = ritmoCardiaco;
        return this;
    }

    public void setRitmoCardiaco(Integer ritmoCardiaco) {
        this.ritmoCardiaco = ritmoCardiaco;
    }

    public Integer getRitmoRespiratorio() {
        return ritmoRespiratorio;
    }

    public Fisiometria1 ritmoRespiratorio(Integer ritmoRespiratorio) {
        this.ritmoRespiratorio = ritmoRespiratorio;
        return this;
    }

    public void setRitmoRespiratorio(Integer ritmoRespiratorio) {
        this.ritmoRespiratorio = ritmoRespiratorio;
    }

    public Integer getOximetria() {
        return oximetria;
    }

    public Fisiometria1 oximetria(Integer oximetria) {
        this.oximetria = oximetria;
        return this;
    }

    public void setOximetria(Integer oximetria) {
        this.oximetria = oximetria;
    }

    public Integer getPresionArterialSistolica() {
        return presionArterialSistolica;
    }

    public Fisiometria1 presionArterialSistolica(Integer presionArterialSistolica) {
        this.presionArterialSistolica = presionArterialSistolica;
        return this;
    }

    public void setPresionArterialSistolica(Integer presionArterialSistolica) {
        this.presionArterialSistolica = presionArterialSistolica;
    }

    public Integer getPresionArterialDiastolica() {
        return presionArterialDiastolica;
    }

    public Fisiometria1 presionArterialDiastolica(Integer presionArterialDiastolica) {
        this.presionArterialDiastolica = presionArterialDiastolica;
        return this;
    }

    public void setPresionArterialDiastolica(Integer presionArterialDiastolica) {
        this.presionArterialDiastolica = presionArterialDiastolica;
    }

    public Float getTemperatura() {
        return temperatura;
    }

    public Fisiometria1 temperatura(Float temperatura) {
        this.temperatura = temperatura;
        return this;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public Instant getTimeInstant() {
        return timeInstant;
    }

    public Fisiometria1 timeInstant(Instant timeInstant) {
        this.timeInstant = timeInstant;
        return this;
    }

    public void setTimeInstant(Instant timeInstant) {
        this.timeInstant = timeInstant;
    }

    public User getUser() {
        return user;
    }

    public Fisiometria1 user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fisiometria1)) {
            return false;
        }
        return id != null && id.equals(((Fisiometria1) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Fisiometria1{" +
            "id=" + getId() +
            ", ritmoCardiaco=" + getRitmoCardiaco() +
            ", ritmoRespiratorio=" + getRitmoRespiratorio() +
            ", oximetria=" + getOximetria() +
            ", presionArterialSistolica=" + getPresionArterialSistolica() +
            ", presionArterialDiastolica=" + getPresionArterialDiastolica() +
            ", temperatura=" + getTemperatura() +
            ", timeInstant='" + getTimeInstant() + "'" +
            "}";
    }
}
