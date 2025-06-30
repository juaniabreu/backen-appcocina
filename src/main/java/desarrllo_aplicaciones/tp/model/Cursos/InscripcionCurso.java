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
 @NoArgsConstructor @AllArgsConstructor
public class InscripcionCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Usuario alumno;

    @ManyToOne
    private CursoSede cursoSede;

    private LocalDate fechaInscripcion;

    private Double montoPagado;

    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago;

    public enum EstadoPago {
        PAGADO, REINTEGRADO_TOTAL, REINTEGRADO_PARCIAL, NO_REINTEGRADO
    }

    @OneToMany(mappedBy = "inscripcion", cascade = CascadeType.ALL)
    private List<Asistencia> asistencias;

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public CursoSede getCursoSede() {
        return cursoSede;
    }

    public void setCursoSede(CursoSede cursoSede) {
        this.cursoSede = cursoSede;
    }

    public Usuario getAlumno() {
        return alumno;
    }

    public void setAlumno(Usuario alumno) {
        this.alumno = alumno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
