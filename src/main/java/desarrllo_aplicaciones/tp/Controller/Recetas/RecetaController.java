package desarrllo_aplicaciones.tp.Controller.Recetas;

import desarrllo_aplicaciones.tp.Controller.Recetas.dto.AutorDTO;
import desarrllo_aplicaciones.tp.Controller.Recetas.dto.RecetaDTO;
import desarrllo_aplicaciones.tp.Controller.Recetas.dto.ValoracionDTO;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import desarrllo_aplicaciones.tp.model.Recetas.*;
import desarrllo_aplicaciones.tp.repository.Recetas.RecetaRepository;
import desarrllo_aplicaciones.tp.services.Cursos.CursoService;
import desarrllo_aplicaciones.tp.services.CustomUserDetailsService;
import desarrllo_aplicaciones.tp.services.Recetas.RecetaService;
import desarrllo_aplicaciones.tp.services.Recetas.ValoracionService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private ValoracionService valoracionService;
    @Autowired
    private RecetaRepository recetaRepository;

/*

Endpoints

GET /recetas: listar todas las recetas visibles (filtradas por tipo, nombre, ingredientes, etc.) x

GET /recetas/{id}: detalle de receta x

POST /recetas: crear receta (revisada antes de publicarse) x

PUT /recetas/{id}: modificar receta existente x

DELETE /recetas/{id}: eliminar x

POST /recetas/{id}/valorar: puntuar y comentar receta x

GET /recetas/usuario/{idUsuario}: recetas de un usuario x

GET /recetas/noIngrediente?nombre=harina: recetas sin cierto ingrediente ?????**/


    @GetMapping
    public ResponseEntity<List<RecetaDTO>> listarTodas() {
        List<Receta> recetas = recetaService.obtenerRecetasAprobadas();

        List<RecetaDTO> dtos = recetas.stream().map(receta -> {
            AutorDTO autorDTO = new AutorDTO();
            autorDTO.setId(receta.getAutor().getId());
            autorDTO.setNombre(receta.getAutor().getNombre());
            autorDTO.setUsername(receta.getAutor().getUsername());

            RecetaDTO dto = new RecetaDTO();
            dto.setId(receta.getId());
            dto.setNombre(receta.getNombre());
            dto.setDescripcion(receta.getDescripcion());
            dto.setPorciones(receta.getPorciones());
            dto.setTipo(receta.getTipo());
            dto.setFotosPlato(receta.getFotosPlato());
            dto.setAutor(autorDTO);
            dto.setPasos(receta.getPasos());
            dto.setValoraciones(receta.getValoraciones());
            dto.setIngredientes(receta.getIngredientes());
            dto.setFechaCreacion(receta.getFechaCreacion());
            dto.setAprobada(receta.isAprobada());
            return dto;
        }).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/recientes")
    public List<Receta> listarRecientes() {
        return recetaService.obtenerRecetasRecientes();
    }

    @GetMapping("/recetas/{id}")
    public ResponseEntity<Optional<Receta>> buscarById(@PathVariable Long id) {
        Optional<Receta> receta = recetaService.findbyID(id);
        if (receta.isPresent()) {
            return ResponseEntity.ok(receta);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<Receta> buscarPorNombre(@RequestParam String nombre) {
        Optional<Receta> receta = recetaService.buscarPorNombreReceta(nombre);
        if (receta.isPresent()) {
            return ResponseEntity.ok(receta.get());

        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/editar")
    public ResponseEntity<Receta> editarReceta(@RequestBody Receta receta) {
        recetaService.guardarReceta(receta);
        return ResponseEntity.ok(recetaService.guardarReceta(receta));
    }


    @GetMapping("/usuario/{idUsuario}")
    public List<Receta> buscarPorUsuario(@PathVariable Long idUsuario) {
        return recetaService.recetasPorUsuario(idUsuario);
    }

    @GetMapping("/usuario/{id}")
    public List<Receta> porUsuario(@PathVariable Long id) {
        return recetaService.recetasPorUsuario(id);
    }

    @DeleteMapping("/recetas/{id}")
    public ResponseEntity<String> eliminarReceta(@PathVariable Long id) {
        recetaService.borrarReceta(id);
        return ResponseEntity.accepted().build();
    }
    @PermitAll
    @PostMapping("/crear")
    public ResponseEntity<Receta> crearReceta(@RequestBody Receta receta) {
        Optional<Receta> recetaOptional = recetaService.buscarPorNombreUsuario(receta.getNombre(), receta.getAutor().getId());
        if (recetaOptional.isPresent()) {
            Receta receta1 = recetaOptional.get();
            receta1.getIngredientes().size(); // Fuerza la carga
            receta1.getPasos().size();        // Fuerza la carga
            receta1.getValoraciones().size();

            return ResponseEntity.status(HttpStatus.CONFLICT).body(receta1);
        }
        Usuario user = userDetailsService.findById(receta.getAutor().getId());
        receta.setAutor(user);
        receta.setAprobada(false);
        for (IngredienteReceta ing : receta.getIngredientes()) {
            ing.setReceta(receta);
        }
        for (PasoReceta paso : receta.getPasos()) {
            paso.setReceta(receta);
        }
        return ResponseEntity.ok(recetaService.guardarReceta(receta));
    }

    @GetMapping("/{id}/obtenerGuardadas")
    public ResponseEntity<List<Receta>> ObtenerRectasGuardadas(@PathVariable Long id) {
        Usuario usuario = userDetailsService.findById(id);
        return ResponseEntity.ok().body(usuario.getListaRecetasGuardadas());
    }

    @PutMapping("/{id}/guardarEnUsuario")
    public ResponseEntity<List<Receta>> guardarRecetaEnUsuario(@PathVariable Long id, @RequestBody Long idUsuario) {
        Usuario usuario = userDetailsService.findById(idUsuario);
        List<Receta> lista = usuario.getListaRecetasGuardadas();
        Optional<Receta> receta = recetaService.findbyID(id);
        if (receta.isPresent()) {
            lista.add(receta.get());
            return ResponseEntity.ok().body(lista);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/guardar")
    public ResponseEntity<List<Receta>> eliminarRecetaGuardada(@PathVariable Long id, @RequestBody Long idUsuario) {
        Usuario usuario = userDetailsService.findById(idUsuario);
        List<Receta> lista = usuario.getListaRecetasGuardadas();
        Optional<Receta> receta = recetaService.findbyID(id);
        if (receta.isPresent()) {
            lista.remove(receta.get());
            return ResponseEntity.ok().body(lista);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/misRecetas")
    public List<Receta> misRecetasCreadas(@PathVariable Long id) {
        return recetaService.recetasPorUsuario(id);
    }

    @PostMapping("/{id}/valorar")
    public ResponseEntity<Valoracion> valorar(@RequestBody ValoracionDTO valoracionDTO, @PathVariable Long id) {
        Usuario usuario = userDetailsService.findById(valoracionDTO.getUsuarioId());
        Optional<Receta> receta = recetaService.findbyID(id);
        if (receta.isPresent()) {
            Receta recetaGet = receta.get();
            Valoracion valoracion = new Valoracion();
            valoracion.setUsuario(usuario);
            valoracion.setAprobado(false);
            valoracion.setReceta(receta.get());
            valoracion.setComentario(valoracionDTO.getComentario());
            valoracion.setPuntaje(valoracionDTO.getPuntaje());
            recetaGet.getValoraciones().add(valoracion);
            recetaService.guardarReceta(recetaGet);
            return ResponseEntity.ok(valoracion);
             }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}/valoraciones")
    public List<Valoracion> valoraciones(@PathVariable Long id) {
        return recetaService.obtenerValoracionesAprobadas(id);
    }


    @PutMapping("/aprobarReceta/{id}")
    public ResponseEntity<Receta> aprobarReceta(@PathVariable Long id) {
        Optional<Receta> recetaOptional = recetaService.findbyID(id);
        if (recetaOptional.isPresent()) {
            Receta receta = recetaOptional.get();
            receta.aprobar();
            System.out.println(receta.isAprobada());

            return ResponseEntity.ok(recetaService.guardarReceta(receta));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/aprobarComentaio/{id}")
    public ResponseEntity<Valoracion> aprobarComentario(@PathVariable Long id) {
        Optional<Valoracion> valoracionOptional = valoracionService.findValoracionById(id);
        if (valoracionOptional.isPresent()) {
            Valoracion valoracion = valoracionOptional.get();
            valoracion.aprobar();
            System.out.println(valoracion.isAprobado());
            return ResponseEntity.ok().body(valoracionService.guardarValoracion(valoracion));
        }
        return ResponseEntity.notFound().build();
    }
}