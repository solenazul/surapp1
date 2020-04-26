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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.be4tech.surap.domain.ComentarioBlog} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.ComentarioBlogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /comentario-blogs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ComentarioBlogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter comentario;

    private InstantFilter fecha;

    private StringFilter estado;

    private LongFilter blogIdId;

    public ComentarioBlogCriteria() {
    }

    public ComentarioBlogCriteria(ComentarioBlogCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.comentario = other.comentario == null ? null : other.comentario.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.blogIdId = other.blogIdId == null ? null : other.blogIdId.copy();
    }

    @Override
    public ComentarioBlogCriteria copy() {
        return new ComentarioBlogCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getComentario() {
        return comentario;
    }

    public void setComentario(StringFilter comentario) {
        this.comentario = comentario;
    }

    public InstantFilter getFecha() {
        return fecha;
    }

    public void setFecha(InstantFilter fecha) {
        this.fecha = fecha;
    }

    public StringFilter getEstado() {
        return estado;
    }

    public void setEstado(StringFilter estado) {
        this.estado = estado;
    }

    public LongFilter getBlogIdId() {
        return blogIdId;
    }

    public void setBlogIdId(LongFilter blogIdId) {
        this.blogIdId = blogIdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ComentarioBlogCriteria that = (ComentarioBlogCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(comentario, that.comentario) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(blogIdId, that.blogIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        comentario,
        fecha,
        estado,
        blogIdId
        );
    }

    @Override
    public String toString() {
        return "ComentarioBlogCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (comentario != null ? "comentario=" + comentario + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (estado != null ? "estado=" + estado + ", " : "") +
                (blogIdId != null ? "blogIdId=" + blogIdId + ", " : "") +
            "}";
    }

}
