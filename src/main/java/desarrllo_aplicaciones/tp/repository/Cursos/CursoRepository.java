package desarrllo_aplicaciones.tp.repository.Cursos;


import desarrllo_aplicaciones.tp.model.Cursos.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    Optional<Curso> findByNombre(String nombre);

    Optional<Curso> findById(Long id);
}
