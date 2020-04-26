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
 * Criteria class for the {@link com.be4tech.surap.domain.InvitacionTablero} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.InvitacionTableroResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invitacion-tableros?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InvitacionTableroCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter estado;

    private InstantFilter fecha;

    private LongFilter idUserId;

    private LongFilter tableroIdId;

    public InvitacionTableroCriteria() {
    }

    public InvitacionTableroCriteria(InvitacionTableroCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.idUserId = other.idUserId == null ? null : other.idUserId.copy();
        this.tableroIdId = other.tableroIdId == null ? null : other.tableroIdId.copy();
    }

    @Override
    public InvitacionTableroCriteria copy() {
        return new InvitacionTableroCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getEstado() {
        return estado;
    }

    public void setEstado(BooleanFilter estado) {
        this.estado = estado;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InvitacionTableroCriteria that = (InvitacionTableroCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(idUserId, that.idUserId) &&
            Objects.equals(tableroIdId, that.tableroIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        estado,
        fecha,
        idUserId,
        tableroIdId
        );
    }

    @Override
    public String toString() {
        return "InvitacionTableroCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (estado != null ? "estado=" + estado + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (idUserId != null ? "idUserId=" + idUserId + ", " : "") +
                (tableroIdId != null ? "tableroIdId=" + tableroIdId + ", " : "") +
            "}";
    }

}
