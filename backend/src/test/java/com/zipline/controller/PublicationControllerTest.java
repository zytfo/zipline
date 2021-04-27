package com.zipline.controller;

import com.zipline.util.AbstractRestControllerTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;

import static com.zipline.util.LogInUtils.getTokenForLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PublicationControllerTest extends AbstractRestControllerTest {

   @Test
   @Ignore
   public void getPublications() throws Exception {
      final String token = getTokenForLogin("test", "password", getMockMvc());

      getMockMvc().perform(get("/api/publication/")
         .contentType(MediaType.APPLICATION_JSON)
         .header("Authorization", "Bearer " + token))
         .andExpect(status().isOk());
   }
}
