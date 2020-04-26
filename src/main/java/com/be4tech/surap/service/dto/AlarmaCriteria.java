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
 * Criteria class for the {@link com.be4tech.surap.domain.Alarma} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.AlarmaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /alarmas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AlarmaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter timeInstant;

    private StringFilter descripcion;

    private StringFilter procedimiento;

    private LongFilter userId;

    public AlarmaCriteria() {
    }

    public AlarmaCriteria(AlarmaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.timeInstant = other.timeInstant == null ? null : other.timeInstant.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.procedimiento = other.procedimiento == null ? null : other.procedimiento.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public AlarmaCriteria copy() {
        return new AlarmaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getTimeInstant() {
        return timeInstant;
    }

    public void setTimeInstant(InstantFilter timeInstant) {
        this.timeInstant = timeInstant;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public StringFilter getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(StringFilter procedimiento) {
        this.procedimiento = procedimiento;
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
        final AlarmaCriteria that = (AlarmaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(timeInstant, that.timeInstant) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(procedimiento, that.procedimiento) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        timeInstant,
        descripcion,
        procedimiento,
        userId
        );
    }

    @Override
    public String toString() {
        return "AlarmaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (timeInstant != null ? "timeInstant=" + timeInstant + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (procedimiento != null ? "procedimiento=" + procedimiento + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
