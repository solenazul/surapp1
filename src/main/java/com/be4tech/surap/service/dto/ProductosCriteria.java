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
 * Criteria class for the {@link com.be4tech.surap.domain.Productos} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.ProductosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /productos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductosCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter descripcion;

    private IntegerFilter inventario;

    private IntegerFilter tipo;

    private IntegerFilter impuesto;

    private IntegerFilter valor;

    private IntegerFilter unidad;

    private IntegerFilter estado;

    private IntegerFilter tiempoEntrega;

    private BooleanFilter dispinibilidad;

    private BooleanFilter nuevo;

    private IntegerFilter descuento;

    private BooleanFilter remate;

    private StringFilter tags;

    private IntegerFilter puntuacion;

    private IntegerFilter vistos;

    private IntegerFilter oferta;

    private IntegerFilter tiempoOferta;

    public ProductosCriteria() {
    }

    public ProductosCriteria(ProductosCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.inventario = other.inventario == null ? null : other.inventario.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.impuesto = other.impuesto == null ? null : other.impuesto.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.unidad = other.unidad == null ? null : other.unidad.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.tiempoEntrega = other.tiempoEntrega == null ? null : other.tiempoEntrega.copy();
        this.dispinibilidad = other.dispinibilidad == null ? null : other.dispinibilidad.copy();
        this.nuevo = other.nuevo == null ? null : other.nuevo.copy();
        this.descuento = other.descuento == null ? null : other.descuento.copy();
        this.remate = other.remate == null ? null : other.remate.copy();
        this.tags = other.tags == null ? null : other.tags.copy();
        this.puntuacion = other.puntuacion == null ? null : other.puntuacion.copy();
        this.vistos = other.vistos == null ? null : other.vistos.copy();
        this.oferta = other.oferta == null ? null : other.oferta.copy();
        this.tiempoOferta = other.tiempoOferta == null ? null : other.tiempoOferta.copy();
    }

    @Override
    public ProductosCriteria copy() {
        return new ProductosCriteria(this);
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

    public IntegerFilter getInventario() {
        return inventario;
    }

    public void setInventario(IntegerFilter inventario) {
        this.inventario = inventario;
    }

    public IntegerFilter getTipo() {
        return tipo;
    }

    public void setTipo(IntegerFilter tipo) {
        this.tipo = tipo;
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

    public IntegerFilter getEstado() {
        return estado;
    }

    public void setEstado(IntegerFilter estado) {
        this.estado = estado;
    }

    public IntegerFilter getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(IntegerFilter tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    public BooleanFilter getDispinibilidad() {
        return dispinibilidad;
    }

    public void setDispinibilidad(BooleanFilter dispinibilidad) {
        this.dispinibilidad = dispinibilidad;
    }

    public BooleanFilter getNuevo() {
        return nuevo;
    }

    public void setNuevo(BooleanFilter nuevo) {
        this.nuevo = nuevo;
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
        final ProductosCriteria that = (ProductosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(inventario, that.inventario) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(impuesto, that.impuesto) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(unidad, that.unidad) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(tiempoEntrega, that.tiempoEntrega) &&
            Objects.equals(dispinibilidad, that.dispinibilidad) &&
            Objects.equals(nuevo, that.nuevo) &&
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
        inventario,
        tipo,
        impuesto,
        valor,
        unidad,
        estado,
        tiempoEntrega,
        dispinibilidad,
        nuevo,
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
        return "ProductosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (inventario != null ? "inventario=" + inventario + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (impuesto != null ? "impuesto=" + impuesto + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (unidad != null ? "unidad=" + unidad + ", " : "") +
                (estado != null ? "estado=" + estado + ", " : "") +
                (tiempoEntrega != null ? "tiempoEntrega=" + tiempoEntrega + ", " : "") +
                (dispinibilidad != null ? "dispinibilidad=" + dispinibilidad + ", " : "") +
                (nuevo != null ? "nuevo=" + nuevo + ", " : "") +
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
