package com.vertex.e4it.usermanagement.util.impl;

import com.vertex.e4it.usermanagement.util.EmailDecoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Base64EmailDecoderImpl implements EmailDecoder {

    @Override
    public String encode(String rawEmail) {
        return Base64.getEncoder().encodeToString(rawEmail.getBytes());
    }

    @Override
    public String decode(String encryptedEmail) {
        String decode = "";
        byte[] bytes = Base64.getDecoder().decode(encryptedEmail);
        for (byte b : bytes) {
            decode = decode.concat(String.valueOf((char) b));
        }
        return decode;
    }
}
