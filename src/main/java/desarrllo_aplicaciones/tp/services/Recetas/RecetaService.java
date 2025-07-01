package desarrllo_aplicaciones.tp.services.Recetas;

import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import desarrllo_aplicaciones.tp.model.Recetas.*;
import desarrllo_aplicaciones.tp.repository.Recetas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepo;

    @Autowired
    private IngredienteRecetaRepository ingredienteRepo;

    @Autowired
    private PasoRecetaRepository pasoRepo;

    @Autowired
    private ValoracionRepository valoracionRepo;

    public List<Receta> obtenerRecetasAprobadas() {
        return recetaRepo.findByAprobadaTrue();
    }

    public List<Receta> buscarConFiltros(String nombre, String tipo, String ingredienteIncluido, String ingredienteExcluido, String ordenarPor, String orden) {
        List<Receta> recetas = recetaRepo.findAll();

        if (nombre != null && !nombre.isBlank()) {
            recetas = recetas.stream()
                    .filter(r -> r.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .toList();
        }

        if (tipo != null && !tipo.isBlank()) {
            recetas = recetas.stream()
                    .filter(r -> tipo.equalsIgnoreCase(r.getTipo()))
                    .toList();
        }

        if (ingredienteIncluido != null && !ingredienteIncluido.isBlank()) {
            recetas = recetas.stream()
                    .filter(r -> r.getIngredientes().stream()
                            .anyMatch(i -> i.getNombreIngrediente().toLowerCase().contains(ingredienteIncluido.toLowerCase())))
                    .toList();
        }

        if (ingredienteExcluido != null && !ingredienteExcluido.isBlank()) {
            recetas = recetas.stream()
                    .filter(r -> r.getIngredientes().stream()
                            .noneMatch(i -> i.getNombreIngrediente().toLowerCase().contains(ingredienteExcluido.toLowerCase())))
                    .toList();
        }

        Comparator<Receta> comparator = switch (ordenarPor) {
            case "nombre" -> Comparator.comparing(Receta::getNombre, String.CASE_INSENSITIVE_ORDER);
            case "autor" -> Comparator.comparing(r -> r.getAutor().getUsername(), String.CASE_INSENSITIVE_ORDER);
            default -> Comparator.comparing(Receta::getFechaCreacion); // por fecha
        };

        if ("desc".equalsIgnoreCase(orden)) {
            comparator = comparator.reversed();
        }

        return recetas.stream().sorted(comparator).toList();
    }

    public List<Receta> buscarPorNombre(String nombre) {
        return recetaRepo.findByNombreContainingIgnoreCaseAndAprobadaTrue(nombre);
    }

    public List<Receta> buscarPorTipo(String tipo) {
        return recetaRepo.findByTipoIgnoreCaseAndAprobadaTrue(tipo);
    }

    public List<Receta> recetasPorUsuario(Long usuarioId) {
        return recetaRepo.findByAutorId(usuarioId);
    }

    public Receta guardarReceta(Receta receta) {
        return recetaRepo.save(receta);
    }
    public Optional<Receta> existeReceta(String nombre,Long usuarioId) {
        return recetaRepo.findByNombreIgnoreCaseAndAutorId(nombre,usuarioId);
    }

    public List<IngredienteReceta> obtenerIngredientes(Long recetaId) {
        return ingredienteRepo.findByRecetaId(recetaId);
    }

    public List<PasoReceta> obtenerPasos(Long recetaId) {
        return pasoRepo.findByRecetaIdOrderByOrden(recetaId);
    }

    public Valoracion valorar(Long recetaId, Long usuarioId, int puntaje, String comentario) {
        if (valoracionRepo.existsByUsuarioIdAndRecetaId(usuarioId, recetaId)) {
            throw new RuntimeException("Ya valoraste esta receta");
        }

        Valoracion val = new Valoracion();
        val.setReceta(new Receta());
        val.getReceta().setId(recetaId);
        val.setUsuario(new Usuario());
        val.getUsuario().setId(usuarioId);
        val.setPuntaje(puntaje);
        val.setComentario(comentario);
        val.setAprobado(false);

        return valoracionRepo.save(val);
    }

    public List<Valoracion> obtenerValoracionesAprobadas(Long recetaId) {
        return valoracionRepo.findByRecetaIdAndAprobadoTrue(recetaId);
    }



    public List<Receta> buscarPorIngrediente(String ingrediente) {
        List<IngredienteReceta> relacionados = ingredienteRepo.findByNombreIngredienteIgnoreCaseContaining(ingrediente);
        return relacionados.stream()
                .map(IngredienteReceta::getReceta)
                .filter(Receta::isAprobada)
                .distinct()
                .toList();
    }

    public List<Receta> buscarSinIngrediente(String ingrediente) {
        List<Receta> todas = recetaRepo.findByAprobadaTrue();
        List<IngredienteReceta> conIngrediente = ingredienteRepo.findByNombreIngredienteIgnoreCaseContaining(ingrediente);
        List<Receta> conIngredienteSet = conIngrediente.stream()
                .map(IngredienteReceta::getReceta)
                .distinct()
                .toList();

        return todas.stream()
                .filter(r -> !conIngredienteSet.contains(r))
                .toList();
    }

    public Optional<Receta> editarReceta(Long id, Receta recetaActualizada) {
        return recetaRepo.findById(id).map(recetaExistente -> {

            recetaExistente.setNombre(recetaActualizada.getNombre());
            recetaExistente.setDescripcion(recetaActualizada.getDescripcion());
            recetaExistente.setPorciones(recetaActualizada.getPorciones());
            recetaExistente.setTipo(recetaActualizada.getTipo());
            recetaExistente.setFotosPlato(recetaActualizada.getFotosPlato());

            // Reemplazar ingredientes
            recetaExistente.getIngredientes().clear();
            recetaExistente.getIngredientes().addAll(recetaActualizada.getIngredientes());
            recetaExistente.getIngredientes().forEach(i -> i.setReceta(recetaExistente));

            // Reemplazar pasos
            recetaExistente.getPasos().clear();
            recetaExistente.getPasos().addAll(recetaActualizada.getPasos());
            recetaExistente.getPasos().forEach(p -> p.setReceta(recetaExistente));

            return recetaRepo.save(recetaExistente);
        });
    }

    public List<Receta> buscarPorIngredientes(List<String> ingredientes) {
        List<IngredienteReceta> relacionados = ingredienteRepo.findAll()
                .stream()
                .filter(i -> ingredientes.stream()
                        .anyMatch(req -> i.getNombreIngrediente().equalsIgnoreCase(req)))
                .toList();

        return relacionados.stream()
                .map(IngredienteReceta::getReceta)
                .filter(Receta::isAprobada)
                .distinct()
                .toList();
    }

    public List<Receta> buscarSinIngredientes(List<String> ingredientes) {
        List<Receta> todas = recetaRepo.findByAprobadaTrue();

        List<Receta> conIngredientes = ingredienteRepo.findAll()
                .stream()
                .filter(i -> ingredientes.stream()
                        .anyMatch(req -> i.getNombreIngrediente().equalsIgnoreCase(req)))
                .map(IngredienteReceta::getReceta)
                .distinct()
                .toList();

        return todas.stream()
                .filter(r -> !conIngredientes.contains(r))
                .toList();
    }

    public Optional<Receta> findbyID(Long id) {
        return recetaRepo.findById(id);
    }
    public List<Receta> obtenerRecetasRecientes() {
       List<Receta> list =  recetaRepo.findTop3ByOrderByFechaCreacionDesc();
       return list;

    }
    public Optional<Receta> buscarPorNombreUsuario(String nombre, Long usuarioId) {
       return recetaRepo.findByNombreIgnoreCaseAndAutorId(nombre, usuarioId);
    }
    public void borrarReceta(Receta receta){
        recetaRepo.delete(receta);
    }
    public Optional<Receta> buscarPorNombreReceta(String nombre){
        return recetaRepo.findByNombre(nombre);
    }

    public List<Receta> findAll() {
        return recetaRepo.findAll();
    }
}