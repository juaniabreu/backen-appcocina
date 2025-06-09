package desarrllo_aplicaciones.tp.Controller.cursos;

import desarrllo_aplicaciones.tp.model.Cursos.Asistencia;
import desarrllo_aplicaciones.tp.services.Cursos.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos/asistencia")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping
    public ResponseEntity<?> registrarAsistencia(
            @RequestParam Long usuarioId,
            @RequestParam Long cursoId) {
        return ResponseEntity.ok(asistenciaService.registrarAsistencia(usuarioId, cursoId));
    }
    @GetMapping
    public List<Asistencia> obtenerAsistencias(@RequestParam Long usuarioId) {
        return asistenciaService.obtenerAsistencias(usuarioId);
    }
}
