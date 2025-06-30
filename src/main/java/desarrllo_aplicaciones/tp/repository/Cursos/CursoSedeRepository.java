package desarrllo_aplicaciones.tp.repository.Cursos;


import desarrllo_aplicaciones.tp.model.Cursos.CursoSede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoSedeRepository extends JpaRepository<CursoSede, Long> {
    List<CursoSede> findBySedeId(Long sedeId);
    List<CursoSede> findByCursoId(Long cursoId);
}
