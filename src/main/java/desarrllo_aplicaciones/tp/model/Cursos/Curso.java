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
@NoArgsConstructor @AllArgsConstructor
public class Curso {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nombre;
        private String descripcionCorta;
        private String foto;

        @Lob
        private String descripcionLarga;

        @Enumerated(EnumType.STRING)
        private Modalidad modalidad; // PRESENCIAL, VIRTUAL, ONLINE

        @ElementCollection
        private List<String> temas;

        @ElementCollection
        private List<String> practicas;

        private boolean requiereInsumos;
        private boolean requiereUtensilios;

        @OneToMany(mappedBy = "curso")
        @JsonIgnore
        private List<CursoSede> cursoSedes;

        public enum Modalidad {
            PRESENCIAL, VIRTUAL, ONLINE
        }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public List<String> getTemas() {
        return temas;
    }

    public void setTemas(List<String> temas) {
        this.temas = temas;
    }

    public List<String> getPracticas() {
        return practicas;
    }

    public void setPracticas(List<String> practicas) {
        this.practicas = practicas;
    }

    public boolean isRequiereInsumos() {
        return requiereInsumos;
    }

    public void setRequiereInsumos(boolean requiereInsumos) {
        this.requiereInsumos = requiereInsumos;
    }

    public boolean isRequiereUtensilios() {
        return requiereUtensilios;
    }

    public void setRequiereUtensilios(boolean requiereUtensilios) {
        this.requiereUtensilios = requiereUtensilios;
    }

    public List<CursoSede> getCursoSedes() {
        return cursoSedes;
    }

    public void setCursoSedes(List<CursoSede> cursoSedes) {
        this.cursoSedes = cursoSedes;
    }
}

