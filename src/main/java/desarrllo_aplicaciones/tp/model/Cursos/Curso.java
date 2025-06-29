package desarrllo_aplicaciones.tp.model.Cursos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcionCorta;

    @Lob
    private String descripcionCompleta;

    @Column(nullable = false)
    private String modalidad; // presencial, virtual, online

    @Column(nullable = false)
    private double precio;

    private String horario;


    @ManyToMany
    @JoinTable(name = "curso_sede",
    joinColumns = @JoinColumn(name = "curso_id"),
    inverseJoinColumns = @JoinColumn(name = "sede_id")
    )
    private List<Sede> sedes;

    public List<Sede> getSedes() {
        return sedes;
    }

    public void setSedes(List<Sede> sedes) {
        this.sedes = sedes;
    }

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @ElementCollection
    private List<String> temas; // o @OneToMany si querés detalle

    @ElementCollection
    private List<String> insumosRequeridos;

    private boolean requiereAsistencia;

    @ManyToMany(mappedBy = "cursosInscriptos")
    private List<Usuario> alumnos;

    public List<Usuario> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Usuario> alumnos) {
        this.alumnos = alumnos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getDescripcionCompleta() {
        return descripcionCompleta;
    }

    public void setDescripcionCompleta(String descripcionCompleta) {
        this.descripcionCompleta = descripcionCompleta;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
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

    public List<String> getTemas() {
        return temas;
    }

    public void setTemas(List<String> temas) {
        this.temas = temas;
    }

    public List<String> getInsumosRequeridos() {
        return insumosRequeridos;
    }

    public void setInsumosRequeridos(List<String> insumosRequeridos) {
        this.insumosRequeridos = insumosRequeridos;
    }

    public boolean isRequiereAsistencia() {
        return requiereAsistencia;
    }

    public void setRequiereAsistencia(boolean requiereAsistencia) {
        this.requiereAsistencia = requiereAsistencia;
    }
}
