package com.shorturl.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateSHortUrlForm(
        @NotBlank(message = "Original URL is required")
        String originalUrl) {
}
