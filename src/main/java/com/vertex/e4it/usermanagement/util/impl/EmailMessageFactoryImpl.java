package com.vertex.e4it.usermanagement.util.impl;

import com.vertex.e4it.usermanagement.model.User;
import com.vertex.e4it.usermanagement.util.EmailConfirmationMessage;
import com.vertex.e4it.usermanagement.util.EmailDecoder;
import com.vertex.e4it.usermanagement.util.EmailMessage;
import com.vertex.e4it.usermanagement.util.EmailMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageFactoryImpl implements EmailMessageFactory {

    @Value("${base-confirmation-url}")
    private String baseConfirmationUrl;
    private final EmailDecoder emailDecoder;

    @Autowired
    public EmailMessageFactoryImpl(EmailDecoder emailDecoder) {
        this.emailDecoder = emailDecoder;
    }

    public EmailConfirmationMessage createConfirmationMessage(User user){
        return new EmailConfirmationMessage(user.getEmail(),
                verifyBaseUrl(baseConfirmationUrl) + emailDecoder.encode(user.getEmail()));
    }

    public EmailMessage createEmailMessage(User user) {
        return new EmailMessage(user.getEmail(),
                null,
                "Email Confirmation",
                "Thanks! Your email is confirm.");
    }

    private String verifyBaseUrl(String url){
        return url.endsWith("/") ? url : url + "/";
    }
}
