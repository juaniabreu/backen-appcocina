package desarrllo_aplicaciones.tp.Controller.cursos;

import desarrllo_aplicaciones.tp.Controller.cursos.dto.AsistenciaQRRequest;
import desarrllo_aplicaciones.tp.model.Cursos.Asistencia;
import desarrllo_aplicaciones.tp.model.Cursos.CursoSede;
import desarrllo_aplicaciones.tp.model.Cursos.TokenQRAsistencia;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import desarrllo_aplicaciones.tp.repository.Cursos.CursoRepository;
import desarrllo_aplicaciones.tp.repository.Cursos.CursoSedeRepository;
import desarrllo_aplicaciones.tp.repository.Cursos.TokenQRAsistenciaRepository;
import desarrllo_aplicaciones.tp.services.Cursos.AsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/asistencia")
@RequiredArgsConstructor
public class AsistenciaController {
    @Autowired
    private AsistenciaService asistenciaService;
    @Autowired
    private CursoSedeRepository cursoSedeRepo;
    @Autowired
    private TokenQRAsistenciaRepository qrRepo;



    @GetMapping("/porcentaje/{inscripcionId}")
    public ResponseEntity<Double> porcentaje(
            @PathVariable Long inscripcionId) {
        return ResponseEntity.ok(
                asistenciaService.calcularPorcentajeAsistenciaById(inscripcionId)
        );
    }

    @GetMapping("/qr/{cursoSedeId}/{fecha}")
    public ResponseEntity<Map<String, String>> generarQR(
            @PathVariable Long cursoSedeId,
            @PathVariable String fecha) {

        CursoSede cursoSede = cursoSedeRepo.findById(cursoSedeId)
                .orElseThrow(() -> new RuntimeException("CursoSede no encontrado"));
        LocalDate fechaClase = LocalDate.parse(fecha);

        TokenQRAsistencia qr = qrRepo.findByCursoSedeAndFechaClase(cursoSede, fechaClase)
                .orElseGet(() -> {
                    TokenQRAsistencia nuevo = new TokenQRAsistencia();
                    nuevo.setCursoSede(cursoSede);
                    nuevo.setFechaClase(fechaClase);
                    nuevo.setToken(UUID.randomUUID().toString());
                    return qrRepo.save(nuevo);
                });

        String payload = cursoSedeId + ";" + fecha + ";" + qr.getToken();
        String base64 = Base64.getEncoder().encodeToString(payload.getBytes());

        return ResponseEntity.ok(Map.of("qr", base64));
    }


}

