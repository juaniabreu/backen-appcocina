package desarrllo_aplicaciones.tp.Controller.cursos;


import desarrllo_aplicaciones.tp.model.Cursos.Curso;
import desarrllo_aplicaciones.tp.model.Cursos.Sede;
import desarrllo_aplicaciones.tp.services.Cursos.SedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/sede")
public class SedeCotroller {

    @Autowired
    SedeService sedeService;

    @GetMapping
    public ResponseEntity<List<Sede>> getAllSedes() {
        return ResponseEntity.ok(sedeService.findAll());
    }

    // GET /sedes/{id}: detalle de una sede
    @GetMapping("/{id}")
    public ResponseEntity<Sede> getSedeById(@PathVariable Long id) {
        Optional<Sede> sede = sedeService.findById(id);
        if (sede.isPresent()) {
            return ResponseEntity.ok(sede.get());
        }
        return ResponseEntity.notFound().build();
    }

    // GET /sedes/{id}/cursos: cursos de una sede
    @GetMapping("/{id}/cursos")
    public ResponseEntity<List<Curso>> getCursosDeSede(@PathVariable Long id) {
        Optional<Sede> sede = sedeService.findById(id);
        if (sede.isPresent()) {
            return ResponseEntity.ok(sede.get().getCursos());
        }
        return ResponseEntity.notFound().build();
    }

    // POST /sedes: crear nueva sede
    @PostMapping
    public ResponseEntity<Sede> createSede(@RequestBody Sede sede) {
        return ResponseEntity.ok(sedeService.save(sede));
    }

    // PUT /sedes/{id}: editar sede existente
    @PutMapping("/{id}")
    public ResponseEntity<Sede> updateSede(@RequestBody Sede sede) {
        return ResponseEntity.ok(sedeService.update(sede));
    }

    // DELETE /sedes/{id}: eliminar sede
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSede(@PathVariable Long id) {
        sedeService.deleteSedeById(id);
        return ResponseEntity.noContent().build();
    }
}

