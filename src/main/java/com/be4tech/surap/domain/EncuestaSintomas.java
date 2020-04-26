package com.be4tech.surap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A EncuestaSintomas.
 */
@Entity
@Table(name = "encuesta_sintomas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EncuestaSintomas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fiebre")
    private Boolean fiebre;

    @Column(name = "dolor_garganta")
    private Boolean dolorGarganta;

    @Column(name = "congestion_nasal")
    private Boolean congestionNasal;

    @Column(name = "tos")
    private Boolean tos;

    @Column(name = "dificultad_respirar")
    private Boolean dificultadRespirar;

    @Column(name = "fatiga")
    private Boolean fatiga;

    @Column(name = "escalofrio")
    private Boolean escalofrio;

    @Column(name = "dolor_muscular")
    private Boolean dolorMuscular;

    @Column(name = "ninguno")
    private Boolean ninguno;

    @Column(name = "fecha")
    private Instant fecha;

    @ManyToOne
    @JsonIgnoreProperties("encuestaSintomas")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isFiebre() {
        return fiebre;
    }

    public EncuestaSintomas fiebre(Boolean fiebre) {
        this.fiebre = fiebre;
        return this;
    }

    public void setFiebre(Boolean fiebre) {
        this.fiebre = fiebre;
    }

    public Boolean isDolorGarganta() {
        return dolorGarganta;
    }

    public EncuestaSintomas dolorGarganta(Boolean dolorGarganta) {
        this.dolorGarganta = dolorGarganta;
        return this;
    }

    public void setDolorGarganta(Boolean dolorGarganta) {
        this.dolorGarganta = dolorGarganta;
    }

    public Boolean isCongestionNasal() {
        return congestionNasal;
    }

    public EncuestaSintomas congestionNasal(Boolean congestionNasal) {
        this.congestionNasal = congestionNasal;
        return this;
    }

    public void setCongestionNasal(Boolean congestionNasal) {
        this.congestionNasal = congestionNasal;
    }

    public Boolean isTos() {
        return tos;
    }

    public EncuestaSintomas tos(Boolean tos) {
        this.tos = tos;
        return this;
    }

    public void setTos(Boolean tos) {
        this.tos = tos;
    }

    public Boolean isDificultadRespirar() {
        return dificultadRespirar;
    }

    public EncuestaSintomas dificultadRespirar(Boolean dificultadRespirar) {
        this.dificultadRespirar = dificultadRespirar;
        return this;
    }

    public void setDificultadRespirar(Boolean dificultadRespirar) {
        this.dificultadRespirar = dificultadRespirar;
    }

    public Boolean isFatiga() {
        return fatiga;
    }

    public EncuestaSintomas fatiga(Boolean fatiga) {
        this.fatiga = fatiga;
        return this;
    }

    public void setFatiga(Boolean fatiga) {
        this.fatiga = fatiga;
    }

    public Boolean isEscalofrio() {
        return escalofrio;
    }

    public EncuestaSintomas escalofrio(Boolean escalofrio) {
        this.escalofrio = escalofrio;
        return this;
    }

    public void setEscalofrio(Boolean escalofrio) {
        this.escalofrio = escalofrio;
    }

    public Boolean isDolorMuscular() {
        return dolorMuscular;
    }

    public EncuestaSintomas dolorMuscular(Boolean dolorMuscular) {
        this.dolorMuscular = dolorMuscular;
        return this;
    }

    public void setDolorMuscular(Boolean dolorMuscular) {
        this.dolorMuscular = dolorMuscular;
    }

    public Boolean isNinguno() {
        return ninguno;
    }

    public EncuestaSintomas ninguno(Boolean ninguno) {
        this.ninguno = ninguno;
        return this;
    }

    public void setNinguno(Boolean ninguno) {
        this.ninguno = ninguno;
    }

    public Instant getFecha() {
        return fecha;
    }

    public EncuestaSintomas fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public User getUser() {
        return user;
    }

    public EncuestaSintomas user(User user) {
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
        if (!(o instanceof EncuestaSintomas)) {
            return false;
        }
        return id != null && id.equals(((EncuestaSintomas) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EncuestaSintomas{" +
            "id=" + getId() +
            ", fiebre='" + isFiebre() + "'" +
            ", dolorGarganta='" + isDolorGarganta() + "'" +
            ", congestionNasal='" + isCongestionNasal() + "'" +
            ", tos='" + isTos() + "'" +
            ", dificultadRespirar='" + isDificultadRespirar() + "'" +
            ", fatiga='" + isFatiga() + "'" +
            ", escalofrio='" + isEscalofrio() + "'" +
            ", dolorMuscular='" + isDolorMuscular() + "'" +
            ", ninguno='" + isNinguno() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
