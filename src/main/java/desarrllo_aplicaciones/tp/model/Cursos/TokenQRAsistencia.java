package desarrllo_aplicaciones.tp.model.Cursos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TokenQRAsistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CursoSede cursoSede;

    private LocalDate fechaClase;

    private String token; // UUID

    private boolean activo = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CursoSede getCursoSede() {
        return cursoSede;
    }

    public void setCursoSede(CursoSede cursoSede) {
        this.cursoSede = cursoSede;
    }

    public LocalDate getFechaClase() {
        return fechaClase;
    }

    public void setFechaClase(LocalDate fechaClase) {
        this.fechaClase = fechaClase;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
