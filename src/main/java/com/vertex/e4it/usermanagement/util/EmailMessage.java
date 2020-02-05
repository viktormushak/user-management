package com.vertex.e4it.usermanagement.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailMessage {

    private String to, cc, subject, message;
}
