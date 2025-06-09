package desarrllo_aplicaciones.tp.Controller.Recetas;

import desarrllo_aplicaciones.tp.model.Recetas.*;
import desarrllo_aplicaciones.tp.services.Recetas.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @GetMapping
    public List<Receta> listarTodas() {
        return recetaService.obtenerRecetasAprobadas();
    }

    @GetMapping("/recientes")
    public List<Receta> listarRecientes() {
        return recetaService.obtenerRecetasRecientes();
    }
    @GetMapping("/buscar")
    public List<Receta> buscarPorNombre(@RequestParam String nombre) {
        return recetaService.buscarPorNombre(nombre);
    }

    @GetMapping("/tipo")
    public List<Receta> buscarPorTipo(@RequestParam String tipo) {
        return recetaService.buscarPorTipo(tipo);
    }

    @GetMapping("/usuario/{id}")
    public List<Receta> porUsuario(@PathVariable Long id) {
        return recetaService.recetasPorUsuario(id);
    }

    @PostMapping
    public ResponseEntity<Receta> crearReceta(@RequestBody Receta receta) {
        return ResponseEntity.ok(recetaService.guardarReceta(receta));
    }

    @GetMapping("/{id}/ingredientes")
    public List<IngredienteReceta> ingredientes(@PathVariable Long id) {
        return recetaService.obtenerIngredientes(id);
    }

    @GetMapping("/{id}/pasos")
    public List<PasoReceta> pasos(@PathVariable Long id) {
        return recetaService.obtenerPasos(id);
    }

    @PostMapping("/{id}/valorar")
    public ResponseEntity<Valoracion> valorar(
            @PathVariable Long id,
            @RequestParam Long usuarioId,
            @RequestParam int puntaje,
            @RequestParam(required = false) String comentario) {
        return ResponseEntity.ok(recetaService.valorar(id, usuarioId, puntaje, comentario));
    }

    @GetMapping("/{id}/valoraciones")
    public List<Valoracion> valoraciones(@PathVariable Long id) {
        return recetaService.obtenerValoracionesAprobadas(id);
    }

    @GetMapping("/ingrediente")
    public List<Receta> buscarPorIngrediente(@RequestParam String nombre) {
        // Este método debe implementarse en el service con un query custom o lógica interna
        return recetaService.buscarPorIngrediente(nombre);
    }


    @PostMapping("/filtroavanzado")
    public List<Receta> buscarPorIngredientes(@RequestBody List<String> ingredientes) {
        return recetaService.buscarPorIngredientes(ingredientes);
    }
}