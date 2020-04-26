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
 * Criteria class for the {@link com.be4tech.surap.domain.EncuestaEntorno} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.EncuestaEntornoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /encuesta-entornos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EncuestaEntornoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter contactoSintomas;

    private BooleanFilter viajeInternacional;

    private BooleanFilter viajeNacional;

    private BooleanFilter trabajadorSalud;

    private BooleanFilter ninguna;

    private InstantFilter fecha;

    private LongFilter userId;

    public EncuestaEntornoCriteria() {
    }

    public EncuestaEntornoCriteria(EncuestaEntornoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.contactoSintomas = other.contactoSintomas == null ? null : other.contactoSintomas.copy();
        this.viajeInternacional = other.viajeInternacional == null ? null : other.viajeInternacional.copy();
        this.viajeNacional = other.viajeNacional == null ? null : other.viajeNacional.copy();
        this.trabajadorSalud = other.trabajadorSalud == null ? null : other.trabajadorSalud.copy();
        this.ninguna = other.ninguna == null ? null : other.ninguna.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public EncuestaEntornoCriteria copy() {
        return new EncuestaEntornoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getContactoSintomas() {
        return contactoSintomas;
    }

    public void setContactoSintomas(BooleanFilter contactoSintomas) {
        this.contactoSintomas = contactoSintomas;
    }

    public BooleanFilter getViajeInternacional() {
        return viajeInternacional;
    }

    public void setViajeInternacional(BooleanFilter viajeInternacional) {
        this.viajeInternacional = viajeInternacional;
    }

    public BooleanFilter getViajeNacional() {
        return viajeNacional;
    }

    public void setViajeNacional(BooleanFilter viajeNacional) {
        this.viajeNacional = viajeNacional;
    }

    public BooleanFilter getTrabajadorSalud() {
        return trabajadorSalud;
    }

    public void setTrabajadorSalud(BooleanFilter trabajadorSalud) {
        this.trabajadorSalud = trabajadorSalud;
    }

    public BooleanFilter getNinguna() {
        return ninguna;
    }

    public void setNinguna(BooleanFilter ninguna) {
        this.ninguna = ninguna;
    }

    public InstantFilter getFecha() {
        return fecha;
    }

    public void setFecha(InstantFilter fecha) {
        this.fecha = fecha;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EncuestaEntornoCriteria that = (EncuestaEntornoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(contactoSintomas, that.contactoSintomas) &&
            Objects.equals(viajeInternacional, that.viajeInternacional) &&
            Objects.equals(viajeNacional, that.viajeNacional) &&
            Objects.equals(trabajadorSalud, that.trabajadorSalud) &&
            Objects.equals(ninguna, that.ninguna) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        contactoSintomas,
        viajeInternacional,
        viajeNacional,
        trabajadorSalud,
        ninguna,
        fecha,
        userId
        );
    }

    @Override
    public String toString() {
        return "EncuestaEntornoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (contactoSintomas != null ? "contactoSintomas=" + contactoSintomas + ", " : "") +
                (viajeInternacional != null ? "viajeInternacional=" + viajeInternacional + ", " : "") +
                (viajeNacional != null ? "viajeNacional=" + viajeNacional + ", " : "") +
                (trabajadorSalud != null ? "trabajadorSalud=" + trabajadorSalud + ", " : "") +
                (ninguna != null ? "ninguna=" + ninguna + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
