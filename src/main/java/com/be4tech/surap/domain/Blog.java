package com.be4tech.surap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Blog.
 */
@Entity
@Table(name = "blog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "contenido")
    private String contenido;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Column(name = "video")
    private String video;

    @Column(name = "calificacion")
    private Integer calificacion;

    @Column(name = "lecturas")
    private Integer lecturas;

    @ManyToOne
    @JsonIgnoreProperties("blogs")
    private User idUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Blog titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public Blog contenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Blog imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Blog imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public String getVideo() {
        return video;
    }

    public Blog video(String video) {
        this.video = video;
        return this;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public Blog calificacion(Integer calificacion) {
        this.calificacion = calificacion;
        return this;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public Integer getLecturas() {
        return lecturas;
    }

    public Blog lecturas(Integer lecturas) {
        this.lecturas = lecturas;
        return this;
    }

    public void setLecturas(Integer lecturas) {
        this.lecturas = lecturas;
    }

    public User getIdUser() {
        return idUser;
    }

    public Blog idUser(User user) {
        this.idUser = user;
        return this;
    }

    public void setIdUser(User user) {
        this.idUser = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Blog)) {
            return false;
        }
        return id != null && id.equals(((Blog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Blog{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            ", video='" + getVideo() + "'" +
            ", calificacion=" + getCalificacion() +
            ", lecturas=" + getLecturas() +
            "}";
    }
}
