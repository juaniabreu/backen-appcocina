package desarrllo_aplicaciones.tp.services.Cursos;

import desarrllo_aplicaciones.tp.model.Cursos.CursoSede;
import desarrllo_aplicaciones.tp.model.Cursos.InscripcionCurso;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import desarrllo_aplicaciones.tp.repository.Cursos.CursoSedeRepository;
import desarrllo_aplicaciones.tp.repository.Cursos.InscripcionCursoRepository;
import desarrllo_aplicaciones.tp.repository.Cursos.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class InscripcionService {

    @Autowired
    private  InscripcionCursoRepository inscripcionRepo;
    @Autowired
    private  CursoSedeRepository cursoSedeRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    public String cancelarInscripcion(Long inscripcionId) {
        InscripcionCurso inscripcion = inscripcionRepo.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        LocalDate hoy = LocalDate.now();
        LocalDate inicio = inscripcion.getCursoSede().getFechaInicio();

        long diasAntes = ChronoUnit.DAYS.between(hoy, inicio);
        double monto = inscripcion.getMontoPagado();
        double reintegro = 0.0;

        if (hoy.isAfter(inicio)) {
            inscripcion.setEstadoPago(InscripcionCurso.EstadoPago.NO_REINTEGRADO);
            reintegro = 0.0;
        } else if (diasAntes >= 10) {
            inscripcion.setEstadoPago(InscripcionCurso.EstadoPago.REINTEGRADO_TOTAL);
            reintegro = monto;
        } else if (diasAntes >= 1) {
            inscripcion.setEstadoPago(InscripcionCurso.EstadoPago.REINTEGRADO_PARCIAL);
            reintegro = monto * 0.7;
        } else if (diasAntes == 0) {
            inscripcion.setEstadoPago(InscripcionCurso.EstadoPago.REINTEGRADO_PARCIAL);
            reintegro = monto * 0.5;
        }

        inscripcion.getCursoSede().setCuposDisponibles(
                inscripcion.getCursoSede().getCuposDisponibles() + 1
        );

        inscripcionRepo.save(inscripcion);
        cursoSedeRepo.save(inscripcion.getCursoSede());

        return "Reintegro de $" + reintegro + " aplicado. Estado: " + inscripcion.getEstadoPago();
    }

    public InscripcionCurso inscribirAlumno(Usuario alumno, Long cursoSedeId) {
        CursoSede cursoSede = cursoSedeRepo.findById(cursoSedeId)
                .orElseThrow(() -> new RuntimeException("CursoSede no encontrado"));

        if (cursoSede.getCuposDisponibles() <= 0) {
            throw new RuntimeException("No hay cupos disponibles");
        }

        Optional<InscripcionCurso> yaInscripto = inscripcionRepo.findByAlumnoAndCursoSede(alumno, cursoSede);
        if (yaInscripto.isPresent()) {
            throw new RuntimeException("El alumno ya está inscrito en este curso");
        }

        double precioFinal = cursoSede.getPrecio();
        if (cursoSede.getDescuento() != null) {
            precioFinal = precioFinal - (precioFinal * cursoSede.getDescuento());
        }

        InscripcionCurso inscripcion = new InscripcionCurso();
        inscripcion.setAlumno(alumno);
        inscripcion.setCursoSede(cursoSede);
        inscripcion.setFechaInscripcion(LocalDate.now());
        inscripcion.setMontoPagado(precioFinal);
        inscripcion.setEstadoPago(InscripcionCurso.EstadoPago.PAGADO);

        cursoSede.setCuposDisponibles(cursoSede.getCuposDisponibles() - 1);

        inscripcionRepo.save(inscripcion);
        cursoSedeRepo.save(cursoSede);

        return inscripcion;
    }
    public InscripcionCurso obtenerInscripcionPorId(Long id) {
        return inscripcionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));
    }

    public List<InscripcionCurso> obtenerInscripcionesPorAlumno(Long alumnoId) {
        return inscripcionRepo.findByAlumnoId(alumnoId);
    }
    public InscripcionCurso inscribirAlumnoPorId(Long usuarioId, Long cursoSedeId) {
        Usuario alumno = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return inscribirAlumno(alumno, cursoSedeId); // reutiliza el método original
    }

}

