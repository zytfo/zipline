package com.zipline.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * The type Configuration dto.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ios")
public class ConfigurationDTO implements Serializable {
    private static final long serialVersionUID = 8219454436837226096L;
    private String color;
}
