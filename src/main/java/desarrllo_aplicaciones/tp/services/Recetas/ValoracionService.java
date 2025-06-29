package desarrllo_aplicaciones.tp.services.Recetas;

import desarrllo_aplicaciones.tp.model.Recetas.Valoracion;
import desarrllo_aplicaciones.tp.repository.Recetas.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValoracionService {
    @Autowired
    ValoracionRepository valoracionRepository;

    public List<Valoracion> findByRecetaId(Long recetaId){
        return valoracionRepository.findByRecetaId(recetaId);
    }
    public Valoracion guardarValoracion(Valoracion valoracion){
        return valoracionRepository.save(valoracion);
    }
    public Optional<Valoracion> findValoracionById(Long valoracionId){
        return valoracionRepository.findById(valoracionId);
    }
    public List<Valoracion> findAllValoracionesAprobadas(Long idReceta){
        return valoracionRepository.findByRecetaIdAndAprobadoTrue(idReceta);
    }
}
