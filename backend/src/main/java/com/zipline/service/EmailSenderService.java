package com.zipline.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * The type Email sender service.
 */
@Service
@Transactional
public class EmailSenderService {
    private final JavaMailSender javaMailSender;

    /**
     * Instantiates a new Email sender service.
     *
     * @param javaMailSender the java mail sender
     */
    public EmailSenderService(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Send email.
     *
     * @param email the email
     */
    public void sendEmail(final SimpleMailMessage email) {
        javaMailSender.send(email);
    }
}
