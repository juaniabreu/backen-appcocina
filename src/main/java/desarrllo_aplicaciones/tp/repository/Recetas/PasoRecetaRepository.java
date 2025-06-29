package desarrllo_aplicaciones.tp.repository.Recetas;
import desarrllo_aplicaciones.tp.model.Recetas.PasoReceta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasoRecetaRepository extends JpaRepository<PasoReceta, Long> {

    List<PasoReceta> findByRecetaIdOrderByOrden(Long recetaId);
}
