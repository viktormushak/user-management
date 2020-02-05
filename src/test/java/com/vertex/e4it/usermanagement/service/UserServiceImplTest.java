package com.vertex.e4it.usermanagement.service;

import com.vertex.e4it.usermanagement.data.jpa.UserRepository;
import com.vertex.e4it.usermanagement.model.User;
import com.vertex.e4it.usermanagement.model.UserStatus;
import com.vertex.e4it.usermanagement.service.impl.UserServiceImpl;
import com.vertex.e4it.usermanagement.util.EmailDecoder;
import com.vertex.e4it.usermanagement.util.EmailMessageFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private EmailDecoder emailDecoder;
    @Autowired
    private EmailMessageFactory emailMessageFactory;
    @MockBean
    private EmailService emailService;
    private UserRepository userRepository;
    private UserService userService;
    private List<User> testUserList;
    private User testUser;


    @Before
    public void setUp(){
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository, Mockito.mock(EmailService.class), emailDecoder, emailMessageFactory);
        testUser = User.builder()
                .id(1)
                .email("testEmail")
                .name("testName")
                .surname("testSurname")
                .build();
        testUserList = Collections.singletonList(testUser);
    }

    @Test
    public void getUserListByIdsUsersExist() {
        when(userRepository.findAllById(Arrays.asList(1, 2, 3))).thenReturn(testUserList);
        List<User> users = userService.getUserListByIds(Arrays.asList(1, 2, 3));
        assertEquals(testUserList, users);
    }

    @Test
    public void getUserListByIdsIfUsersNotExist() {
        when(userRepository.findAllById(Arrays.asList(1, 2, 3))).thenReturn(Collections.emptyList());
        List<User> users = userService.getUserListByIds(Arrays.asList(1, 2, 3));
        assertTrue(users.isEmpty());
    }

    @Test
    public void getUserListByEmailsUsersExist() {
        when(userRepository.findAllByEmailIn(Collections.singletonList("testEmail"))).thenReturn(testUserList);
        List<User> users = userService.getUserListByEmails(Collections.singletonList("testEmail"));
        assertEquals(testUserList, users);
    }


    @Test
    public void getUserListByEmailsIfUsersNotExist() {
        when(userRepository.findAllByEmailIn(Collections.singletonList("testEmail"))).thenReturn(Collections.emptyList());
        List<User> users = userService.getUserListByEmails(Collections.singletonList("testEmail"));
        assertTrue(users.isEmpty());
    }

    @Test
    public void getUserListWithPaginationIfPageIsNull(){
        when(userRepository.findAll(PageRequest.of(1, 10))).thenReturn(null);
        List<User> users = userService.getUserListWithPagination(1, 10);
        assertEquals(Collections.emptyList(), users);
    }

    @Test
    public void getByIdIfUserExist() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        User foundUser = userService.getById(1);
        assertEquals(testUser, foundUser);
    }

    @Test
    public void getByIdIfUserNotExist() {
        when(userRepository.getOne(1)).thenReturn(null);
        User foundUser = userService.getById(1);
        assertEquals(User.EMPTY_USER, foundUser);
    }

    @Test
    public void getByEmailIfUserExist() {
        when(userRepository.findByEmail("testEmail")).thenReturn(testUser);
        User foundUser = userService.getByEmail("testEmail");
        assertEquals(testUser, foundUser);
    }

    @Test
    public void getByEmailIfUserNotExist() {
        when(userRepository.findByEmail("testEmail")).thenReturn(null);
        User foundUser = userService.getByEmail("testEmail");
        assertEquals(User.EMPTY_USER, foundUser);
    }

    @Test
    public void createIfUserExist() {
        when(userRepository.findByEmail("testEmail")).thenReturn(testUser);
        when(userRepository.saveAndFlush(testUser)).thenReturn(testUser);
        User createdUser = userService.create(testUser);
        assertEquals(User.EMPTY_USER, createdUser);
    }

    @Test
    public void createIfUserNotExist() {
        when(userRepository.findByEmail("testEmail")).thenReturn(null);
        when(userRepository.saveAndFlush(testUser)).thenReturn(testUser);
        User createdUser = userService.create(testUser);
        assertEquals(testUser, createdUser);
    }

    @Test
    public void confirmIfUserExist() {
        testUser.setStatus(UserStatus.NEW);
        when(userRepository.findByEmail("testEmail")).thenReturn(testUser);
        when(userRepository.saveAndFlush(testUser)).thenReturn(testUser);
        User confirmedUser = userService.confirmUser(Base64.getEncoder().encodeToString("testEmail".getBytes()));
        assertEquals(UserStatus.CONFIRMED, confirmedUser.getStatus());
    }

    @Test
    public void updateIfUserExist() {
        when(userRepository.saveAndFlush(testUser)).thenReturn(testUser);
        testUser.setName("newName");
        User updatedUser = userService.update(testUser);
        assertEquals(testUser, updatedUser);
    }

    @Test
    public void deleteByIdIfUserExist() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(userRepository.saveAndFlush(testUser)).thenReturn(testUser);
        userService.deleteById(1);
        User deletedUser = userService.getById(1);
        assertEquals(UserStatus.DELETED, deletedUser.getStatus());
    }
}