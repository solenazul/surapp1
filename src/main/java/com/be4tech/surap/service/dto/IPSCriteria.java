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

/**
 * Criteria class for the {@link com.be4tech.surap.domain.IPS} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.IPSResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ips?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IPSCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter nit;

    private StringFilter direccion;

    private StringFilter telefono;

    private StringFilter correoElectronico;

    public IPSCriteria() {
    }

    public IPSCriteria(IPSCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.nit = other.nit == null ? null : other.nit.copy();
        this.direccion = other.direccion == null ? null : other.direccion.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.correoElectronico = other.correoElectronico == null ? null : other.correoElectronico.copy();
    }

    @Override
    public IPSCriteria copy() {
        return new IPSCriteria(this);
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

    public StringFilter getNit() {
        return nit;
    }

    public void setNit(StringFilter nit) {
        this.nit = nit;
    }

    public StringFilter getDireccion() {
        return direccion;
    }

    public void setDireccion(StringFilter direccion) {
        this.direccion = direccion;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
    }

    public StringFilter getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(StringFilter correoElectronico) {
        this.correoElectronico = correoElectronico;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IPSCriteria that = (IPSCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(nit, that.nit) &&
            Objects.equals(direccion, that.direccion) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(correoElectronico, that.correoElectronico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        nit,
        direccion,
        telefono,
        correoElectronico
        );
    }

    @Override
    public String toString() {
        return "IPSCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (nit != null ? "nit=" + nit + ", " : "") +
                (direccion != null ? "direccion=" + direccion + ", " : "") +
                (telefono != null ? "telefono=" + telefono + ", " : "") +
                (correoElectronico != null ? "correoElectronico=" + correoElectronico + ", " : "") +
            "}";
    }

}
