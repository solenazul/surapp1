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
 * Criteria class for the {@link com.be4tech.surap.domain.CategoriaProducto} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.CategoriaProductoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categoria-productos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoriaProductoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter categoria;

    private InstantFilter fecha;

    private LongFilter catagoriaIdId;

    private LongFilter productoIdId;

    public CategoriaProductoCriteria() {
    }

    public CategoriaProductoCriteria(CategoriaProductoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.categoria = other.categoria == null ? null : other.categoria.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.catagoriaIdId = other.catagoriaIdId == null ? null : other.catagoriaIdId.copy();
        this.productoIdId = other.productoIdId == null ? null : other.productoIdId.copy();
    }

    @Override
    public CategoriaProductoCriteria copy() {
        return new CategoriaProductoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCategoria() {
        return categoria;
    }

    public void setCategoria(StringFilter categoria) {
        this.categoria = categoria;
    }

    public InstantFilter getFecha() {
        return fecha;
    }

    public void setFecha(InstantFilter fecha) {
        this.fecha = fecha;
    }

    public LongFilter getCatagoriaIdId() {
        return catagoriaIdId;
    }

    public void setCatagoriaIdId(LongFilter catagoriaIdId) {
        this.catagoriaIdId = catagoriaIdId;
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
        final CategoriaProductoCriteria that = (CategoriaProductoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(categoria, that.categoria) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(catagoriaIdId, that.catagoriaIdId) &&
            Objects.equals(productoIdId, that.productoIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        categoria,
        fecha,
        catagoriaIdId,
        productoIdId
        );
    }

    @Override
    public String toString() {
        return "CategoriaProductoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (categoria != null ? "categoria=" + categoria + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (catagoriaIdId != null ? "catagoriaIdId=" + catagoriaIdId + ", " : "") +
                (productoIdId != null ? "productoIdId=" + productoIdId + ", " : "") +
            "}";
    }

}
