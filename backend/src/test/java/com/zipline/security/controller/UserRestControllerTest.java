package com.zipline.security.controller;

import com.zipline.util.AbstractRestControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.zipline.util.LogInUtils.getTokenForLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRestControllerTest extends AbstractRestControllerTest {

   @Before
   public void setUp() {
      SecurityContextHolder.clearContext();
   }

   @Test
   public void getActualUserForUserWithToken() throws Exception {
      final String token = getTokenForLogin("test", "testtest", getMockMvc());

      getMockMvc().perform(get("/api/auth/user")
         .contentType(MediaType.APPLICATION_JSON)
         .header("Authorization", "Bearer " + token))
         .andExpect(status().isOk())
         .andExpect(content().json(
            "{\n" +
               "    \"id\": 1,\n" +
               "    \"username\": \"test\",\n" +
               "    \"email\": \"test@test\",\n" +
               "    \"authorities\": [\n" +
               "        {\n" +
               "            \"authority\": \"ROLE_USER\"\n" +
               "        }\n" +
               "    ],\n" +
               "    \"enabled\": true,\n" +
               "    \"accountNonExpired\": true,\n" +
               "    \"accountNonLocked\": true,\n" +
               "    \"credentialsNonExpired\": true\n" +
               "}"
         ));
   }

   @Test
   public void getActualUserForUserWithoutToken() throws Exception {
      getMockMvc().perform(get("/api/auth/user")
         .contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isUnauthorized());
   }
}
