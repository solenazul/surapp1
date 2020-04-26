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
 * Criteria class for the {@link com.be4tech.surap.domain.ContenidoTablero} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.ContenidoTableroResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contenido-tableros?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContenidoTableroCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter comentario;

    private InstantFilter fecha;

    private LongFilter idUserId;

    private LongFilter tableroIdId;

    private LongFilter productoIdId;

    public ContenidoTableroCriteria() {
    }

    public ContenidoTableroCriteria(ContenidoTableroCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.comentario = other.comentario == null ? null : other.comentario.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.idUserId = other.idUserId == null ? null : other.idUserId.copy();
        this.tableroIdId = other.tableroIdId == null ? null : other.tableroIdId.copy();
        this.productoIdId = other.productoIdId == null ? null : other.productoIdId.copy();
    }

    @Override
    public ContenidoTableroCriteria copy() {
        return new ContenidoTableroCriteria(this);
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

    public LongFilter getIdUserId() {
        return idUserId;
    }

    public void setIdUserId(LongFilter idUserId) {
        this.idUserId = idUserId;
    }

    public LongFilter getTableroIdId() {
        return tableroIdId;
    }

    public void setTableroIdId(LongFilter tableroIdId) {
        this.tableroIdId = tableroIdId;
    }

    public LongFilter getProductoIdId() {
        return productoIdId;
    }

    public void setProductoIdId(LongFilter productoIdId) {
        this.productoIdId = productoIdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ContenidoTableroCriteria that = (ContenidoTableroCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(comentario, that.comentario) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(idUserId, that.idUserId) &&
            Objects.equals(tableroIdId, that.tableroIdId) &&
            Objects.equals(productoIdId, that.productoIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        comentario,
        fecha,
        idUserId,
        tableroIdId,
        productoIdId
        );
    }

    @Override
    public String toString() {
        return "ContenidoTableroCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (comentario != null ? "comentario=" + comentario + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (idUserId != null ? "idUserId=" + idUserId + ", " : "") +
                (tableroIdId != null ? "tableroIdId=" + tableroIdId + ", " : "") +
                (productoIdId != null ? "productoIdId=" + productoIdId + ", " : "") +
            "}";
    }

}
