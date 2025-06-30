package desarrllo_aplicaciones.tp.repository.Cursos;

import desarrllo_aplicaciones.tp.model.Cursos.CursoSede;
import desarrllo_aplicaciones.tp.model.Cursos.InscripcionCurso;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionCursoRepository extends JpaRepository<InscripcionCurso, Long> {
    Optional<InscripcionCurso> findByAlumnoAndCursoSede(Usuario alumno, CursoSede cursoSede);

    List<InscripcionCurso> findByAlumnoId(Long alumnoId);
}
