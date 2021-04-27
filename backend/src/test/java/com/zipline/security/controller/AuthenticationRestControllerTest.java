package com.zipline.security.controller;

import com.zipline.util.AbstractRestControllerTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationRestControllerTest extends AbstractRestControllerTest {
   @Test
   public void successfulAuthenticationWithUser() throws Exception {
      getMockMvc().perform(post("/api/auth/login")
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"testtest\", \"username\": \"test\"}"))
         .andExpect(status().isOk())
         .andExpect(content().string(containsString("token")));
   }

   /*@Test
   public void successfulAuthenticationWithAdmin() throws Exception {
      getMockMvc().perform(post("/api/auth/login")
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"testtest\", \"username\": \"test\"}"))
         .andExpect(status().isOk())
         .andExpect(content().string(containsString("token")));
   }

   @Test
   public void unsuccessfulAuthenticationWithDisabled() throws Exception {
      getMockMvc().perform(post("/api/auth/login")
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"testtest\", \"username\": \"disabled\"}"))
         .andExpect(status().isUnauthorized())
         .andExpect(content().string(not(containsString("token"))));
   }*/

   @Test
   public void unsuccessfulAuthenticationWithWrongPassword() throws Exception {
      getMockMvc().perform(post("/api/auth/login")
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"wrong\", \"username\": \"test\"}"))
         .andExpect(status().isUnauthorized())
         .andExpect(content().string(not(containsString("token"))));
   }

   @Test
   public void unsuccessfulAuthenticationWithNotExistingUser() throws Exception {
      getMockMvc().perform(post("/api/auth/login")
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"password\", \"username\": \"not_existing\"}"))
         .andExpect(status().isUnauthorized())
         .andExpect(content().string(not(containsString("token"))));
   }
}
