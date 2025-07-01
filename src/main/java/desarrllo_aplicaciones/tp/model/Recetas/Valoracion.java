package desarrllo_aplicaciones.tp.model.Recetas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

    @Entity
    @Getter
    @Setter
    public class Valoracion {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "receta_id", nullable = false)
        @JsonBackReference("receta-valoraciones")
        private Receta receta;


        @ManyToOne(cascade = CascadeType.ALL)
        @JsonBackReference("usuario-valoraciones")
        private Usuario usuario;

        @Column(nullable = false)
        private int puntaje; // de 1 a 5

        @Lob
        private String comentario;

        private boolean aprobado;

        private LocalDateTime fecha = LocalDateTime.now();

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Receta getReceta() {
            return receta;
        }

        public void setReceta(Receta receta) {
            this.receta = receta;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }
    public  void aprobar(){
            this.aprobado= true;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

        @Override
        public String toString() {
            return super.toString();
        }
    }
