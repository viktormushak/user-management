package com.vertex.e4it.usermanagement.service;

import com.vertex.e4it.usermanagement.util.EmailConfirmationMessage;
import com.vertex.e4it.usermanagement.util.EmailMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("${email-service.name}")
public interface EmailService {

    @PutMapping("/email")
    String sendEmail(@RequestBody EmailMessage message);

    @PutMapping("/email/confirmation")
    void sendEmailConfirmation(@RequestBody EmailConfirmationMessage message);

}
