package desarrllo_aplicaciones.tp.Controller.cursos;

import desarrllo_aplicaciones.tp.model.Cursos.CursoSede;
import desarrllo_aplicaciones.tp.model.Cursos.Sede;
import desarrllo_aplicaciones.tp.repository.Cursos.CursoSedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
    @RequestMapping("/api/curso-sede")
@RequiredArgsConstructor
public class CursoSedeController {
    @Autowired
    private CursoSedeRepository cursoSedeRepo;

    // GET: listar todas las instancias
    @GetMapping
    public List<CursoSede> listarTodos() {
        return cursoSedeRepo.findAll();
    }

    // GET: por sede
    @GetMapping("/sede/{sedeId}")
    public List<CursoSede> porSede(@PathVariable Long sedeId) {
        return cursoSedeRepo.findBySedeId(sedeId);
    }

    // GET: por curso
    @GetMapping("/curso/{cursoId}")
    public List<CursoSede> porCurso(@PathVariable Long cursoId) {

        return cursoSedeRepo.findByCursoId(cursoId);
    }

    // GET: por ID
    @GetMapping("/{id}")
    public ResponseEntity<CursoSede> obtenerPorId(@PathVariable Long id) {
        return cursoSedeRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: crear instancia curso-sede
    @PostMapping
    public ResponseEntity<CursoSede> crear(@RequestBody CursoSede cursoSede) {
        CursoSede creado = cursoSedeRepo.save(cursoSede);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // DELETE: eliminar una instancia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!cursoSedeRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cursoSedeRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/curso/{cursoId}/sedes")
    public ResponseEntity<List<Sede>> listarSedesPorCurso(@PathVariable Long cursoId) {
        List<Sede> sedes = cursoSedeRepo.findSedesByCursoId(cursoId);
        return ResponseEntity.ok(sedes);
    }
}
