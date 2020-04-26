package com.be4tech.surap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A ContenidoTablero.
 */
@Entity
@Table(name = "contenido_tablero")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContenidoTablero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "fecha")
    private Instant fecha;

    @ManyToOne
    @JsonIgnoreProperties("contenidoTableros")
    private User idUser;

    @ManyToOne
    @JsonIgnoreProperties("contenidoTableros")
    private Tableros tableroId;

    @ManyToOne
    @JsonIgnoreProperties("contenidoTableros")
    private Productos productoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public ContenidoTablero comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Instant getFecha() {
        return fecha;
    }

    public ContenidoTablero fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public User getIdUser() {
        return idUser;
    }

    public ContenidoTablero idUser(User user) {
        this.idUser = user;
        return this;
    }

    public void setIdUser(User user) {
        this.idUser = user;
    }

    public Tableros getTableroId() {
        return tableroId;
    }

    public ContenidoTablero tableroId(Tableros tableros) {
        this.tableroId = tableros;
        return this;
    }

    public void setTableroId(Tableros tableros) {
        this.tableroId = tableros;
    }

    public Productos getProductoId() {
        return productoId;
    }

    public ContenidoTablero productoId(Productos productos) {
        this.productoId = productos;
        return this;
    }

    public void setProductoId(Productos productos) {
        this.productoId = productos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContenidoTablero)) {
            return false;
        }
        return id != null && id.equals(((ContenidoTablero) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContenidoTablero{" +
            "id=" + getId() +
            ", comentario='" + getComentario() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
