package com.be4tech.surap.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.be4tech.surap.domain.Blog} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.BlogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /blogs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BlogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titulo;

    private StringFilter video;

    private IntegerFilter calificacion;

    private IntegerFilter lecturas;

    private LongFilter idUserId;

    public BlogCriteria() {
    }

    public BlogCriteria(BlogCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.video = other.video == null ? null : other.video.copy();
        this.calificacion = other.calificacion == null ? null : other.calificacion.copy();
        this.lecturas = other.lecturas == null ? null : other.lecturas.copy();
        this.idUserId = other.idUserId == null ? null : other.idUserId.copy();
    }

    @Override
    public BlogCriteria copy() {
        return new BlogCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public StringFilter getVideo() {
        return video;
    }

    public void setVideo(StringFilter video) {
        this.video = video;
    }

    public IntegerFilter getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(IntegerFilter calificacion) {
        this.calificacion = calificacion;
    }

    public IntegerFilter getLecturas() {
        return lecturas;
    }

    public void setLecturas(IntegerFilter lecturas) {
        this.lecturas = lecturas;
    }

    public LongFilter getIdUserId() {
        return idUserId;
    }

    public void setIdUserId(LongFilter idUserId) {
        this.idUserId = idUserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BlogCriteria that = (BlogCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(video, that.video) &&
            Objects.equals(calificacion, that.calificacion) &&
            Objects.equals(lecturas, that.lecturas) &&
            Objects.equals(idUserId, that.idUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        titulo,
        video,
        calificacion,
        lecturas,
        idUserId
        );
    }

    @Override
    public String toString() {
        return "BlogCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (video != null ? "video=" + video + ", " : "") +
                (calificacion != null ? "calificacion=" + calificacion + ", " : "") +
                (lecturas != null ? "lecturas=" + lecturas + ", " : "") +
                (idUserId != null ? "idUserId=" + idUserId + ", " : "") +
            "}";
    }

}
