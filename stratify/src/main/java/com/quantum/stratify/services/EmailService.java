package com.quantum.stratify.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
    }
    public void enviarEmailSenhaResetada(String destino, String novaSenha) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destino);
        mensagem.setSubject("Reset de senha - Stratify");
        mensagem.setText("Sua nova senha de acesso é: " + novaSenha + "\n\nPor favor, altere após o login.");
        mailSender.send(mensagem);
    }
}
