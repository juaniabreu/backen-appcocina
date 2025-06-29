package desarrllo_aplicaciones.tp.repository.Recetas;
import desarrllo_aplicaciones.tp.model.Recetas.IngredienteReceta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredienteRecetaRepository extends JpaRepository<IngredienteReceta, Long> {

    List<IngredienteReceta> findByRecetaId(Long recetaId);

    List<IngredienteReceta> findByNombreIngredienteIgnoreCaseContaining(String ingrediente);
}
