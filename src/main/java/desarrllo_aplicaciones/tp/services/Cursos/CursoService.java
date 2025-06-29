package desarrllo_aplicaciones.tp.services.Cursos;


import desarrllo_aplicaciones.tp.model.Cursos.Curso;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import desarrllo_aplicaciones.tp.repository.Cursos.CursoRepository;
import desarrllo_aplicaciones.tp.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepo;
    @Autowired
    private CustomUserDetailsService userDetailsService;

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

    public List<Curso> getAllCursos(){
        return cursoRepo.findAll();
    }
    public Optional<Curso> getCursoById(Long id){
        return cursoRepo.findById(id);
    }
    public Curso createCurso(Curso curso){
        return cursoRepo.save(curso);
    }
    public Curso updateCurso(Curso curso){
        return cursoRepo.save(curso);
    }
    public void deleteCurso(Curso curso){
        cursoRepo.delete(curso);
    }
    public boolean hayVacantes(Curso curso) {
        // Suponiendo un límite teórico o basado en algún campo, o número fijo por ahora.
        return curso.getAlumnos().size() < 30;
    }

    public Curso inscribirAlumno(Long cursoId, Long usuarioId) {
        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
            Usuario alumno = userDetailsService.findById(usuarioId);

        if (!alumno.getTipo().equals(Usuario.TipoUsuario.ALUMNO)) {
            throw new RuntimeException("Solo los alumnos pueden inscribirse.");
        }

        if (!hayVacantes(curso)) {
            throw new RuntimeException("No hay vacantes disponibles.");
        }

        // Cobro ficticio por tarjeta de crédito
        if (alumno.getTarjetaCredito() == null) {
            throw new RuntimeException("Debe tener tarjeta registrada.");
        }

        alumno.getCursosInscriptos().add(curso);
        userDetailsService.save(alumno); // guarda la relación
        return curso;
    }


}
