package desarrllo_aplicaciones.tp.repository;

import desarrllo_aplicaciones.tp.model.CodigoVerificacion;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodigoVerificacionRepository extends JpaRepository<CodigoVerificacion, Long> {

    Optional<CodigoVerificacion> findByUsuarioAndCodeAndUsedIsFalse(Usuario usuario,String code);
    Optional<CodigoVerificacion> findByUsedIsTrue();
}
