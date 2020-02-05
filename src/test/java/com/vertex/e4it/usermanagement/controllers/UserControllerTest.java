package com.vertex.e4it.usermanagement.controllers;


import com.google.gson.Gson;
import com.vertex.e4it.usermanagement.model.User;
import com.vertex.e4it.usermanagement.service.EmailService;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private User testUser;
    private String testUserToken;
    private String testAdminToken;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Before
    public void setUp(){
        testUser = User.builder()
                .id(1)
                .email("testUser@mail.com")
                .name("testName")
                .surname("testSurname")
                .build();

        testUserToken = generateToken("testUser@mail.com", "userPassword");
        testAdminToken = generateToken("testAdmin@mail.com", "adminPassword");
    }

    private String generateToken(String username, String password) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);
        params.add("scope", "authorization");

        String resultString = null;

        try {
            resultString = mockMvc.perform(post("/oauth/token")
                    .params(params)
                    .header("Authorization",
                            "Basic " + Base64.encodeBase64String("test-web-client:testClientPassword".getBytes()))
                    .accept("application/json;charset=UTF-8"))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JacksonJsonParser().parseMap(resultString).get("access_token").toString();
    }

    @Test
    public void getUserByIdUserAccessRequestIsOk() throws Exception {
        mockMvc.perform(get("/users/id/1")
                .header("Authorization", "Bearer " + testUserToken))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByIdAdminAccessRequestIsOk() throws Exception {
        mockMvc.perform(get("/users/id/1")
                .header("Authorization", "Bearer " + testAdminToken))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByIdRequestWithoutAuthorization() throws Exception {
        mockMvc.perform(get("/users/id/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getUserByJwtUserAccessRequestIsOk() throws Exception {
        mockMvc.perform(get("/users/userByJwt")
                .header("Authorization", "Bearer " + testUserToken))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByJwtAdminAccessRequestIsOk() throws Exception {
        mockMvc.perform(get("/users/userByJwt")
                .header("Authorization", "Bearer " + testAdminToken))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByJwtWithoutAuthorization() throws Exception {
        mockMvc.perform(get("/users/userByJwt"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getUserByEmailUserAccessRequestIsOk() throws Exception {
        mockMvc.perform(get("/users/userByEmail/testEmail")
                .header("Authorization", "Bearer " + testUserToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getUserByEmailAdminAccessRequestIsOk() throws Exception {
        mockMvc.perform(get("/users/userByEmail/testEmail")
                .header("Authorization", "Bearer " + testAdminToken))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByEmailWithoutAuthorization() throws Exception {
        mockMvc.perform(get("/users/userByEmail/testEmail"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createUserUserAccessRequestOk() throws Exception {
        mockMvc.perform(post("/users/create")
                .header("Authorization", "Bearer " + testUserToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(testUser)))
                .andExpect(status().isOk());
    }

    @Test
    public void createUserAdminAccessRequestOk() throws Exception {
        mockMvc.perform(post("/users/create")
                .header("Authorization", "Bearer " + testAdminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(testUser)))
                .andExpect(status().isOk());
    }

    @Test
    public void createUserWithoutAuthorization() throws Exception {
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(testUser)))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void updateUserUserAccessRequestIsOk() throws Exception {
        mockMvc.perform(put("/users/update")
                .header("Authorization", "Bearer " + testUserToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(testUser)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserAdminAccessRequestIsOk() throws Exception {
        mockMvc.perform(put("/users/update")
                .header("Authorization", "Bearer " + testAdminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(testUser)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserWithoutAuthorization() throws Exception {
        mockMvc.perform(put("/users/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(testUser)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteUserUserAccessRequestOk() throws Exception {
        mockMvc.perform(delete("/users/delete/1")
                .header("Authorization", "Bearer " + testUserToken))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserAdminAccessRequestOk() throws Exception {
        mockMvc.perform(delete("/users/delete/1")
                .header("Authorization", "Bearer " + testAdminToken))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserWithoutAuthorization() throws Exception {
        mockMvc.perform(delete("/users/delete/1"))
                .andExpect(status().isUnauthorized());
    }
}