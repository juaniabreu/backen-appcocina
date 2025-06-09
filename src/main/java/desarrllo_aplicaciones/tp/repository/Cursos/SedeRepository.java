package desarrllo_aplicaciones.tp.repository.Cursos;

import desarrllo_aplicaciones.tp.model.Cursos.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Long> {

    Optional<Sede> findByNombre(String nombre);
}
