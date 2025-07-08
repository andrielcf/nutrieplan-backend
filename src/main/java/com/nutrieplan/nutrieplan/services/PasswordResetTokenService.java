package com.nutrieplan.nutrieplan.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.nutrieplan.nutrieplan.entity.user.User;
import com.nutrieplan.nutrieplan.entity.user.PasswordReset.PasswordResetToken;
import com.nutrieplan.nutrieplan.repositories.PasswordResetTokenRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
public class PasswordResetTokenService {
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${password.reset.token.expiry}")
    private long expiryTime;

    @Transactional
    public void createPasswordResetTokenForUser(User user) throws MessagingException {
        // Remove qualquer token antigo desse usuário
        tokenRepository.deleteByUser(user);

        System.out.println("ENVIAR TOKEN PARA: " + user.getEmail());

        // Gera um token único
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (tokenRepository.existsByToken(token));

        System.out.println("TOKEN GERADO: " + token);

        // Cria e salva o novo token
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUser(user);
        myToken.setExpiryDate(new Date(System.currentTimeMillis() + expiryTime));

        tokenRepository.save(myToken);

        String resetLink = "http://localhost:5173/reset-password/" + token;
        sendPasswordResetEmail(user.getEmail(), resetLink);
    }

    public String validatePasswordResetToken(String token) {
        PasswordResetToken passToken = tokenRepository.findByToken(token);
        if (passToken == null) {
            return "invalid";
        }

        if (passToken.getExpiryDate().before(new Date())) {
            return "expired";
        }

        return null;
    }

    public User getUserByPasswordResetToken(String token) {
        return tokenRepository.findByToken(token).getUser();
    }

    // private void sendPasswordResetEmail(String email, String resetLink) {
    // SimpleMailMessage message = new SimpleMailMessage();
    // message.setFrom("nutrieplan@gmail.com");
    // message.setTo(email);
    // message.setSubject("Redefinição de senha - NutriePlan");
    // message.setText("Para redefinir sua senha, clique no link abaixo:\n" +
    // resetLink +
    // "\n\nSe você não solicitou esta redefinição, ignore este e-mail.");
    // mailSender.send(message);
    // }
    private void sendPasswordResetEmail(String email, String resetLink) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlContent = "<p>Para redefinir sua senha, clique no botão abaixo:</p>"
                + "<a href=\"" + resetLink + "\" style=\"display:inline-block;padding:10px 20px;"
                + "font-size:16px;color:#fff;background-color:#28a745;text-decoration:none;"
                + "border-radius:5px;\">Redefinir Senha</a>"
                + "<p>Se você não solicitou esta redefinição, ignore este e-mail.</p>";

        helper.setFrom("nutrieplan@gmail.com");
        helper.setTo(email);
        helper.setSubject("Redefinição de senha - NutriePlan");
        helper.setText(htmlContent, true); // O segundo parâmetro indica que é HTML

        mailSender.send(mimeMessage);
    }
}
