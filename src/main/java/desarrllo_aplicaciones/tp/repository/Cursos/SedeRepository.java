package desarrllo_aplicaciones.tp.repository.Cursos;

import desarrllo_aplicaciones.tp.model.Cursos.Curso;
import desarrllo_aplicaciones.tp.model.Cursos.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Long> {

   /**
    * GET /sedes: listar todas las sedes
    *
    * GET /sedes/{id}: detalles de una sede
    *
    * GET /sedes/{id}/cursos: cursos dictados en esa sede
    *
    * POST /sedes: crear sede (admin)
    *
    * PUT /sedes/{id}: editar
    *
    * DELETE /sedes/{id}: eliminar*/
   List<Sede> findAll();
   Optional<Sede> findById(Long id);
}
