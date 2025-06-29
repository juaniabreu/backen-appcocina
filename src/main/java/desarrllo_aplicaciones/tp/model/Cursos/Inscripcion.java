package desarrllo_aplicaciones.tp.model.Cursos;

import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "alumno_id")
    private Usuario alumno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Column(nullable = false)
    private LocalDate fechaInscripcion;

    @Column(nullable = false)
    private double montoPagado;


    @ManyToOne
    @JoinColumn(name = "sede_id")
    private Sede sede;

    private boolean dadoDeBaja = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoInscripcion estado;

    public enum EstadoInscripcion {
        INSCRIPTO,
        BAJA_100,
        BAJA_70,
        BAJA_50,
        FINALIZADO
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getAlumno() {
        return alumno;
    }

    public void setAlumno(Usuario alumno) {
        this.alumno = alumno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public boolean isDadoDeBaja() {
        return dadoDeBaja;
    }

    public void setDadoDeBaja(boolean dadoDeBaja) {
        this.dadoDeBaja = dadoDeBaja;
    }

    public EstadoInscripcion getEstado() {
        return estado;
    }

    public void setEstado(EstadoInscripcion estado) {
        this.estado = estado;
    }
}
