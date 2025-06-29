package desarrllo_aplicaciones.tp.services.Cursos;

import desarrllo_aplicaciones.tp.model.Cursos.Curso;
import desarrllo_aplicaciones.tp.model.Cursos.Sede;
import desarrllo_aplicaciones.tp.repository.Cursos.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SedeService{

    @Autowired
    SedeRepository sedeRepository;

    public List<Sede> findAll() {
        return sedeRepository.findAll();
    }

    public Optional<Sede> findById(Long id) {
        if (sedeRepository.findById(id).isPresent()) {
            return sedeRepository.findById(id);
        }
        return null;
    }


    public Sede save(Sede sede) {
       return sedeRepository.save(sede);
    }
    public Sede update(Sede sede) {
        return sedeRepository.save(sede);
    }
    public void deleteSedeById(Long sedeId) {
        sedeRepository.deleteById(sedeId);
    }
}
