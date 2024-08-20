package com.engrkirky.moonlight.service;

public interface EmailSenderService {
    void sendEmail(String sender, String recipient, String subject, String body);
}
