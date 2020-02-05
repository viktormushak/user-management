package com.vertex.e4it.usermanagement.service.impl;

import com.vertex.e4it.usermanagement.data.jpa.UserRepository;
import com.vertex.e4it.usermanagement.model.User;
import com.vertex.e4it.usermanagement.model.UserStatus;
import com.vertex.e4it.usermanagement.service.EmailService;
import com.vertex.e4it.usermanagement.service.UserService;
import com.vertex.e4it.usermanagement.util.EmailDecoder;
import com.vertex.e4it.usermanagement.util.EmailMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.vertex.e4it.usermanagement.model.User.EMPTY_USER;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final EmailDecoder emailDecoder;
    private final EmailMessageFactory emailMessageFactory;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           EmailService emailService,
                           EmailDecoder emailDecoder,
                           EmailMessageFactory emailMessageFactory) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.emailDecoder = emailDecoder;
        this.emailMessageFactory = emailMessageFactory;
    }

    public List<User> getUserListByIds(Collection<Integer> ids) {
        return Optional.of(userRepository.findAllById(ids))
                .orElse(Collections.emptyList());
    }

    public List<User> getUserListByEmails(Collection<String> emails) {
        return Optional.of(userRepository.findAllByEmailIn(emails))
                .orElse(Collections.emptyList());
    }

    public List<User> getUserListWithPagination(int pageNumber, int pageSize) {
        return Optional.ofNullable(userRepository.findAll(PageRequest.of(pageNumber, pageSize)))
                .map(Page::getContent)
                .orElse(Collections.emptyList());
    }

    public User getById(int id) {
        return userRepository.findById(id).orElse(EMPTY_USER);
    }

    public User getByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email))
                .orElse(EMPTY_USER);
    }

    public User create(User user) {
        User existedUser = this.getByEmail(user.getEmail());
        return EMPTY_USER.equals(existedUser) ? finishCreating(user) : EMPTY_USER;
    }

    private User finishCreating(User user) {
        user.setStatus(UserStatus.NEW);
        User created = this.update(user);
        emailService.sendEmailConfirmation(emailMessageFactory.createConfirmationMessage(created));
        return created;
    }

    public User confirmUser(String confirmKey) {
        String email = emailDecoder.decode(confirmKey);
        User unconfirmedUser = this.getByEmail(email);
        return EMPTY_USER.equals(unconfirmedUser) ? EMPTY_USER : finishConfirmation(unconfirmedUser);
    }

    private User finishConfirmation(User user) {
        if (user.getStatus().equals(UserStatus.NEW)) {
            user.setStatus(UserStatus.CONFIRMED);
        }
        User updated = this.update(user);
        emailService.sendEmail(emailMessageFactory.createEmailMessage(updated));
        return updated;
    }

    public User update(User user) {
        return userRepository.saveAndFlush(user);
    }

    public boolean deleteById(int id) {
        boolean result = false;
        User user = getById(id);
        if(!EMPTY_USER.equals(user)) {
            user.setStatus(UserStatus.DELETED);
            User updated = this.update(user);
            result = !EMPTY_USER.equals(updated);
        }
        return result;
    }
}
