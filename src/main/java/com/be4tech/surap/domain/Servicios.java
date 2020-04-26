package com.be4tech.surap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Servicios.
 */
@Entity
@Table(name = "servicios")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Servicios implements Serializable {

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

    @Lob
    @Column(name = "videos")
    private byte[] videos;

    @Column(name = "videos_content_type")
    private String videosContentType;

    @Lob
    @Column(name = "documento")
    private byte[] documento;

    @Column(name = "documento_content_type")
    private String documentoContentType;

    @Column(name = "proveedor")
    private String proveedor;

    @Column(name = "impuesto")
    private Integer impuesto;

    @Column(name = "valor")
    private Integer valor;

    @Column(name = "unidad")
    private Integer unidad;

    @Column(name = "dispinibilidad")
    private Boolean dispinibilidad;

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

    public Servicios nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Servicios descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Servicios imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Servicios imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public byte[] getVideos() {
        return videos;
    }

    public Servicios videos(byte[] videos) {
        this.videos = videos;
        return this;
    }

    public void setVideos(byte[] videos) {
        this.videos = videos;
    }

    public String getVideosContentType() {
        return videosContentType;
    }

    public Servicios videosContentType(String videosContentType) {
        this.videosContentType = videosContentType;
        return this;
    }

    public void setVideosContentType(String videosContentType) {
        this.videosContentType = videosContentType;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public Servicios documento(byte[] documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public String getDocumentoContentType() {
        return documentoContentType;
    }

    public Servicios documentoContentType(String documentoContentType) {
        this.documentoContentType = documentoContentType;
        return this;
    }

    public void setDocumentoContentType(String documentoContentType) {
        this.documentoContentType = documentoContentType;
    }

    public String getProveedor() {
        return proveedor;
    }

    public Servicios proveedor(String proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getImpuesto() {
        return impuesto;
    }

    public Servicios impuesto(Integer impuesto) {
        this.impuesto = impuesto;
        return this;
    }

    public void setImpuesto(Integer impuesto) {
        this.impuesto = impuesto;
    }

    public Integer getValor() {
        return valor;
    }

    public Servicios valor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Integer getUnidad() {
        return unidad;
    }

    public Servicios unidad(Integer unidad) {
        this.unidad = unidad;
        return this;
    }

    public void setUnidad(Integer unidad) {
        this.unidad = unidad;
    }

    public Boolean isDispinibilidad() {
        return dispinibilidad;
    }

    public Servicios dispinibilidad(Boolean dispinibilidad) {
        this.dispinibilidad = dispinibilidad;
        return this;
    }

    public void setDispinibilidad(Boolean dispinibilidad) {
        this.dispinibilidad = dispinibilidad;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public Servicios descuento(Integer descuento) {
        this.descuento = descuento;
        return this;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public Boolean isRemate() {
        return remate;
    }

    public Servicios remate(Boolean remate) {
        this.remate = remate;
        return this;
    }

    public void setRemate(Boolean remate) {
        this.remate = remate;
    }

    public String getTags() {
        return tags;
    }

    public Servicios tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public Servicios puntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
        return this;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Integer getVistos() {
        return vistos;
    }

    public Servicios vistos(Integer vistos) {
        this.vistos = vistos;
        return this;
    }

    public void setVistos(Integer vistos) {
        this.vistos = vistos;
    }

    public Integer getOferta() {
        return oferta;
    }

    public Servicios oferta(Integer oferta) {
        this.oferta = oferta;
        return this;
    }

    public void setOferta(Integer oferta) {
        this.oferta = oferta;
    }

    public Integer getTiempoOferta() {
        return tiempoOferta;
    }

    public Servicios tiempoOferta(Integer tiempoOferta) {
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
        if (!(o instanceof Servicios)) {
            return false;
        }
        return id != null && id.equals(((Servicios) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Servicios{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            ", videos='" + getVideos() + "'" +
            ", videosContentType='" + getVideosContentType() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", documentoContentType='" + getDocumentoContentType() + "'" +
            ", proveedor='" + getProveedor() + "'" +
            ", impuesto=" + getImpuesto() +
            ", valor=" + getValor() +
            ", unidad=" + getUnidad() +
            ", dispinibilidad='" + isDispinibilidad() + "'" +
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
