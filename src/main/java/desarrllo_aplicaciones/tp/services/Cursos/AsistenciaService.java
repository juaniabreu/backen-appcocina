package desarrllo_aplicaciones.tp.services.Cursos;

import desarrllo_aplicaciones.tp.model.Cursos.Asistencia;
import desarrllo_aplicaciones.tp.model.Cursos.Curso;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import desarrllo_aplicaciones.tp.repository.Cursos.AsistenciaRepository;
import desarrllo_aplicaciones.tp.repository.Cursos.CursoRepository;
import desarrllo_aplicaciones.tp.repository.Cursos.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepo;

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    public Asistencia registrarAsistencia(Long usuarioId, Long cursoId) {
        Usuario alumno = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Asistencia asistencia = new Asistencia();
        asistencia.setAlumno(alumno);
        asistencia.setCurso(curso);
        asistencia.setFechaClase(LocalDate.now());
        asistencia.setPresente(true);

        return asistenciaRepo.save(asistencia);
    }

    public List<Asistencia> obtenerAsistencias(Long usuarioId) {
        return asistenciaRepo.findByAlumnoId(usuarioId);
    }
}

