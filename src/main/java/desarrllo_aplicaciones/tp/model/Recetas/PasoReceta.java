package desarrllo_aplicaciones.tp.model.Recetas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter@NoArgsConstructor
@AllArgsConstructor
public class PasoReceta {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getArchivosMultimedia() {
        return archivosMultimedia;
    }

    public void setArchivosMultimedia(List<String> archivosMultimedia) {
        this.archivosMultimedia = archivosMultimedia;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int orden;

    @Lob
    private String descripcion;

    @ElementCollection
    private List<String> archivosMultimedia; // links a fotos/videos

    @ManyToOne
    @JoinColumn(name = "receta_id")
    @JsonIgnore
    private Receta receta;
}
