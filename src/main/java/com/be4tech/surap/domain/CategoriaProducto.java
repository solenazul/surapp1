package com.be4tech.surap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A CategoriaProducto.
 */
@Entity
@Table(name = "categoria_producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CategoriaProducto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "fecha")
    private Instant fecha;

    @ManyToOne
    @JsonIgnoreProperties("categoriaProductos")
    private Categorias catagoriaId;

    @ManyToOne
    @JsonIgnoreProperties("categoriaProductos")
    private Productos productoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public CategoriaProducto categoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Instant getFecha() {
        return fecha;
    }

    public CategoriaProducto fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Categorias getCatagoriaId() {
        return catagoriaId;
    }

    public CategoriaProducto catagoriaId(Categorias categorias) {
        this.catagoriaId = categorias;
        return this;
    }

    public void setCatagoriaId(Categorias categorias) {
        this.catagoriaId = categorias;
    }

    public Productos getProductoId() {
        return productoId;
    }

    public CategoriaProducto productoId(Productos productos) {
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
        if (!(o instanceof CategoriaProducto)) {
            return false;
        }
        return id != null && id.equals(((CategoriaProducto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CategoriaProducto{" +
            "id=" + getId() +
            ", categoria='" + getCategoria() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
