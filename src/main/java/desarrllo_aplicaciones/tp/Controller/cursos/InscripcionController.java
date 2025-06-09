package desarrllo_aplicaciones.tp.Controller.cursos;

import desarrllo_aplicaciones.tp.model.Cursos.Inscripcion;
import desarrllo_aplicaciones.tp.services.Cursos.InscripcionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/cursos/inscripcion")
public class InscripcionController {

    @Autowired
    private InscripcionesService inscripcionService;

    // POST /cursos/inscripcion?cursoId=1&usuarioId=2
    @PostMapping
    public ResponseEntity<?> inscribirse(
            @RequestParam Long cursoId,
            @RequestParam Long usuarioId) {
        return ResponseEntity.ok(inscripcionService.inscribirse(cursoId, usuarioId));
    }

    // PUT /cursos/inscripcion/baja?id=5&fecha=2025-05-30
    @PutMapping("/baja")
    public ResponseEntity<?> baja(
            @RequestParam Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(inscripcionService.baja(id, fecha));
    }

    // GET /mis-cursos?usuarioId=2
    @GetMapping("/mis-cursos")
    public List<Inscripcion> cursosDelUsuario(@RequestParam Long usuarioId) {
        return inscripcionService.cursosDelUsuario(usuarioId);
    }
}
