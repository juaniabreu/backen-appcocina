package desarrllo_aplicaciones.tp.repository.Recetas;

import desarrllo_aplicaciones.tp.model.Recetas.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    List<Receta> findByAprobadaTrue();

    List<Receta> findByAutorId(Long usuarioId);

    List<Receta> findByNombreContainingIgnoreCaseAndAprobadaTrue(String nombre);

    List<Receta> findByTipoIgnoreCaseAndAprobadaTrue(String tipo);
}
