package desarrllo_aplicaciones.tp.services.Cursos;

import desarrllo_aplicaciones.tp.Controller.cursos.dto.AsistenciaQRRequest;
import desarrllo_aplicaciones.tp.model.Cursos.*;
import desarrllo_aplicaciones.tp.repository.Cursos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AsistenciaService {
    @Autowired
    private  CursoSedeRepository cursoSedeRepo;
    @Autowired
    private  InscripcionCursoRepository inscripcionRepo;
    @Autowired
    private  AsistenciaRepository asistenciaRepo;
    @Autowired
    private  UsuarioRepository usuarioRepo;
    @Autowired
    private TokenQRAsistenciaRepository qrRepo;

    // Registrar asistencia desde token escaneado
    public void registrarAsistenciaDesdeQR(Long usuarioId, Long cursoSedeId, LocalDate fecha, String token) {
        Usuario alumno = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        CursoSede cursoSede = cursoSedeRepo.findById(cursoSedeId)
                .orElseThrow(() -> new RuntimeException("CursoSede no encontrado"));

        TokenQRAsistencia qr = qrRepo.findByCursoSedeAndFechaClase(cursoSede, fecha)
                .orElseThrow(() -> new RuntimeException("QR no generado para esta clase"));

        if (!qr.getToken().equals(token) || !qr.isActivo()) {
            throw new RuntimeException("QR inválido o desactivado");
        }

        InscripcionCurso inscripcion = inscripcionRepo
                .findByAlumnoAndCursoSede(alumno, cursoSede)
                .orElseThrow(() -> new RuntimeException("No estás inscripto a este curso"));

        if (asistenciaRepo.existsByInscripcionAndFechaClase(inscripcion, fecha)) {
            throw new RuntimeException("Asistencia ya registrada");
        }

        Asistencia asistencia = new Asistencia();
        asistencia.setInscripcion(inscripcion);
        asistencia.setFechaClase(fecha);
        asistencia.setPresente(true);

        asistenciaRepo.save(asistencia);
    }

    public double calcularPorcentajeAsistenciaById(Long inscripcionId) {
        InscripcionCurso inscripcion = inscripcionRepo.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        long diasTotales = java.time.temporal.ChronoUnit.DAYS.between(
                inscripcion.getCursoSede().getFechaInicio(),
                inscripcion.getCursoSede().getFechaFin()) + 1;

        if (diasTotales <= 0) throw new RuntimeException("Fechas del curso inválidas");

        long asistencias = asistenciaRepo.countByInscripcionAndPresenteTrue(inscripcion);
        return ((double) asistencias / diasTotales) * 100;
    }

    public String generarQRBase64(Long cursoSedeId, LocalDate fecha) {
        CursoSede cursoSede = cursoSedeRepo.findById(cursoSedeId)
                .orElseThrow(() -> new RuntimeException("CursoSede no encontrado"));

        TokenQRAsistencia qr = qrRepo.findByCursoSedeAndFechaClase(cursoSede, fecha)
                .orElseGet(() -> {
                    TokenQRAsistencia nuevo = new TokenQRAsistencia();
                    nuevo.setCursoSede(cursoSede);
                    nuevo.setFechaClase(fecha);
                    nuevo.setToken(UUID.randomUUID().toString());
                    nuevo.setActivo(true);
                    return qrRepo.save(nuevo);
                });

        String payload = cursoSedeId + ";" + fecha + ";" + qr.getToken();
        return Base64.getEncoder().encodeToString(payload.getBytes());
    }
}


