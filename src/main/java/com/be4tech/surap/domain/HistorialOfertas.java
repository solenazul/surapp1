package com.be4tech.surap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A HistorialOfertas.
 */
@Entity
@Table(name = "historial_ofertas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HistorialOfertas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "valor_producto")
    private Integer valorProducto;

    @Column(name = "valor_oferta")
    private Integer valorOferta;

    @Column(name = "fecha_inicial")
    private LocalDate fechaInicial;

    @Column(name = "fecha_final")
    private LocalDate fechaFinal;

    @ManyToOne
    @JsonIgnoreProperties("historialOfertas")
    private Productos productoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValorProducto() {
        return valorProducto;
    }

    public HistorialOfertas valorProducto(Integer valorProducto) {
        this.valorProducto = valorProducto;
        return this;
    }

    public void setValorProducto(Integer valorProducto) {
        this.valorProducto = valorProducto;
    }

    public Integer getValorOferta() {
        return valorOferta;
    }

    public HistorialOfertas valorOferta(Integer valorOferta) {
        this.valorOferta = valorOferta;
        return this;
    }

    public void setValorOferta(Integer valorOferta) {
        this.valorOferta = valorOferta;
    }

    public LocalDate getFechaInicial() {
        return fechaInicial;
    }

    public HistorialOfertas fechaInicial(LocalDate fechaInicial) {
        this.fechaInicial = fechaInicial;
        return this;
    }

    public void setFechaInicial(LocalDate fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public HistorialOfertas fechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
        return this;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Productos getProductoId() {
        return productoId;
    }

    public HistorialOfertas productoId(Productos productos) {
        this.productoId = productos;
        return this;
    }

    public void setProductoId(Productos productos) {
        this.productoId = productos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistorialOfertas)) {
            return false;
        }
        return id != null && id.equals(((HistorialOfertas) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HistorialOfertas{" +
            "id=" + getId() +
            ", valorProducto=" + getValorProducto() +
            ", valorOferta=" + getValorOferta() +
            ", fechaInicial='" + getFechaInicial() + "'" +
            ", fechaFinal='" + getFechaFinal() + "'" +
            "}";
    }
}
