package desarrllo_aplicaciones.tp.repository.Cursos;


import desarrllo_aplicaciones.tp.model.Cursos.CursoSede;
import desarrllo_aplicaciones.tp.model.Cursos.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoSedeRepository extends JpaRepository<CursoSede, Long> {
    List<CursoSede> findBySedeId(Long sedeId);
    List<CursoSede> findByCursoId(Long cursoId);
    @Query("SELECT DISTINCT cs.sede FROM CursoSede cs WHERE cs.curso.id = :cursoId")
    List<Sede> findSedesByCursoId(@Param("cursoId") Long cursoId);

}
