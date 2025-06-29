package desarrllo_aplicaciones.tp.Controller.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse() {
    }

    public String getToken() {
        return token;
    }
}