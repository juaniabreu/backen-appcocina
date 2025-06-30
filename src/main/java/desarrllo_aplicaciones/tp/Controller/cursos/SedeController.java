package desarrllo_aplicaciones.tp.Controller.cursos;


import desarrllo_aplicaciones.tp.model.Cursos.Curso;
import desarrllo_aplicaciones.tp.model.Cursos.Sede;
import desarrllo_aplicaciones.tp.repository.Cursos.SedeRepository;
import desarrllo_aplicaciones.tp.services.Cursos.SedeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sedes")
@RequiredArgsConstructor
public class SedeController {

    @Autowired
    private  SedeRepository sedeRepo;

    // Crear una sede
    @PostMapping
    public ResponseEntity<Sede> crear(@RequestBody Sede sede) {
        Sede nueva = sedeRepo.save(sede);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // Listar todas las sedes
    @GetMapping
    public List<Sede> listarTodas() {
        return sedeRepo.findAll();
    }

    // Buscar sede por ID
    @GetMapping("/{id}")
    public ResponseEntity<Sede> buscarPorId(@PathVariable Long id) {
        return sedeRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

