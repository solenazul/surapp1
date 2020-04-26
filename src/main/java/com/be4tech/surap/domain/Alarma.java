package com.be4tech.surap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A Alarma.
 */
@Entity
@Table(name = "alarma")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Alarma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "time_instant")
    private Instant timeInstant;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "procedimiento")
    private String procedimiento;

    @ManyToOne
    @JsonIgnoreProperties("alarmas")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTimeInstant() {
        return timeInstant;
    }

    public Alarma timeInstant(Instant timeInstant) {
        this.timeInstant = timeInstant;
        return this;
    }

    public void setTimeInstant(Instant timeInstant) {
        this.timeInstant = timeInstant;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Alarma descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public Alarma procedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
        return this;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public User getUser() {
        return user;
    }

    public Alarma user(User user) {
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
        if (!(o instanceof Alarma)) {
            return false;
        }
        return id != null && id.equals(((Alarma) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Alarma{" +
            "id=" + getId() +
            ", timeInstant='" + getTimeInstant() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", procedimiento='" + getProcedimiento() + "'" +
            "}";
    }
}
