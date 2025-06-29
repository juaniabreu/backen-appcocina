package desarrllo_aplicaciones.tp.repository;

import desarrllo_aplicaciones.tp.model.EmailDTO;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


public interface IEmailRepository {
    public void sendSimpleMail(String email,String codigo) throws MessagingException;

}
