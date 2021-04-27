package com.zipline.util;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public final class LogInUtils {

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

   private LogInUtils() {
   }

   public static String getTokenForLogin(String username, String password, MockMvc mockMvc) throws Exception {
      String content = mockMvc.perform(post("/api/auth/login")
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"" + password + "\", \"username\": \"" + username + "\"}"))
         .andReturn()
         .getResponse()
         .getContentAsString();
      AuthenticationResponse authResponse = OBJECT_MAPPER.readValue(content, AuthenticationResponse.class);
      return authResponse.getToken();
   }

   @Getter
   @Setter
   @NoArgsConstructor
   private static class AuthenticationResponse {
      @JsonAlias("token")
      private String token;

      @JsonAlias("roles")
      private List<String> roles;

      @JsonAlias("type")
      private String type;

      @JsonAlias("id")
      private Long userId;

      @JsonAlias("username")
      private String userName;

      @JsonAlias("email")
      private String email;
   }
}
