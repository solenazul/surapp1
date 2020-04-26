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
 * Criteria class for the {@link com.be4tech.surap.domain.Paciente} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.PacienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pacientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PacienteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private IntegerFilter identificacion;

    private IntegerFilter edad;

    private StringFilter sexo;

    private LocalDateFilter fechaNacimiento;

    private LongFilter ipsId;

    private LongFilter userId;

    public PacienteCriteria() {
    }

    public PacienteCriteria(PacienteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.identificacion = other.identificacion == null ? null : other.identificacion.copy();
        this.edad = other.edad == null ? null : other.edad.copy();
        this.sexo = other.sexo == null ? null : other.sexo.copy();
        this.fechaNacimiento = other.fechaNacimiento == null ? null : other.fechaNacimiento.copy();
        this.ipsId = other.ipsId == null ? null : other.ipsId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public PacienteCriteria copy() {
        return new PacienteCriteria(this);
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

    public IntegerFilter getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(IntegerFilter identificacion) {
        this.identificacion = identificacion;
    }

    public IntegerFilter getEdad() {
        return edad;
    }

    public void setEdad(IntegerFilter edad) {
        this.edad = edad;
    }

    public StringFilter getSexo() {
        return sexo;
    }

    public void setSexo(StringFilter sexo) {
        this.sexo = sexo;
    }

    public LocalDateFilter getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDateFilter fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LongFilter getIpsId() {
        return ipsId;
    }

    public void setIpsId(LongFilter ipsId) {
        this.ipsId = ipsId;
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
        final PacienteCriteria that = (PacienteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(identificacion, that.identificacion) &&
            Objects.equals(edad, that.edad) &&
            Objects.equals(sexo, that.sexo) &&
            Objects.equals(fechaNacimiento, that.fechaNacimiento) &&
            Objects.equals(ipsId, that.ipsId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        identificacion,
        edad,
        sexo,
        fechaNacimiento,
        ipsId,
        userId
        );
    }

    @Override
    public String toString() {
        return "PacienteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (identificacion != null ? "identificacion=" + identificacion + ", " : "") +
                (edad != null ? "edad=" + edad + ", " : "") +
                (sexo != null ? "sexo=" + sexo + ", " : "") +
                (fechaNacimiento != null ? "fechaNacimiento=" + fechaNacimiento + ", " : "") +
                (ipsId != null ? "ipsId=" + ipsId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
