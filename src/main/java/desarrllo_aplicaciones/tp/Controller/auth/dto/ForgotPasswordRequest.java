package desarrllo_aplicaciones.tp.Controller.auth.dto;

// DTO para forgot password (solo el email)
public class ForgotPasswordRequest {
    private String email;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

