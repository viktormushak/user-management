package com.vertex.e4it.usermanagement.service;

import com.vertex.e4it.usermanagement.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {

    List<User> getUserListByIds(Collection<Integer> ids);
    List<User> getUserListByEmails(Collection<String> emails);
    List<User> getUserListWithPagination(int pageNumber, int pageSize);
    User getById(int id);
    User getByEmail(String email);
    User create(User user);
    User confirmUser(String confirmKey);
    User update(User user);
    boolean deleteById(int id);
}
