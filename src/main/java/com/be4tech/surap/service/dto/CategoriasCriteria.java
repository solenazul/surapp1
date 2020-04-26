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
 * Criteria class for the {@link com.be4tech.surap.domain.Categorias} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.CategoriasResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categorias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoriasCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter categoria;

    private InstantFilter fecha;

    public CategoriasCriteria() {
    }

    public CategoriasCriteria(CategoriasCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.categoria = other.categoria == null ? null : other.categoria.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
    }

    @Override
    public CategoriasCriteria copy() {
        return new CategoriasCriteria(this);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategoriasCriteria that = (CategoriasCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(categoria, that.categoria) &&
            Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        categoria,
        fecha
        );
    }

    @Override
    public String toString() {
        return "CategoriasCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (categoria != null ? "categoria=" + categoria + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
            "}";
    }

}
