package desarrllo_aplicaciones.tp.services;

import desarrllo_aplicaciones.tp.model.CodigoVerificacion;
import desarrllo_aplicaciones.tp.model.EmailDTO;
import desarrllo_aplicaciones.tp.repository.IEmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailRepository {
    private final JavaMailSender mailSender;
    CodigoVerificacionService codigoVerificacionService;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Override
    public void sendSimpleMail(String email,String codigo) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
        helper.setTo(email);
        helper.setSubject("Codigo de verificacion");
        helper.setText("Su codigo de verificacion es: " + codigo);
        mailSender.send(mimeMessage);
    }
}
