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
 * Criteria class for the {@link com.be4tech.surap.domain.EncuestaSintomas} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.EncuestaSintomasResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /encuesta-sintomas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EncuestaSintomasCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter fiebre;

    private BooleanFilter dolorGarganta;

    private BooleanFilter congestionNasal;

    private BooleanFilter tos;

    private BooleanFilter dificultadRespirar;

    private BooleanFilter fatiga;

    private BooleanFilter escalofrio;

    private BooleanFilter dolorMuscular;

    private BooleanFilter ninguno;

    private InstantFilter fecha;

    private LongFilter userId;

    public EncuestaSintomasCriteria() {
    }

    public EncuestaSintomasCriteria(EncuestaSintomasCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fiebre = other.fiebre == null ? null : other.fiebre.copy();
        this.dolorGarganta = other.dolorGarganta == null ? null : other.dolorGarganta.copy();
        this.congestionNasal = other.congestionNasal == null ? null : other.congestionNasal.copy();
        this.tos = other.tos == null ? null : other.tos.copy();
        this.dificultadRespirar = other.dificultadRespirar == null ? null : other.dificultadRespirar.copy();
        this.fatiga = other.fatiga == null ? null : other.fatiga.copy();
        this.escalofrio = other.escalofrio == null ? null : other.escalofrio.copy();
        this.dolorMuscular = other.dolorMuscular == null ? null : other.dolorMuscular.copy();
        this.ninguno = other.ninguno == null ? null : other.ninguno.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public EncuestaSintomasCriteria copy() {
        return new EncuestaSintomasCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getFiebre() {
        return fiebre;
    }

    public void setFiebre(BooleanFilter fiebre) {
        this.fiebre = fiebre;
    }

    public BooleanFilter getDolorGarganta() {
        return dolorGarganta;
    }

    public void setDolorGarganta(BooleanFilter dolorGarganta) {
        this.dolorGarganta = dolorGarganta;
    }

    public BooleanFilter getCongestionNasal() {
        return congestionNasal;
    }

    public void setCongestionNasal(BooleanFilter congestionNasal) {
        this.congestionNasal = congestionNasal;
    }

    public BooleanFilter getTos() {
        return tos;
    }

    public void setTos(BooleanFilter tos) {
        this.tos = tos;
    }

    public BooleanFilter getDificultadRespirar() {
        return dificultadRespirar;
    }

    public void setDificultadRespirar(BooleanFilter dificultadRespirar) {
        this.dificultadRespirar = dificultadRespirar;
    }

    public BooleanFilter getFatiga() {
        return fatiga;
    }

    public void setFatiga(BooleanFilter fatiga) {
        this.fatiga = fatiga;
    }

    public BooleanFilter getEscalofrio() {
        return escalofrio;
    }

    public void setEscalofrio(BooleanFilter escalofrio) {
        this.escalofrio = escalofrio;
    }

    public BooleanFilter getDolorMuscular() {
        return dolorMuscular;
    }

    public void setDolorMuscular(BooleanFilter dolorMuscular) {
        this.dolorMuscular = dolorMuscular;
    }

    public BooleanFilter getNinguno() {
        return ninguno;
    }

    public void setNinguno(BooleanFilter ninguno) {
        this.ninguno = ninguno;
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
        final EncuestaSintomasCriteria that = (EncuestaSintomasCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fiebre, that.fiebre) &&
            Objects.equals(dolorGarganta, that.dolorGarganta) &&
            Objects.equals(congestionNasal, that.congestionNasal) &&
            Objects.equals(tos, that.tos) &&
            Objects.equals(dificultadRespirar, that.dificultadRespirar) &&
            Objects.equals(fatiga, that.fatiga) &&
            Objects.equals(escalofrio, that.escalofrio) &&
            Objects.equals(dolorMuscular, that.dolorMuscular) &&
            Objects.equals(ninguno, that.ninguno) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fiebre,
        dolorGarganta,
        congestionNasal,
        tos,
        dificultadRespirar,
        fatiga,
        escalofrio,
        dolorMuscular,
        ninguno,
        fecha,
        userId
        );
    }

    @Override
    public String toString() {
        return "EncuestaSintomasCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fiebre != null ? "fiebre=" + fiebre + ", " : "") +
                (dolorGarganta != null ? "dolorGarganta=" + dolorGarganta + ", " : "") +
                (congestionNasal != null ? "congestionNasal=" + congestionNasal + ", " : "") +
                (tos != null ? "tos=" + tos + ", " : "") +
                (dificultadRespirar != null ? "dificultadRespirar=" + dificultadRespirar + ", " : "") +
                (fatiga != null ? "fatiga=" + fatiga + ", " : "") +
                (escalofrio != null ? "escalofrio=" + escalofrio + ", " : "") +
                (dolorMuscular != null ? "dolorMuscular=" + dolorMuscular + ", " : "") +
                (ninguno != null ? "ninguno=" + ninguno + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
