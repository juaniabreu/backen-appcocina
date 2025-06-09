package desarrllo_aplicaciones.tp.services.Cursos;


import desarrllo_aplicaciones.tp.model.Cursos.Curso;
import desarrllo_aplicaciones.tp.repository.Cursos.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepo;

    public List<Curso> obtenerTodos() {
        return cursoRepo.findAll();
    }

    public Curso obtenerPorId(Long id) {
        return cursoRepo.findById(id).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
    }
}
