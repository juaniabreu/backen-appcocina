package desarrllo_aplicaciones.tp.model.Recetas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Lob
    private String descripcion;

    @Column(nullable = false)
    private int porciones;

    private String tipo; // Ej: "pasta", "ensalada"

    @ElementCollection
    private List<String> fotosPlato; // URLs o nombres de archivo

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario autor;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<IngredienteReceta> ingredientes;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @JsonBackReference
    private List<PasoReceta> pasos;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Valoracion> valoraciones;

    public List<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }

    private boolean aprobada;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

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

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
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

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }
    public void aprobar(){
        this.aprobada =true;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
