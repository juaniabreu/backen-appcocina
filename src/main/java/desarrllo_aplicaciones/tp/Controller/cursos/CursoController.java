package desarrllo_aplicaciones.tp.Controller.cursos;

import desarrllo_aplicaciones.tp.model.Cursos.Curso;
import desarrllo_aplicaciones.tp.model.Cursos.InscripcionCurso;
import desarrllo_aplicaciones.tp.model.Cursos.Sede;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import desarrllo_aplicaciones.tp.repository.Cursos.CursoRepository;
import desarrllo_aplicaciones.tp.repository.Cursos.InscripcionCursoRepository;
import desarrllo_aplicaciones.tp.services.Cursos.CursoService;
import desarrllo_aplicaciones.tp.services.Cursos.InscripcionService;
import desarrllo_aplicaciones.tp.services.Cursos.SedeService;
import desarrllo_aplicaciones.tp.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {
    @Autowired
    private  CursoService cursoService;

    @Autowired
    private InscripcionCursoRepository inscripcionRepo;
    @GetMapping
    public List<Curso> listarTodos() {
        return cursoService.listarTodos();
    }


    @PostMapping
    public ResponseEntity<Curso> crearCurso(@RequestBody Curso curso) {
        Curso creado = cursoService.crearCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    @GetMapping("/usuario/{usuarioId}/estado")
    public List<InscripcionCurso> porEstado(@PathVariable Long usuarioId, @RequestParam String tipo) {
        List<InscripcionCurso> todas = inscripcionRepo.findByAlumnoId(usuarioId);
        LocalDate hoy = LocalDate.now();

        return switch (tipo.toLowerCase()) {
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
            default -> todas;
        };
    }


    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerPorId(@PathVariable Long id) {
        return cursoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/modalidad/{modalidad}")
    public List<Curso> filtrarPorModalidad(@PathVariable Curso.Modalidad modalidad) {
        return cursoService.filtrarPorModalidad(modalidad);
    }
}
