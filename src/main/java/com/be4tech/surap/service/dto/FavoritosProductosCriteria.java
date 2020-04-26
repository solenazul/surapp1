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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.be4tech.surap.domain.FavoritosProductos} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.FavoritosProductosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /favoritos-productos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FavoritosProductosCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter fecha;

    private LongFilter idUserId;

    private LongFilter productoIdId;

    public FavoritosProductosCriteria() {
    }

    public FavoritosProductosCriteria(FavoritosProductosCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.idUserId = other.idUserId == null ? null : other.idUserId.copy();
        this.productoIdId = other.productoIdId == null ? null : other.productoIdId.copy();
    }

    @Override
    public FavoritosProductosCriteria copy() {
        return new FavoritosProductosCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateFilter fecha) {
        this.fecha = fecha;
    }

    public LongFilter getIdUserId() {
        return idUserId;
    }

    public void setIdUserId(LongFilter idUserId) {
        this.idUserId = idUserId;
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
        final FavoritosProductosCriteria that = (FavoritosProductosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(idUserId, that.idUserId) &&
            Objects.equals(productoIdId, that.productoIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fecha,
        idUserId,
        productoIdId
        );
    }

    @Override
    public String toString() {
        return "FavoritosProductosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (idUserId != null ? "idUserId=" + idUserId + ", " : "") +
                (productoIdId != null ? "productoIdId=" + productoIdId + ", " : "") +
            "}";
    }

}
