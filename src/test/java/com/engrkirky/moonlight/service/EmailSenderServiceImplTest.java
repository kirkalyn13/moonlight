package com.engrkirky.moonlight.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailSenderServiceImplTest {
    @Mock JavaMailSender mailSender;
    @InjectMocks EmailSenderServiceImpl underTest;

    @BeforeEach
    void setup() {
        underTest = new EmailSenderServiceImpl(mailSender);
    }
    @Test
    void sendEmail() {
        String sender = "test.sender@example.com";
        String recipient = "test.recipient@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        underTest.sendEmail(sender, recipient, subject, body);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertEquals(sender, sentMessage.getFrom());
        assertEquals(recipient, Objects.requireNonNull(sentMessage.getTo())[0]);
        assertEquals(subject, sentMessage.getSubject());
        assertEquals(body, sentMessage.getText());
    }
}