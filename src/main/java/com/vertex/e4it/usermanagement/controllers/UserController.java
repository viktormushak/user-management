package com.vertex.e4it.usermanagement.controllers;

import com.vertex.e4it.usermanagement.model.User;
import com.vertex.e4it.usermanagement.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Api(value = "User RestController", description = "Provide operations for managing users")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "View a list of users by collection of id", response = List.class)
    @PostMapping("/allByIds")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<User> getAllUsersByIds(@RequestBody Collection<Integer> ids) {
        return userService.getUserListByIds(ids);
    }

    @ApiOperation(value = "View a list of users by collection of email", response = List.class)
    @PostMapping("/allByEmails")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<User> getAllUsersByEmails(@RequestBody Collection<String> emails) {
        return userService.getUserListByEmails(emails);
    }

    @ApiOperation(value = "View a list of users with pagination. Page number as PathVariable. Page size as RequestParam", response = List.class)
    @GetMapping("/allWithPagination/{number}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<User> getAllUsersWithPagination(@PathVariable("number") Integer pageNumber,
                                               @RequestParam("size") Integer pageSize) {
        return userService.getUserListWithPagination(pageNumber, pageSize);
    }

    @ApiOperation(value = "View a one user by required id", response = User.class)
    @GetMapping("/id/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getById(id);
    }

    @ApiOperation(value = "View a one user by self jwt-token", response = User.class)
    @GetMapping("/userByJwt")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public User getUserByJwt(Authentication authentication) {
        return userService.getByEmail((String) authentication.getPrincipal());
    }

    @ApiOperation(value = "View a one user by email", response = User.class)
    @GetMapping("/userByEmail/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserByEmail(@PathVariable("email") String email) {
        return userService.getByEmail(email);
    }

    @ApiOperation(value = "Create user and return created instance", response = User.class)
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public User createUser(@RequestBody User user){
        return userService.create(user);
    }

    @ApiOperation(value = "Confirm user email and return updated user instance", response = User.class)
    @GetMapping("/confirm/{key}")
    public User confirmUser(@PathVariable("key") String confirmKey){
        return userService.confirmUser(confirmKey);
    }

    @ApiOperation(value = "Update user and return updated user instance", response = User.class)
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public User updateUser(@RequestBody User user){
        return userService.update(user);
    }

    @ApiOperation(value = "Delete user", response = Boolean.class)
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public boolean deleteUser(@PathVariable("id") int id){
        return userService.deleteById(id);
    }
}
