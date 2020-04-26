package com.be4tech.surap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A EncuestaEntorno.
 */
@Entity
@Table(name = "encuesta_entorno")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EncuestaEntorno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "contacto_sintomas")
    private Boolean contactoSintomas;

    @Column(name = "viaje_internacional")
    private Boolean viajeInternacional;

    @Column(name = "viaje_nacional")
    private Boolean viajeNacional;

    @Column(name = "trabajador_salud")
    private Boolean trabajadorSalud;

    @Column(name = "ninguna")
    private Boolean ninguna;

    @Column(name = "fecha")
    private Instant fecha;

    @ManyToOne
    @JsonIgnoreProperties("encuestaEntornos")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isContactoSintomas() {
        return contactoSintomas;
    }

    public EncuestaEntorno contactoSintomas(Boolean contactoSintomas) {
        this.contactoSintomas = contactoSintomas;
        return this;
    }

    public void setContactoSintomas(Boolean contactoSintomas) {
        this.contactoSintomas = contactoSintomas;
    }

    public Boolean isViajeInternacional() {
        return viajeInternacional;
    }

    public EncuestaEntorno viajeInternacional(Boolean viajeInternacional) {
        this.viajeInternacional = viajeInternacional;
        return this;
    }

    public void setViajeInternacional(Boolean viajeInternacional) {
        this.viajeInternacional = viajeInternacional;
    }

    public Boolean isViajeNacional() {
        return viajeNacional;
    }

    public EncuestaEntorno viajeNacional(Boolean viajeNacional) {
        this.viajeNacional = viajeNacional;
        return this;
    }

    public void setViajeNacional(Boolean viajeNacional) {
        this.viajeNacional = viajeNacional;
    }

    public Boolean isTrabajadorSalud() {
        return trabajadorSalud;
    }

    public EncuestaEntorno trabajadorSalud(Boolean trabajadorSalud) {
        this.trabajadorSalud = trabajadorSalud;
        return this;
    }

    public void setTrabajadorSalud(Boolean trabajadorSalud) {
        this.trabajadorSalud = trabajadorSalud;
    }

    public Boolean isNinguna() {
        return ninguna;
    }

    public EncuestaEntorno ninguna(Boolean ninguna) {
        this.ninguna = ninguna;
        return this;
    }

    public void setNinguna(Boolean ninguna) {
        this.ninguna = ninguna;
    }

    public Instant getFecha() {
        return fecha;
    }

    public EncuestaEntorno fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public User getUser() {
        return user;
    }

    public EncuestaEntorno user(User user) {
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
        if (!(o instanceof EncuestaEntorno)) {
            return false;
        }
        return id != null && id.equals(((EncuestaEntorno) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EncuestaEntorno{" +
            "id=" + getId() +
            ", contactoSintomas='" + isContactoSintomas() + "'" +
            ", viajeInternacional='" + isViajeInternacional() + "'" +
            ", viajeNacional='" + isViajeNacional() + "'" +
            ", trabajadorSalud='" + isTrabajadorSalud() + "'" +
            ", ninguna='" + isNinguna() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
