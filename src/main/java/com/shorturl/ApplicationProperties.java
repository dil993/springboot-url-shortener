package com.shorturl;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties("app")
public record ApplicationProperties(
        @NotBlank
        //@DefaultValue("http://localhost:8080")
        String baseUrl,
        @DefaultValue("30")
        @Min(1)
        @Max(365)
        int defaultExpiryDays,
        //@DefaultValue("false")
        boolean validateUrl
) {
}
