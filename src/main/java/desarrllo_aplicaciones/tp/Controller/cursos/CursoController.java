package desarrllo_aplicaciones.tp.Controller.cursos;

import desarrllo_aplicaciones.tp.model.Cursos.Curso;
import desarrllo_aplicaciones.tp.model.Cursos.Sede;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import desarrllo_aplicaciones.tp.repository.Cursos.CursoRepository;
import desarrllo_aplicaciones.tp.services.Cursos.CursoService;
import desarrllo_aplicaciones.tp.services.Cursos.SedeService;
import desarrllo_aplicaciones.tp.services.CustomUserDetailsService;
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
@CrossOrigin("*")
@RequestMapping("api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;
    @Autowired
    private SedeService sedeService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * GET /cursos: listar todos los cursos (solo nombre y breve descripción si es visitante/usuario) x
     *
     * GET /cursos/{id}: obtener detalles completos del curso (solo alumno) x
     *
     * GET /cursos/disponibles: cursos activos por sede y modalidad x
     *
     * POST /cursos: crear curso (admin) x
     *
     * PUT /cursos/{id}: editar curso (admin) x
     *
     * DELETE /cursos/{id}: eliminar curso (admin) x */



    // 🔹 GET /cursos → listar todos
    @GetMapping
    public ResponseEntity<List<Curso>> getAllCursos() {
        return ResponseEntity.ok(cursoService.getAllCursos());
    }

    // 🔹 GET /cursos/{id} → buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Curso> getCursoById(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.getCursoById(id);
        if (curso.isPresent()) {
            return ResponseEntity.ok(curso.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Curso>> getCursoPorNombreSedeModalidad(
            @RequestParam String nombre,
            @RequestParam Long sedeId,
            @RequestParam String modalidad) {
        LocalDate hoy = LocalDate.now();
        List<Curso> cursosDisponibles = new ArrayList();
        List<Curso> cursos = cursoService.getAllCursos();
        for (Curso curso : cursos) {
            if (hoy.isBefore(curso.getFechaInicio())) {
                cursosDisponibles.add(curso);
            }
        }
        return ResponseEntity.ok(cursosDisponibles);
    }
    @GetMapping("/{cursoId}/sedes")
    public ResponseEntity<List<Sede>> getCursoPorSede(@PathVariable Long cursoId) {
        Optional<Curso> curso = cursoService.getCursoById(cursoId);
        if (curso.isPresent()) {
            return ResponseEntity.ok(curso.get().getSedes());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Curso> createCurso(@RequestBody Curso curso) {
        List<Sede> sedes = new ArrayList();
        for (Sede sede : curso.getSedes()) {
            Optional<Sede> sedeEncontrada = sedeService.findById(sede.getId());
            if (sedeEncontrada.isPresent()) {
                sedes.add(sedeEncontrada.get());
            }
        }
        curso.setSedes(sedes);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.createCurso(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> updateCurso(@PathVariable Long id, @RequestBody Curso curso) {
        curso.setId(id);
        return ResponseEntity.ok(cursoService.updateCurso(curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.getCursoById(id);
        if (curso.isPresent()) {
            cursoService.deleteCurso(curso.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}/cursosUsuario")
    public ResponseEntity<List<Curso>> getCursosUsuario(@PathVariable Long id) {
        Usuario usuario= customUserDetailsService.findById(id);
        return ResponseEntity.ok().body(usuario.getCursosInscriptos());
    }
    @PostMapping("/{idCurso}/inscribirse")
    public ResponseEntity<?> inscribirseCurso(
            @PathVariable Long idCurso,
            @RequestParam Long idUsuario) {
        try {
            Curso curso = cursoService.inscribirAlumno(idCurso, idUsuario);
            return ResponseEntity.ok("Inscripción exitosa al curso: " + curso.getNombre());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
