package desarrllo_aplicaciones.tp.services.Cursos;


import desarrllo_aplicaciones.tp.model.Cursos.Curso;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import desarrllo_aplicaciones.tp.repository.Cursos.CursoRepository;
import desarrllo_aplicaciones.tp.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoService {
    @Autowired
    private CursoRepository cursoRepo;

    public List<Curso> listarTodos() {
        return cursoRepo.findAll();
    }

    public Curso crearCurso(Curso curso) {
        return cursoRepo.save(curso);
    }

    public Optional<Curso> obtenerPorId(Long id) {
        return cursoRepo.findById(id);
    }

    public List<Curso> filtrarPorModalidad(Curso.Modalidad modalidad) {
        return cursoRepo.findByModalidad(modalidad);
    }
}

/**
 * GET /cursos: listar todos los cursos (solo nombre y breve descripción si es visitante/usuario)
 *
 * GET /cursos/{id}: obtener detalles completos del curso (solo alumno)
 *
 * GET /cursos/disponibles: cursos activos por sede y modalidad
 *
 * POST /cursos: crear curso (admin)
 *
 * PUT /cursos/{id}: editar curso (admin)
 *
 * DELETE /cursos/{id}: eliminar curso (admin)*/