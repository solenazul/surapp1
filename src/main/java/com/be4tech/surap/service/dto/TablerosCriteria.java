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
 * Criteria class for the {@link com.be4tech.surap.domain.Tableros} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.TablerosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tableros?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TablerosCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private InstantFilter fecha;

    private BooleanFilter privado;

    private IntegerFilter calificacion;

    private LongFilter idUserId;

    private LongFilter categoriaIdId;

    public TablerosCriteria() {
    }

    public TablerosCriteria(TablerosCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.privado = other.privado == null ? null : other.privado.copy();
        this.calificacion = other.calificacion == null ? null : other.calificacion.copy();
        this.idUserId = other.idUserId == null ? null : other.idUserId.copy();
        this.categoriaIdId = other.categoriaIdId == null ? null : other.categoriaIdId.copy();
    }

    @Override
    public TablerosCriteria copy() {
        return new TablerosCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public InstantFilter getFecha() {
        return fecha;
    }

    public void setFecha(InstantFilter fecha) {
        this.fecha = fecha;
    }

    public BooleanFilter getPrivado() {
        return privado;
    }

    public void setPrivado(BooleanFilter privado) {
        this.privado = privado;
    }

    public IntegerFilter getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(IntegerFilter calificacion) {
        this.calificacion = calificacion;
    }

    public LongFilter getIdUserId() {
        return idUserId;
    }

    public void setIdUserId(LongFilter idUserId) {
        this.idUserId = idUserId;
    }

    public LongFilter getCategoriaIdId() {
        return categoriaIdId;
    }

    public void setCategoriaIdId(LongFilter categoriaIdId) {
        this.categoriaIdId = categoriaIdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TablerosCriteria that = (TablerosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(privado, that.privado) &&
            Objects.equals(calificacion, that.calificacion) &&
            Objects.equals(idUserId, that.idUserId) &&
            Objects.equals(categoriaIdId, that.categoriaIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        fecha,
        privado,
        calificacion,
        idUserId,
        categoriaIdId
        );
    }

    @Override
    public String toString() {
        return "TablerosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (privado != null ? "privado=" + privado + ", " : "") +
                (calificacion != null ? "calificacion=" + calificacion + ", " : "") +
                (idUserId != null ? "idUserId=" + idUserId + ", " : "") +
                (categoriaIdId != null ? "categoriaIdId=" + categoriaIdId + ", " : "") +
            "}";
    }

}
