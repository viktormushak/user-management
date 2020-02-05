package com.vertex.e4it.usermanagement.data.jpa;

import com.vertex.e4it.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    List<User> findAllByEmailIn(Collection<String> emails);
}
