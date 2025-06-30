package desarrllo_aplicaciones.tp.Controller.Recetas.dto;

import desarrllo_aplicaciones.tp.Controller.Recetas.dto.AutorDTO;
import desarrllo_aplicaciones.tp.model.Recetas.IngredienteReceta;
import desarrllo_aplicaciones.tp.model.Recetas.PasoReceta;
import desarrllo_aplicaciones.tp.model.Recetas.Valoracion;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class RecetaDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private int porciones;
    private String tipo;
    private List<String> fotosPlato;

    private AutorDTO autor;

    private List<IngredienteReceta> ingredientes;
    private List<PasoReceta> pasos;
    private List<Valoracion> valoraciones;

    private boolean aprobada;
    private LocalDateTime fechaCreacion;

    // Getters y setters

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPorciones() {
        return porciones;
    }

    public void setPorciones(int porciones) {
        this.porciones = porciones;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<String> getFotosPlato() {
        return fotosPlato;
    }

    public void setFotosPlato(List<String> fotosPlato) {
        this.fotosPlato = fotosPlato;
    }

    public AutorDTO getAutor() {
        return autor;
    }

    public void setAutor(AutorDTO autor) {
        this.autor = autor;
    }

    public List<IngredienteReceta> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteReceta> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<PasoReceta> getPasos() {
        return pasos;
    }

    public void setPasos(List<PasoReceta> pasos) {
        this.pasos = pasos;
    }

    public List<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
