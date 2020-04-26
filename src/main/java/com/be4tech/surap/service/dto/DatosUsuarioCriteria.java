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
 * Criteria class for the {@link com.be4tech.surap.domain.DatosUsuario} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.DatosUsuarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /datos-usuarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DatosUsuarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter fechaNacimiento;

    private StringFilter genero;

    private IntegerFilter telefono;

    private StringFilter pais;

    private StringFilter ciudad;

    private StringFilter direccion;

    private LongFilter idUserId;

    private LongFilter nacionalidadId;

    public DatosUsuarioCriteria() {
    }

    public DatosUsuarioCriteria(DatosUsuarioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fechaNacimiento = other.fechaNacimiento == null ? null : other.fechaNacimiento.copy();
        this.genero = other.genero == null ? null : other.genero.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.pais = other.pais == null ? null : other.pais.copy();
        this.ciudad = other.ciudad == null ? null : other.ciudad.copy();
        this.direccion = other.direccion == null ? null : other.direccion.copy();
        this.idUserId = other.idUserId == null ? null : other.idUserId.copy();
        this.nacionalidadId = other.nacionalidadId == null ? null : other.nacionalidadId.copy();
    }

    @Override
    public DatosUsuarioCriteria copy() {
        return new DatosUsuarioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDateFilter fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public StringFilter getGenero() {
        return genero;
    }

    public void setGenero(StringFilter genero) {
        this.genero = genero;
    }

    public IntegerFilter getTelefono() {
        return telefono;
    }

    public void setTelefono(IntegerFilter telefono) {
        this.telefono = telefono;
    }

    public StringFilter getPais() {
        return pais;
    }

    public void setPais(StringFilter pais) {
        this.pais = pais;
    }

    public StringFilter getCiudad() {
        return ciudad;
    }

    public void setCiudad(StringFilter ciudad) {
        this.ciudad = ciudad;
    }

    public StringFilter getDireccion() {
        return direccion;
    }

    public void setDireccion(StringFilter direccion) {
        this.direccion = direccion;
    }

    public LongFilter getIdUserId() {
        return idUserId;
    }

    public void setIdUserId(LongFilter idUserId) {
        this.idUserId = idUserId;
    }

    public LongFilter getNacionalidadId() {
        return nacionalidadId;
    }

    public void setNacionalidadId(LongFilter nacionalidadId) {
        this.nacionalidadId = nacionalidadId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DatosUsuarioCriteria that = (DatosUsuarioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fechaNacimiento, that.fechaNacimiento) &&
            Objects.equals(genero, that.genero) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(pais, that.pais) &&
            Objects.equals(ciudad, that.ciudad) &&
            Objects.equals(direccion, that.direccion) &&
            Objects.equals(idUserId, that.idUserId) &&
            Objects.equals(nacionalidadId, that.nacionalidadId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fechaNacimiento,
        genero,
        telefono,
        pais,
        ciudad,
        direccion,
        idUserId,
        nacionalidadId
        );
    }

    @Override
    public String toString() {
        return "DatosUsuarioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fechaNacimiento != null ? "fechaNacimiento=" + fechaNacimiento + ", " : "") +
                (genero != null ? "genero=" + genero + ", " : "") +
                (telefono != null ? "telefono=" + telefono + ", " : "") +
                (pais != null ? "pais=" + pais + ", " : "") +
                (ciudad != null ? "ciudad=" + ciudad + ", " : "") +
                (direccion != null ? "direccion=" + direccion + ", " : "") +
                (idUserId != null ? "idUserId=" + idUserId + ", " : "") +
                (nacionalidadId != null ? "nacionalidadId=" + nacionalidadId + ", " : "") +
            "}";
    }

}
