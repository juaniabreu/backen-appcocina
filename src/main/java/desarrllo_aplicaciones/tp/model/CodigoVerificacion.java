package desarrllo_aplicaciones.tp.model;

import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class CodigoVerificacion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A qué usuario corresponde este código
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario usuario;

    // El código de verificación (puede ser numérico o string)
    private String code;

    // Cuándo expira el código
    private LocalDateTime expiresAt;

    // Si ya fue usado o no (true = usado → ya no es válido)
    private boolean used = false;

    // Cuándo se generó el código
    private LocalDateTime createdAt = LocalDateTime.now();

    public CodigoVerificacion() {
    }

    @Override
    public String toString() {
        return "Codigo: "+ this.code + " usuario: "+ this.usuario.getUsername();
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
