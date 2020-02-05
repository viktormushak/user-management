package com.vertex.e4it.usermanagement.util;

public interface EmailDecoder {

    String encode(String rawEmail);

    String decode(String encodedEmail);
}
