package com.be4tech.surap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A ComentarioBlog.
 */
@Entity
@Table(name = "comentario_blog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ComentarioBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JsonIgnoreProperties("comentarioBlogs")
    private Blog blogId;

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

    public ComentarioBlog comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Instant getFecha() {
        return fecha;
    }

    public ComentarioBlog fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public ComentarioBlog estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Blog getBlogId() {
        return blogId;
    }

    public ComentarioBlog blogId(Blog blog) {
        this.blogId = blog;
        return this;
    }

    public void setBlogId(Blog blog) {
        this.blogId = blog;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComentarioBlog)) {
            return false;
        }
        return id != null && id.equals(((ComentarioBlog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ComentarioBlog{" +
            "id=" + getId() +
            ", comentario='" + getComentario() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
