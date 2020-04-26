package com.be4tech.surap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "identificacion")
    private Integer identificacion;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "condicion")
    private String condicion;

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private IPS ips;

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private User user;

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

    public Paciente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdentificacion() {
        return identificacion;
    }

    public Paciente identificacion(Integer identificacion) {
        this.identificacion = identificacion;
        return this;
    }

    public void setIdentificacion(Integer identificacion) {
        this.identificacion = identificacion;
    }

    public Integer getEdad() {
        return edad;
    }

    public Paciente edad(Integer edad) {
        this.edad = edad;
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public Paciente sexo(String sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Paciente fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCondicion() {
        return condicion;
    }

    public Paciente condicion(String condicion) {
        this.condicion = condicion;
        return this;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public IPS getIps() {
        return ips;
    }

    public Paciente ips(IPS iPS) {
        this.ips = iPS;
        return this;
    }

    public void setIps(IPS iPS) {
        this.ips = iPS;
    }

    public User getUser() {
        return user;
    }

    public Paciente user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paciente)) {
            return false;
        }
        return id != null && id.equals(((Paciente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", identificacion=" + getIdentificacion() +
            ", edad=" + getEdad() +
            ", sexo='" + getSexo() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", condicion='" + getCondicion() + "'" +
            "}";
    }
}
