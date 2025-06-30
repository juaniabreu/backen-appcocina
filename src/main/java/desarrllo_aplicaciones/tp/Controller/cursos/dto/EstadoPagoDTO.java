package desarrllo_aplicaciones.tp.Controller.cursos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class EstadoPagoDTO {
    private Long inscripcionId;
    private String curso;
    private String sede;
    private double montoPagado;
    private String estadoPago;

    public Long getInscripcionId() {
        return inscripcionId;
    }

    public void setInscripcionId(Long inscripcionId) {
        this.inscripcionId = inscripcionId;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public EstadoPagoDTO() {
    }

    public EstadoPagoDTO(Long inscripcionId, String curso, String sede, double montoPagado, String estadoPago) {
        this.inscripcionId = inscripcionId;
        this.curso = curso;
        this.sede = sede;
        this.montoPagado = montoPagado;
        this.estadoPago = estadoPago;
    }
}
