package desarrllo_aplicaciones.tp.Controller.cursos;
import desarrllo_aplicaciones.tp.model.Cursos.Asistencia;
import desarrllo_aplicaciones.tp.Controller.cursos.dto.DetalleCursoInscriptoDTO;
import desarrllo_aplicaciones.tp.Controller.cursos.dto.EstadoPagoDTO;
import desarrllo_aplicaciones.tp.model.Cursos.CursoSede;
import desarrllo_aplicaciones.tp.model.Cursos.InscripcionCurso;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import desarrllo_aplicaciones.tp.repository.Cursos.InscripcionCursoRepository;
import desarrllo_aplicaciones.tp.services.Cursos.CursoService;
import desarrllo_aplicaciones.tp.services.Cursos.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
public class InscripcionCursoController {
    @Autowired
    private InscripcionService inscripcionService;
    @Autowired
    private InscripcionCursoRepository inscripcionRepo;

    @GetMapping("/usuario/{usuarioId}/estado")
    public ResponseEntity<List<InscripcionCurso>> inscripcionesPorEstado(
            @PathVariable Long usuarioId,
            @RequestParam(defaultValue = "todos") String tipo) {

        List<InscripcionCurso> todas = inscripcionService.obtenerInscripcionesPorAlumno(usuarioId);

        LocalDate hoy = LocalDate.now();

        List<InscripcionCurso> filtradas = switch (tipo.toLowerCase()) {
            case "activos" -> todas.stream()
                    .filter(i -> !hoy.isBefore(i.getCursoSede().getFechaInicio()) &&
                            !hoy.isAfter(i.getCursoSede().getFechaFin()))
                    .toList();
            case "futuros" -> todas.stream()
                    .filter(i -> hoy.isBefore(i.getCursoSede().getFechaInicio()))
                    .toList();
            case "finalizados" -> todas.stream()
                    .filter(i -> hoy.isAfter(i.getCursoSede().getFechaFin()))
                    .toList();
            default -> todas; // "todos"
        };

        return ResponseEntity.ok(filtradas);
    }

    @PatchMapping("/{inscripcionId}/cancelar")
    public ResponseEntity<String> cancelarInscripcion(@PathVariable Long inscripcionId) {
        return ResponseEntity.ok(inscripcionService.cancelarInscripcion(inscripcionId));
    }


    @GetMapping("/{inscripcionId}/estado")
    public ResponseEntity<EstadoPagoDTO> estadoPago(@PathVariable Long inscripcionId) {
        InscripcionCurso i = inscripcionRepo.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        EstadoPagoDTO dto = new EstadoPagoDTO();
        dto.setInscripcionId(i.getId());
        dto.setCurso(i.getCursoSede().getCurso().getNombre());
        dto.setSede(i.getCursoSede().getSede().getNombre());
        dto.setMontoPagado(i.getMontoPagado());
        dto.setEstadoPago(i.getEstadoPago().name());

        return ResponseEntity.ok(dto);
    }
    @PostMapping("/{cursoSedeId}/usuario/{usuarioId}")
    public ResponseEntity<?> inscribirse(
            @PathVariable Long cursoSedeId,
            @PathVariable Long usuarioId) {
        try {
            InscripcionCurso inscripcion = inscripcionService.inscribirAlumnoPorId(usuarioId, cursoSedeId);
            return ResponseEntity.ok(inscripcion);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<InscripcionCurso> misInscripciones(@PathVariable Long usuarioId) {
        return inscripcionService.obtenerInscripcionesPorAlumno(usuarioId);
    }
    @GetMapping("/{inscripcionId}/detalle")
    public ResponseEntity<DetalleCursoInscriptoDTO> detalleCursoInscripto(@PathVariable Long inscripcionId) {
        InscripcionCurso inscripcion = inscripcionService
                .obtenerInscripcionPorId(inscripcionId); // ahora agregamos este método

        CursoSede cursoSede = inscripcion.getCursoSede();

        List<LocalDate> fechasAsistidas = inscripcion.getAsistencias().stream()
                .filter(Asistencia::isPresente)
                .map(Asistencia::getFechaClase)
                .toList();

        DetalleCursoInscriptoDTO dto = new DetalleCursoInscriptoDTO(
                cursoSede.getCurso().getNombre(),
                cursoSede.getSede().getNombre(),
                cursoSede.getHorario(),
                cursoSede.getFechaInicio(),
                cursoSede.getFechaFin(),
                inscripcion.getMontoPagado(),
                inscripcion.getEstadoPago().name(),
                cursoSede.getRequisitosInsumos(),
                cursoSede.getRequisitosUtensilios(),
                fechasAsistidas
        );

        return ResponseEntity.ok(dto);
    }
}
