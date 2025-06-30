package desarrllo_aplicaciones.tp.repository.Recetas;

import desarrllo_aplicaciones.tp.model.Recetas.Receta;
import desarrllo_aplicaciones.tp.model.Recetas.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {

    List<Valoracion> findByRecetaIdAndAprobadoTrue(Long recetaId);

    boolean existsByUsuarioIdAndRecetaId(Long usuarioId, Long recetaId);

    Valoracion findByIdAndReceta(Long valoracionId, Receta receta);
    List<Valoracion> findByRecetaId(Long recetaId);

}
