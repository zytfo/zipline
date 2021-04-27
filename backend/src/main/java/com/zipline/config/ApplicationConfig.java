package com.zipline.config;

import com.zipline.dto.ConfigurationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * The Application config.
 */
@Configuration
public class ApplicationConfig {
    /**
     * Model mapper bean. Used to map entities from dto to model and vice versa.
     *
     * @return the model mapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Configuration configuration dto.
     *
     * @return the configuration dto
     */
    @Bean
    public ConfigurationDTO configuration() {
        return new ConfigurationDTO();
    }

    /**
     * Rest template rest template.
     *
     * @return the rest template
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
