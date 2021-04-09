package com.zipline.controller;

import com.zipline.util.AbstractRestControllerTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;

import static com.zipline.util.LogInUtils.getTokenForLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LikeControllerTest extends AbstractRestControllerTest {

   @Test
   @Ignore
   public void getLikes() throws Exception {
      final String token = getTokenForLogin("test", "password", getMockMvc());

      getMockMvc().perform(get("/api/like/")
         .contentType(MediaType.APPLICATION_JSON)
         .header("Authorization", "Bearer " + token))
         .andExpect(status().isOk());
   }
}
