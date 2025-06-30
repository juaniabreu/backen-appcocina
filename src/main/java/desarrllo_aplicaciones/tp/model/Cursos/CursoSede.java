package desarrllo_aplicaciones.tp.model.Cursos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity

public class CursoSede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Curso curso;

    @ManyToOne
    private Sede sede;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private String horario; // o puede ser LocalTime + Enum de día

    private Double precio;
    private Double descuento; // opcional
    private Integer cuposTotales;
    private Integer cuposDisponibles;

    private String requisitosInsumos;
    private String requisitosUtensilios;

    @OneToMany(mappedBy = "cursoSede", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InscripcionCurso> inscripciones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Integer getCuposTotales() {
        return cuposTotales;
    }

    public void setCuposTotales(Integer cuposTotales) {
        this.cuposTotales = cuposTotales;
    }

    public Integer getCuposDisponibles() {
        return cuposDisponibles;
    }

    public void setCuposDisponibles(Integer cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }

    public String getRequisitosInsumos() {
        return requisitosInsumos;
    }

    public void setRequisitosInsumos(String requisitosInsumos) {
        this.requisitosInsumos = requisitosInsumos;
    }

    public String getRequisitosUtensilios() {
        return requisitosUtensilios;
    }

    public void setRequisitosUtensilios(String requisitosUtensilios) {
        this.requisitosUtensilios = requisitosUtensilios;
    }

    public List<InscripcionCurso> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<InscripcionCurso> inscripciones) {
        this.inscripciones = inscripciones;
    }
}
