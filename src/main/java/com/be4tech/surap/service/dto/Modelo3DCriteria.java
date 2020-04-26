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
 * Criteria class for the {@link com.be4tech.surap.domain.Modelo3D} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.Modelo3DResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /modelo-3-ds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class Modelo3DCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter urt;

    private StringFilter colider;

    private StringFilter textureA;

    private StringFilter textureBC;

    private StringFilter textureN;

    private StringFilter textureR;

    private StringFilter shadow;

    private BooleanFilter acutalizado;

    private LocalDateFilter fecha;

    private LongFilter idUserId;

    public Modelo3DCriteria() {
    }

    public Modelo3DCriteria(Modelo3DCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.urt = other.urt == null ? null : other.urt.copy();
        this.colider = other.colider == null ? null : other.colider.copy();
        this.textureA = other.textureA == null ? null : other.textureA.copy();
        this.textureBC = other.textureBC == null ? null : other.textureBC.copy();
        this.textureN = other.textureN == null ? null : other.textureN.copy();
        this.textureR = other.textureR == null ? null : other.textureR.copy();
        this.shadow = other.shadow == null ? null : other.shadow.copy();
        this.acutalizado = other.acutalizado == null ? null : other.acutalizado.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.idUserId = other.idUserId == null ? null : other.idUserId.copy();
    }

    @Override
    public Modelo3DCriteria copy() {
        return new Modelo3DCriteria(this);
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

    public StringFilter getUrt() {
        return urt;
    }

    public void setUrt(StringFilter urt) {
        this.urt = urt;
    }

    public StringFilter getColider() {
        return colider;
    }

    public void setColider(StringFilter colider) {
        this.colider = colider;
    }

    public StringFilter getTextureA() {
        return textureA;
    }

    public void setTextureA(StringFilter textureA) {
        this.textureA = textureA;
    }

    public StringFilter getTextureBC() {
        return textureBC;
    }

    public void setTextureBC(StringFilter textureBC) {
        this.textureBC = textureBC;
    }

    public StringFilter getTextureN() {
        return textureN;
    }

    public void setTextureN(StringFilter textureN) {
        this.textureN = textureN;
    }

    public StringFilter getTextureR() {
        return textureR;
    }

    public void setTextureR(StringFilter textureR) {
        this.textureR = textureR;
    }

    public StringFilter getShadow() {
        return shadow;
    }

    public void setShadow(StringFilter shadow) {
        this.shadow = shadow;
    }

    public BooleanFilter getAcutalizado() {
        return acutalizado;
    }

    public void setAcutalizado(BooleanFilter acutalizado) {
        this.acutalizado = acutalizado;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Modelo3DCriteria that = (Modelo3DCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(urt, that.urt) &&
            Objects.equals(colider, that.colider) &&
            Objects.equals(textureA, that.textureA) &&
            Objects.equals(textureBC, that.textureBC) &&
            Objects.equals(textureN, that.textureN) &&
            Objects.equals(textureR, that.textureR) &&
            Objects.equals(shadow, that.shadow) &&
            Objects.equals(acutalizado, that.acutalizado) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(idUserId, that.idUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        urt,
        colider,
        textureA,
        textureBC,
        textureN,
        textureR,
        shadow,
        acutalizado,
        fecha,
        idUserId
        );
    }

    @Override
    public String toString() {
        return "Modelo3DCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (urt != null ? "urt=" + urt + ", " : "") +
                (colider != null ? "colider=" + colider + ", " : "") +
                (textureA != null ? "textureA=" + textureA + ", " : "") +
                (textureBC != null ? "textureBC=" + textureBC + ", " : "") +
                (textureN != null ? "textureN=" + textureN + ", " : "") +
                (textureR != null ? "textureR=" + textureR + ", " : "") +
                (shadow != null ? "shadow=" + shadow + ", " : "") +
                (acutalizado != null ? "acutalizado=" + acutalizado + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (idUserId != null ? "idUserId=" + idUserId + ", " : "") +
            "}";
    }

}
