package com.be4tech.surap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Productos.
 */
@Entity
@Table(name = "productos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Productos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Column(name = "inventario")
    private Integer inventario;

    @Column(name = "tipo")
    private Integer tipo;

    @Column(name = "impuesto")
    private Integer impuesto;

    @Column(name = "valor")
    private Integer valor;

    @Column(name = "unidad")
    private Integer unidad;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "tiempo_entrega")
    private Integer tiempoEntrega;

    @Column(name = "dispinibilidad")
    private Boolean dispinibilidad;

    @Column(name = "nuevo")
    private Boolean nuevo;

    @Column(name = "descuento")
    private Integer descuento;

    @Column(name = "remate")
    private Boolean remate;

    @Column(name = "tags")
    private String tags;

    @Column(name = "puntuacion")
    private Integer puntuacion;

    @Column(name = "vistos")
    private Integer vistos;

    @Column(name = "oferta")
    private Integer oferta;

    @Column(name = "tiempo_oferta")
    private Integer tiempoOferta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Productos nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Productos descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Productos imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Productos imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Integer getInventario() {
        return inventario;
    }

    public Productos inventario(Integer inventario) {
        this.inventario = inventario;
        return this;
    }

    public void setInventario(Integer inventario) {
        this.inventario = inventario;
    }

    public Integer getTipo() {
        return tipo;
    }

    public Productos tipo(Integer tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getImpuesto() {
        return impuesto;
    }

    public Productos impuesto(Integer impuesto) {
        this.impuesto = impuesto;
        return this;
    }

    public void setImpuesto(Integer impuesto) {
        this.impuesto = impuesto;
    }

    public Integer getValor() {
        return valor;
    }

    public Productos valor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Integer getUnidad() {
        return unidad;
    }

    public Productos unidad(Integer unidad) {
        this.unidad = unidad;
        return this;
    }

    public void setUnidad(Integer unidad) {
        this.unidad = unidad;
    }

    public Integer getEstado() {
        return estado;
    }

    public Productos estado(Integer estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getTiempoEntrega() {
        return tiempoEntrega;
    }

    public Productos tiempoEntrega(Integer tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
        return this;
    }

    public void setTiempoEntrega(Integer tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    public Boolean isDispinibilidad() {
        return dispinibilidad;
    }

    public Productos dispinibilidad(Boolean dispinibilidad) {
        this.dispinibilidad = dispinibilidad;
        return this;
    }

    public void setDispinibilidad(Boolean dispinibilidad) {
        this.dispinibilidad = dispinibilidad;
    }

    public Boolean isNuevo() {
        return nuevo;
    }

    public Productos nuevo(Boolean nuevo) {
        this.nuevo = nuevo;
        return this;
    }

    public void setNuevo(Boolean nuevo) {
        this.nuevo = nuevo;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public Productos descuento(Integer descuento) {
        this.descuento = descuento;
        return this;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public Boolean isRemate() {
        return remate;
    }

    public Productos remate(Boolean remate) {
        this.remate = remate;
        return this;
    }

    public void setRemate(Boolean remate) {
        this.remate = remate;
    }

    public String getTags() {
        return tags;
    }

    public Productos tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public Productos puntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
        return this;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Integer getVistos() {
        return vistos;
    }

    public Productos vistos(Integer vistos) {
        this.vistos = vistos;
        return this;
    }

    public void setVistos(Integer vistos) {
        this.vistos = vistos;
    }

    public Integer getOferta() {
        return oferta;
    }

    public Productos oferta(Integer oferta) {
        this.oferta = oferta;
        return this;
    }

    public void setOferta(Integer oferta) {
        this.oferta = oferta;
    }

    public Integer getTiempoOferta() {
        return tiempoOferta;
    }

    public Productos tiempoOferta(Integer tiempoOferta) {
        this.tiempoOferta = tiempoOferta;
        return this;
    }

    public void setTiempoOferta(Integer tiempoOferta) {
        this.tiempoOferta = tiempoOferta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Productos)) {
            return false;
        }
        return id != null && id.equals(((Productos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Productos{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            ", inventario=" + getInventario() +
            ", tipo=" + getTipo() +
            ", impuesto=" + getImpuesto() +
            ", valor=" + getValor() +
            ", unidad=" + getUnidad() +
            ", estado=" + getEstado() +
            ", tiempoEntrega=" + getTiempoEntrega() +
            ", dispinibilidad='" + isDispinibilidad() + "'" +
            ", nuevo='" + isNuevo() + "'" +
            ", descuento=" + getDescuento() +
            ", remate='" + isRemate() + "'" +
            ", tags='" + getTags() + "'" +
            ", puntuacion=" + getPuntuacion() +
            ", vistos=" + getVistos() +
            ", oferta=" + getOferta() +
            ", tiempoOferta=" + getTiempoOferta() +
            "}";
    }
}
