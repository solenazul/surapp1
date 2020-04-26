package com.be4tech.surap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A InvitacionTablero.
 */
@Entity
@Table(name = "invitacion_tablero")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InvitacionTablero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "fecha")
    private Instant fecha;

    @ManyToOne
    @JsonIgnoreProperties("invitacionTableros")
    private User idUser;

    @ManyToOne
    @JsonIgnoreProperties("invitacionTableros")
    private Tableros tableroId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEstado() {
        return estado;
    }

    public InvitacionTablero estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getFecha() {
        return fecha;
    }

    public InvitacionTablero fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public User getIdUser() {
        return idUser;
    }

    public InvitacionTablero idUser(User user) {
        this.idUser = user;
        return this;
    }

    public void setIdUser(User user) {
        this.idUser = user;
    }

    public Tableros getTableroId() {
        return tableroId;
    }

    public InvitacionTablero tableroId(Tableros tableros) {
        this.tableroId = tableros;
        return this;
    }

    public void setTableroId(Tableros tableros) {
        this.tableroId = tableros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvitacionTablero)) {
            return false;
        }
        return id != null && id.equals(((InvitacionTablero) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InvitacionTablero{" +
            "id=" + getId() +
            ", estado='" + isEstado() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
