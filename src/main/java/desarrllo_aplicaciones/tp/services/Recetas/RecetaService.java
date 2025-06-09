package desarrllo_aplicaciones.tp.services.Recetas;

import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import desarrllo_aplicaciones.tp.model.Recetas.*;
import desarrllo_aplicaciones.tp.repository.Recetas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        receta.setAprobada(false); // requiere revisión
        return recetaRepo.save(receta);
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
        val.setAprobado(false); // requiere revisión

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

    public List<Receta> obtenerRecetasRecientes() {
        return null;
    }
}