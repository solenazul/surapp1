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
 * Criteria class for the {@link com.be4tech.surap.domain.HistorialOfertas} entity. This class is used
 * in {@link com.be4tech.surap.web.rest.HistorialOfertasResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /historial-ofertas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HistorialOfertasCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter valorProducto;

    private IntegerFilter valorOferta;

    private LocalDateFilter fechaInicial;

    private LocalDateFilter fechaFinal;

    private LongFilter productoIdId;

    public HistorialOfertasCriteria() {
    }

    public HistorialOfertasCriteria(HistorialOfertasCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.valorProducto = other.valorProducto == null ? null : other.valorProducto.copy();
        this.valorOferta = other.valorOferta == null ? null : other.valorOferta.copy();
        this.fechaInicial = other.fechaInicial == null ? null : other.fechaInicial.copy();
        this.fechaFinal = other.fechaFinal == null ? null : other.fechaFinal.copy();
        this.productoIdId = other.productoIdId == null ? null : other.productoIdId.copy();
    }

    @Override
    public HistorialOfertasCriteria copy() {
        return new HistorialOfertasCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getValorProducto() {
        return valorProducto;
    }

    public void setValorProducto(IntegerFilter valorProducto) {
        this.valorProducto = valorProducto;
    }

    public IntegerFilter getValorOferta() {
        return valorOferta;
    }

    public void setValorOferta(IntegerFilter valorOferta) {
        this.valorOferta = valorOferta;
    }

    public LocalDateFilter getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(LocalDateFilter fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public LocalDateFilter getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDateFilter fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public LongFilter getProductoIdId() {
        return productoIdId;
    }

    public void setProductoIdId(LongFilter productoIdId) {
        this.productoIdId = productoIdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HistorialOfertasCriteria that = (HistorialOfertasCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(valorProducto, that.valorProducto) &&
            Objects.equals(valorOferta, that.valorOferta) &&
            Objects.equals(fechaInicial, that.fechaInicial) &&
            Objects.equals(fechaFinal, that.fechaFinal) &&
            Objects.equals(productoIdId, that.productoIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        valorProducto,
        valorOferta,
        fechaInicial,
        fechaFinal,
        productoIdId
        );
    }

    @Override
    public String toString() {
        return "HistorialOfertasCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (valorProducto != null ? "valorProducto=" + valorProducto + ", " : "") +
                (valorOferta != null ? "valorOferta=" + valorOferta + ", " : "") +
                (fechaInicial != null ? "fechaInicial=" + fechaInicial + ", " : "") +
                (fechaFinal != null ? "fechaFinal=" + fechaFinal + ", " : "") +
                (productoIdId != null ? "productoIdId=" + productoIdId + ", " : "") +
            "}";
    }

}
