package desarrllo_aplicaciones.tp.repository.Cursos;

import desarrllo_aplicaciones.tp.model.Cursos.CursoSede;
import desarrllo_aplicaciones.tp.model.Cursos.TokenQRAsistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TokenQRAsistenciaRepository extends JpaRepository<TokenQRAsistencia, Long> {
    Optional<TokenQRAsistencia> findByCursoSedeAndFechaClase(CursoSede cursoSede, LocalDate fechaClase);
    Optional<TokenQRAsistencia> findByToken(String token);
}
