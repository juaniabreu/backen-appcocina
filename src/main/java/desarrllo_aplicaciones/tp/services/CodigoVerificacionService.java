package desarrllo_aplicaciones.tp.services;

import desarrllo_aplicaciones.tp.model.CodigoVerificacion;
import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
import desarrllo_aplicaciones.tp.repository.CodigoVerificacionRepository;
import desarrllo_aplicaciones.tp.repository.Cursos.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class CodigoVerificacionService {
    @Autowired
    private CodigoVerificacionRepository codigoVerificacionRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public CodigoVerificacion generarCodigo(Usuario usuario) {
        // 1. Generar un código aleatorio de 6 dígitos
        String codigo = String.format("%06d", new Random().nextInt(1000000));

        // 2. Crear instancia de VerificationCode
        CodigoVerificacion verificationCode = new CodigoVerificacion();
        verificationCode.setUsuario(usuario);
        verificationCode.setCode(codigo);
        verificationCode.setExpiresAt(LocalDateTime.now().plusMinutes(10)); // Expira en 10 min
        verificationCode.setUsed(false);

        // 3. Guardar en la base de datos
        return codigoVerificacionRepository.save(verificationCode);
    }


    public Optional<CodigoVerificacion> findByUsuarioAndCodeAndUsedIsFalse(Usuario usuario, String code) {
        Optional<CodigoVerificacion> vcOpt = codigoVerificacionRepository
                .findByUsuarioAndCodeAndUsedIsFalse(usuario, code);
        System.out.println(vcOpt.get().getCode() + " " + vcOpt.get().getUsuario());
        if (vcOpt.isPresent()) {
            System.out.println(true);
            CodigoVerificacion vc = vcOpt.get();

            if (LocalDateTime.now().isBefore(vc.getExpiresAt())) {
                System.out.println(vc.isUsed());
                vc.setUsed(true);
                codigoVerificacionRepository.save(vc);

                usuarioRepository.save(usuario);
                System.out.println("Codigo valido");

            } else {
                System.out.println("Código expirado");
            }

        } else {
            System.out.println("Código incorrecto o ya usado");
        }
        return vcOpt;
    }
}
