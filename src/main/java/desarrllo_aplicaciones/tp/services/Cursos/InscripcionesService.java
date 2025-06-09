package desarrllo_aplicaciones.tp.services.Cursos;

import desarrllo_aplicaciones.tp.model.Cursos.Curso;
import desarrllo_aplicaciones.tp.model.Cursos.Inscripcion;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import desarrllo_aplicaciones.tp.repository.Cursos.CursoRepository;
import desarrllo_aplicaciones.tp.repository.Cursos.InscripcionRepository;
import desarrllo_aplicaciones.tp.repository.Cursos.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class InscripcionesService {

    @Autowired
    private InscripcionRepository inscripcionRepo;

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    public Inscripcion inscribirse(Long cursoId, Long usuarioId) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        boolean yaInscripto = inscripcionRepo.findByAlumnoIdAndCursoId(usuarioId, cursoId).isPresent();
        if (yaInscripto) throw new RuntimeException("Ya inscripto en el curso");

        Inscripcion insc = new Inscripcion();
        insc.setAlumno(usuario);
        insc.setCurso(curso);
        insc.setFechaInscripcion(LocalDate.now());

        double precio = curso.getPrecio();
        if (curso.getSede() != null) {
            precio = precio * (1 - curso.getSede().getDescuento());
        }
        insc.setMontoPagado(precio);
        insc.setEstado(Inscripcion.EstadoInscripcion.INSCRIPTO);

        return inscripcionRepo.save(insc);
    }

    public Inscripcion baja(Long inscripcionId, LocalDate fechaCancelacion) {
        Inscripcion insc = inscripcionRepo.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        if (insc.isDadoDeBaja()) throw new RuntimeException("Ya se dio de baja");

        Curso curso = insc.getCurso();
        long diasAntes = ChronoUnit.DAYS.between(fechaCancelacion, curso.getFechaInicio());

        if (diasAntes >= 10) {
            insc.setEstado(Inscripcion.EstadoInscripcion.BAJA_100);
        } else if (diasAntes >= 1) {
            insc.setEstado(Inscripcion.EstadoInscripcion.BAJA_70);
        } else if (diasAntes == 0) {
            insc.setEstado(Inscripcion.EstadoInscripcion.BAJA_50);
        } else {
            throw new RuntimeException("No puede darse de baja, ya comenzó el curso");
        }

        insc.setDadoDeBaja(true);
        return inscripcionRepo.save(insc);
    }

    public List<Inscripcion> cursosDelUsuario(Long usuarioId) {
        return inscripcionRepo.findByAlumnoId(usuarioId);
    }
}
