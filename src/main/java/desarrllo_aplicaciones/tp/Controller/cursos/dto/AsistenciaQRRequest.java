package desarrllo_aplicaciones.tp.Controller.cursos.dto;

import java.time.LocalDate;

public class AsistenciaQRRequest {
    private Long cursoSedeId;
    private LocalDate fechaClase;
    private String token;

    public Long getCursoSedeId() {
        return cursoSedeId;
    }

    public void setCursoSedeId(Long cursoSedeId) {
        this.cursoSedeId = cursoSedeId;
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
}
