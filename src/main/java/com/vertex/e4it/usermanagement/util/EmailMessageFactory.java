package com.vertex.e4it.usermanagement.util;

import com.vertex.e4it.usermanagement.model.User;

public interface EmailMessageFactory {

    EmailConfirmationMessage createConfirmationMessage(User user);

    EmailMessage createEmailMessage(User user);
}
