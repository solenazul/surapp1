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
 * Criteria class for the {@link com.be4tech.surap.domain.Servicios} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.ServiciosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /servicios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiciosCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter descripcion;

    private StringFilter proveedor;

    private IntegerFilter impuesto;

    private IntegerFilter valor;

    private IntegerFilter unidad;

    private BooleanFilter dispinibilidad;

    private IntegerFilter descuento;

    private BooleanFilter remate;

    private StringFilter tags;

    private IntegerFilter puntuacion;

    private IntegerFilter vistos;

    private IntegerFilter oferta;

    private IntegerFilter tiempoOferta;

    public ServiciosCriteria() {
    }

    public ServiciosCriteria(ServiciosCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.proveedor = other.proveedor == null ? null : other.proveedor.copy();
        this.impuesto = other.impuesto == null ? null : other.impuesto.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.unidad = other.unidad == null ? null : other.unidad.copy();
        this.dispinibilidad = other.dispinibilidad == null ? null : other.dispinibilidad.copy();
        this.descuento = other.descuento == null ? null : other.descuento.copy();
        this.remate = other.remate == null ? null : other.remate.copy();
        this.tags = other.tags == null ? null : other.tags.copy();
        this.puntuacion = other.puntuacion == null ? null : other.puntuacion.copy();
        this.vistos = other.vistos == null ? null : other.vistos.copy();
        this.oferta = other.oferta == null ? null : other.oferta.copy();
        this.tiempoOferta = other.tiempoOferta == null ? null : other.tiempoOferta.copy();
    }

    @Override
    public ServiciosCriteria copy() {
        return new ServiciosCriteria(this);
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

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public StringFilter getProveedor() {
        return proveedor;
    }

    public void setProveedor(StringFilter proveedor) {
        this.proveedor = proveedor;
    }

    public IntegerFilter getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(IntegerFilter impuesto) {
        this.impuesto = impuesto;
    }

    public IntegerFilter getValor() {
        return valor;
    }

    public void setValor(IntegerFilter valor) {
        this.valor = valor;
    }

    public IntegerFilter getUnidad() {
        return unidad;
    }

    public void setUnidad(IntegerFilter unidad) {
        this.unidad = unidad;
    }

    public BooleanFilter getDispinibilidad() {
        return dispinibilidad;
    }

    public void setDispinibilidad(BooleanFilter dispinibilidad) {
        this.dispinibilidad = dispinibilidad;
    }

    public IntegerFilter getDescuento() {
        return descuento;
    }

    public void setDescuento(IntegerFilter descuento) {
        this.descuento = descuento;
    }

    public BooleanFilter getRemate() {
        return remate;
    }

    public void setRemate(BooleanFilter remate) {
        this.remate = remate;
    }

    public StringFilter getTags() {
        return tags;
    }

    public void setTags(StringFilter tags) {
        this.tags = tags;
    }

    public IntegerFilter getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(IntegerFilter puntuacion) {
        this.puntuacion = puntuacion;
    }

    public IntegerFilter getVistos() {
        return vistos;
    }

    public void setVistos(IntegerFilter vistos) {
        this.vistos = vistos;
    }

    public IntegerFilter getOferta() {
        return oferta;
    }

    public void setOferta(IntegerFilter oferta) {
        this.oferta = oferta;
    }

    public IntegerFilter getTiempoOferta() {
        return tiempoOferta;
    }

    public void setTiempoOferta(IntegerFilter tiempoOferta) {
        this.tiempoOferta = tiempoOferta;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServiciosCriteria that = (ServiciosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(proveedor, that.proveedor) &&
            Objects.equals(impuesto, that.impuesto) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(unidad, that.unidad) &&
            Objects.equals(dispinibilidad, that.dispinibilidad) &&
            Objects.equals(descuento, that.descuento) &&
            Objects.equals(remate, that.remate) &&
            Objects.equals(tags, that.tags) &&
            Objects.equals(puntuacion, that.puntuacion) &&
            Objects.equals(vistos, that.vistos) &&
            Objects.equals(oferta, that.oferta) &&
            Objects.equals(tiempoOferta, that.tiempoOferta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        descripcion,
        proveedor,
        impuesto,
        valor,
        unidad,
        dispinibilidad,
        descuento,
        remate,
        tags,
        puntuacion,
        vistos,
        oferta,
        tiempoOferta
        );
    }

    @Override
    public String toString() {
        return "ServiciosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (proveedor != null ? "proveedor=" + proveedor + ", " : "") +
                (impuesto != null ? "impuesto=" + impuesto + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (unidad != null ? "unidad=" + unidad + ", " : "") +
                (dispinibilidad != null ? "dispinibilidad=" + dispinibilidad + ", " : "") +
                (descuento != null ? "descuento=" + descuento + ", " : "") +
                (remate != null ? "remate=" + remate + ", " : "") +
                (tags != null ? "tags=" + tags + ", " : "") +
                (puntuacion != null ? "puntuacion=" + puntuacion + ", " : "") +
                (vistos != null ? "vistos=" + vistos + ", " : "") +
                (oferta != null ? "oferta=" + oferta + ", " : "") +
                (tiempoOferta != null ? "tiempoOferta=" + tiempoOferta + ", " : "") +
            "}";
    }

}
