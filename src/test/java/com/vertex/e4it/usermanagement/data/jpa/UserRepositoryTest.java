package com.vertex.e4it.usermanagement.data.jpa;

import com.vertex.e4it.usermanagement.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    private User testUser;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        testUser = User.builder()
                .email("test@email.com")
                .name("testName")
                .surname("testSurname")
                .build();
        entityManager.persist(testUser);
        entityManager.flush();
    }

    @Test
    public void findByIdIfUserExist() {
        userRepository.findById(testUser.getId())
                .ifPresent(foundUser -> assertEquals(testUser, foundUser));
    }

    @Test
    public void findByIdIfUserNotExist() {
        assertFalse(userRepository.findById(567).isPresent());
    }

    @Test
    public void findAllByIdIfUsersExist(){
        List<User> users = userRepository.findAllById(Collections.singletonList(testUser.getId()));
        assertFalse(users.isEmpty());
        assertTrue(users.contains(testUser));
    }

    @Test
    public void findAllByIdIfUserNotExist(){
        List<User> users = userRepository.findAllById(Collections.singletonList(567));
        assertTrue(users.isEmpty());
    }

    @Test
    public void findAllWithPagination(){
        List<User> users = userRepository.findAll(PageRequest.of(0,10)).getContent();
        assertFalse(users.isEmpty());
        assertTrue(users.contains(testUser));
    }

    @Test
    public void findByEmailIfUserExist() {
        User foundUser = userRepository.findByEmail(testUser.getEmail());
        assertEquals(testUser, foundUser);
    }

    @Test
    public void findByEmailIfUserNotExist() {
        User foundUser = userRepository.findByEmail("fakeEmail");
        assertNull(foundUser);
    }

    @Test
    public void findAllByEmailInIfUsersExist(){
        List<User> users = userRepository.findAllByEmailIn(Collections.singletonList(testUser.getEmail()));
        assertFalse(users.isEmpty());
        assertTrue(users.contains(testUser));
    }

    @Test
    public void findAllByEmailInIfUserNotExist(){
        List<User> users = userRepository.findAllByEmailIn(Collections.singletonList("fakeEmail"));
        assertTrue(users.isEmpty());
    }
}