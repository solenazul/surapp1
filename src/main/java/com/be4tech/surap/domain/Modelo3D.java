package com.be4tech.surap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A Modelo3D.
 */
@Entity
@Table(name = "modelo_3_d")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Modelo3D implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "urt")
    private String urt;

    @Column(name = "colider")
    private String colider;

    @Column(name = "texture_a")
    private String textureA;

    @Column(name = "texture_bc")
    private String textureBC;

    @Column(name = "texture_n")
    private String textureN;

    @Column(name = "texture_r")
    private String textureR;

    @Column(name = "shadow")
    private String shadow;

    @Column(name = "acutalizado")
    private Boolean acutalizado;

    @Column(name = "fecha")
    private LocalDate fecha;

    @ManyToOne
    @JsonIgnoreProperties("modelo3DS")
    private User idUser;

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

    public Modelo3D nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrt() {
        return urt;
    }

    public Modelo3D urt(String urt) {
        this.urt = urt;
        return this;
    }

    public void setUrt(String urt) {
        this.urt = urt;
    }

    public String getColider() {
        return colider;
    }

    public Modelo3D colider(String colider) {
        this.colider = colider;
        return this;
    }

    public void setColider(String colider) {
        this.colider = colider;
    }

    public String getTextureA() {
        return textureA;
    }

    public Modelo3D textureA(String textureA) {
        this.textureA = textureA;
        return this;
    }

    public void setTextureA(String textureA) {
        this.textureA = textureA;
    }

    public String getTextureBC() {
        return textureBC;
    }

    public Modelo3D textureBC(String textureBC) {
        this.textureBC = textureBC;
        return this;
    }

    public void setTextureBC(String textureBC) {
        this.textureBC = textureBC;
    }

    public String getTextureN() {
        return textureN;
    }

    public Modelo3D textureN(String textureN) {
        this.textureN = textureN;
        return this;
    }

    public void setTextureN(String textureN) {
        this.textureN = textureN;
    }

    public String getTextureR() {
        return textureR;
    }

    public Modelo3D textureR(String textureR) {
        this.textureR = textureR;
        return this;
    }

    public void setTextureR(String textureR) {
        this.textureR = textureR;
    }

    public String getShadow() {
        return shadow;
    }

    public Modelo3D shadow(String shadow) {
        this.shadow = shadow;
        return this;
    }

    public void setShadow(String shadow) {
        this.shadow = shadow;
    }

    public Boolean isAcutalizado() {
        return acutalizado;
    }

    public Modelo3D acutalizado(Boolean acutalizado) {
        this.acutalizado = acutalizado;
        return this;
    }

    public void setAcutalizado(Boolean acutalizado) {
        this.acutalizado = acutalizado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Modelo3D fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public User getIdUser() {
        return idUser;
    }

    public Modelo3D idUser(User user) {
        this.idUser = user;
        return this;
    }

    public void setIdUser(User user) {
        this.idUser = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Modelo3D)) {
            return false;
        }
        return id != null && id.equals(((Modelo3D) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Modelo3D{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", urt='" + getUrt() + "'" +
            ", colider='" + getColider() + "'" +
            ", textureA='" + getTextureA() + "'" +
            ", textureBC='" + getTextureBC() + "'" +
            ", textureN='" + getTextureN() + "'" +
            ", textureR='" + getTextureR() + "'" +
            ", shadow='" + getShadow() + "'" +
            ", acutalizado='" + isAcutalizado() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
