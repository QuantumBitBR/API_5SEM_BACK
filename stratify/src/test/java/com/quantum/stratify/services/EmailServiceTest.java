package com.quantum.stratify.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

class EmailServiceTest {

    private JavaMailSender mailSender;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        mailSender = mock(JavaMailSender.class);
        emailService = new EmailService(mailSender); // <- injeta via construtor aqui!
    }

    @Test
    void testEnviarEmailSenhaResetada_sucesso() {
        // Arrange
        String destino = "usuario@teste.com";
        String novaSenha = "Senha123@";

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Act
        emailService.enviarEmailSenhaResetada(destino, novaSenha);

        // Assert
        verify(mailSender, times(1)).send(captor.capture());
        SimpleMailMessage mensagemEnviada = captor.getValue();

        assertEquals(destino, mensagemEnviada.getTo()[0]);
        assertEquals("Reset de senha - Stratify", mensagemEnviada.getSubject());
        assertTrue(mensagemEnviada.getText().contains(novaSenha));
        assertTrue(mensagemEnviada.getText().contains("Por favor, altere após o login."));
    }
}
