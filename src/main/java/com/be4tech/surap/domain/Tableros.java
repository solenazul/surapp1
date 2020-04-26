package com.be4tech.surap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A Tableros.
 */
@Entity
@Table(name = "tableros")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tableros implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "privado")
    private Boolean privado;

    @Column(name = "calificacion")
    private Integer calificacion;

    @ManyToOne
    @JsonIgnoreProperties("tableros")
    private User idUser;

    @ManyToOne
    @JsonIgnoreProperties("tableros")
    private CategoriaTablero categoriaId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Tableros nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Tableros fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Boolean isPrivado() {
        return privado;
    }

    public Tableros privado(Boolean privado) {
        this.privado = privado;
        return this;
    }

    public void setPrivado(Boolean privado) {
        this.privado = privado;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public Tableros calificacion(Integer calificacion) {
        this.calificacion = calificacion;
        return this;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public User getIdUser() {
        return idUser;
    }

    public Tableros idUser(User user) {
        this.idUser = user;
        return this;
    }

    public void setIdUser(User user) {
        this.idUser = user;
    }

    public CategoriaTablero getCategoriaId() {
        return categoriaId;
    }

    public Tableros categoriaId(CategoriaTablero categoriaTablero) {
        this.categoriaId = categoriaTablero;
        return this;
    }

    public void setCategoriaId(CategoriaTablero categoriaTablero) {
        this.categoriaId = categoriaTablero;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tableros)) {
            return false;
        }
        return id != null && id.equals(((Tableros) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tableros{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", privado='" + isPrivado() + "'" +
            ", calificacion=" + getCalificacion() +
            "}";
    }
}
