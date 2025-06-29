    package desarrllo_aplicaciones.tp.Controller.auth;

    import desarrllo_aplicaciones.tp.Controller.auth.dto.*;
    import desarrllo_aplicaciones.tp.model.CodigoVerificacion;
    import desarrllo_aplicaciones.tp.model.Cursos.TarjetaCredito;
    import desarrllo_aplicaciones.tp.model.Cursos.Usuario;
    import desarrllo_aplicaciones.tp.repository.CodigoVerificacionRepository;
    import desarrllo_aplicaciones.tp.repository.Cursos.UsuarioRepository;
    import desarrllo_aplicaciones.tp.repository.IEmailRepository;
    import desarrllo_aplicaciones.tp.services.CodigoVerificacionService;
    import desarrllo_aplicaciones.tp.utils.JwtUtils;
    import jakarta.mail.MessagingException;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.autoconfigure.security.SecurityProperties;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.web.bind.annotation.*;

    import java.util.Optional;

    @RestController
    @CrossOrigin("*")
    @RequestMapping("/auth")
    public class AuthController {

        @Autowired
        private CodigoVerificacionService codigoVerificacionService;
        private final AuthenticationManager authenticationManager;
        private final JwtUtils jwtUtils;
        private final UsuarioRepository usuarioRepository;
        private final PasswordEncoder passwordEncoder;
        @Autowired
        IEmailRepository emailRepository;

        public AuthController(AuthenticationManager authenticationManager,
                              UsuarioRepository usuarioRepository,
                              PasswordEncoder passwordEncoder,
                              JwtUtils jwtUtils) {
            this.authenticationManager = authenticationManager;
            this.usuarioRepository = usuarioRepository;
            this.passwordEncoder = passwordEncoder;
            this.jwtUtils = jwtUtils;
        }

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody AuthRequest request) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            String token = jwtUtils.createToken(authentication);
            return ResponseEntity.ok(new AuthResponse(token));
        }

        @PostMapping("/register")
        public ResponseEntity<?> enviarCodigo(@RequestBody Usuario usuario) throws MessagingException {
            if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("Username ya en uso");
            }
            if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email ya en uso");
            }
            usuarioRepository.save(usuario);
          CodigoVerificacion code =  codigoVerificacionService.generarCodigo(usuario);
          emailRepository.sendSimpleMail(usuario.getEmail(),code.getCode());
          return ResponseEntity.ok("SE ENVIO UN MAIL CAPO");
        }

        @PostMapping("/register/finalizar")
        public ResponseEntity<?> finalizarRegistro(@RequestBody Usuario usuario) {
            Usuario user = usuarioRepository.findByEmail(usuario.getEmail()).get();
            user.setPassword(passwordEncoder.encode(usuario.getPassword()));
            user.setActivado(true);
            user.setAvatar(usuario.getAvatar());
            user.setDireccion(usuario.getDireccion());
            user.setNombre(usuario.getNombre());
            usuarioRepository.save(user);
            return ResponseEntity.ok("USUARIO CREADO");
        }
        @PostMapping("/register-alumno")
        public ResponseEntity<?> upgradeAlumno(@RequestBody Usuario usuario) {
            System.out.println(usuario.getEmail());

            TarjetaCredito tj = usuario.getTarjetaCredito();
            Usuario user = usuarioRepository.findByEmail(usuario.getEmail()).get();
            user.setFotoDniDorso(usuario.getFotoDniDorso());
            user.setFotoDniFrente(usuario.getFotoDniFrente());
            user.setTarjetaCredito(tj);
            user.setEdad(usuario.getEdad());
            user.setTipo(Usuario.TipoUsuario.ALUMNO);
            user.setNumeroTramiteDni(usuario.getNumeroTramiteDni());
            usuarioRepository.save(user);
            return ResponseEntity.ok("USUARIO CREADO");
        }
        @PostMapping("/verify-code")
        public ResponseEntity<?> verifyCode(@RequestBody CodigoVerificacionRequest request) {
            Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            Optional<CodigoVerificacion> valid = codigoVerificacionService.findByUsuarioAndCodeAndUsedIsFalse(usuario, request.getCode());


            if (valid.isPresent()) {
                usuarioRepository.save(usuario);
                return ResponseEntity.ok("Cuenta verificada con éxito.");
            } else {
                return ResponseEntity.badRequest().body("Código inválido o expirado.");
            }
        }
        @GetMapping("/usuario-actual")
        public ResponseEntity<?> obtenerUsuarioActual(Authentication authentication) {
            String email = authentication.getName();
            System.out.println(email);
            Usuario usuario = usuarioRepository.findByUsername(email).orElse(null);
            return ResponseEntity.ok(usuario);
        }

        @GetMapping("/usuario-actual-2")
        public ResponseEntity<?> obtenerUsuarioActual2() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) authentication.getPrincipal();
            return ResponseEntity.ok(usuario);
        }
        @PostMapping("/forgot-password")
        public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
            // Verificamos que exista el usuario
            Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            return ResponseEntity.ok("Se ha enviado un código de recuperación al email.");
        }

        @PostMapping("/reset-password")
        public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
            Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            usuario.setPassword(passwordEncoder.encode(request.getNewPassword()));

            usuarioRepository.save(usuario);

            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        }
    }

