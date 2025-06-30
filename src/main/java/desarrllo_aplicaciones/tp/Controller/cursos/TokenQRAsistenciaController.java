package desarrllo_aplicaciones.tp.Controller.cursos;


import desarrllo_aplicaciones.tp.services.Cursos.AsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/asistencia")
@RequiredArgsConstructor
public class TokenQRAsistenciaController {
    @Autowired
    private AsistenciaService asistenciaService;


    // Registrar asistencia desde QR escaneado
    @PostMapping("/registrar/qr")
    public ResponseEntity<String> registrarDesdeQR(
            @RequestParam Long usuarioId,
            @RequestBody String qrEncoded) {

        String decoded = new String(java.util.Base64.getDecoder().decode(qrEncoded));
        String[] partes = decoded.split(";");

        if (partes.length != 3)
            return ResponseEntity.badRequest().body("Formato de QR inválido");

        Long cursoSedeId = Long.parseLong(partes[0]);
        LocalDate fecha = LocalDate.parse(partes[1]);
        String token = partes[2];

        asistenciaService.registrarAsistenciaDesdeQR(usuarioId, cursoSedeId, fecha, token);
        return ResponseEntity.ok("Asistencia registrada correctamente");
    }
}