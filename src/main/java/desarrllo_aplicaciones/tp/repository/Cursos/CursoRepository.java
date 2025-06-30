package desarrllo_aplicaciones.tp.repository.Cursos;

import desarrllo_aplicaciones.tp.model.Cursos.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByModalidad(Curso.Modalidad modalidad);
}

