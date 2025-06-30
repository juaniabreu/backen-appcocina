package desarrllo_aplicaciones.tp.Controller.cursos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class DetalleCursoInscriptoDTO {
    private String curso;
    private String sede;
    private String horario;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double monto;
    private String estadoPago;
    private String insumos;
    private String utensilios;
    private List<LocalDate> asistencias;

    public DetalleCursoInscriptoDTO() {
    }

    public DetalleCursoInscriptoDTO(String curso, String sede, String horario, LocalDate fechaInicio, LocalDate fechaFin, double monto, String estadoPago, String insumos, String utensilios, List<LocalDate> asistencias) {
        this.curso = curso;
        this.sede = sede;
        this.horario = horario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.monto = monto;
        this.estadoPago = estadoPago;
        this.insumos = insumos;
        this.utensilios = utensilios;
        this.asistencias = asistencias;
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

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getInsumos() {
        return insumos;
    }

    public void setInsumos(String insumos) {
        this.insumos = insumos;
    }

    public String getUtensilios() {
        return utensilios;
    }

    public void setUtensilios(String utensilios) {
        this.utensilios = utensilios;
    }

    public List<LocalDate> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<LocalDate> asistencias) {
        this.asistencias = asistencias;
    }
}
