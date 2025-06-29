package desarrllo_aplicaciones.tp.repository.Recetas;

import desarrllo_aplicaciones.tp.model.Recetas.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    List<Receta> findByAprobadaTrue();
    Optional<Receta> findByNombre(String nombre);
    List<Receta> findByAutorId(Long usuarioId);
    Optional<Receta> findById(Long id);

    List<Receta> findByNombreContainingIgnoreCaseAndAprobadaTrue(String nombre);

    List<Receta> findByTipoIgnoreCaseAndAprobadaTrue(String tipo);

    Optional<Receta> findByNombreIgnoreCaseAndAutorId(String nombre, Long usuarioId);
    List<Receta> findTop3ByOrderByFechaCreacionDesc();

}
