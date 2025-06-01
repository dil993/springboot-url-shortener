package com.shorturl.domain.dto;

import org.springframework.core.serializer.Serializer;

import java.io.Serializable;
import java.time.Instant;

public record ShortUrlDTO(
    Long id,
    String shortKey,
    String originalUrl,
    Boolean isPrivate,
    Instant expiresAt,
    UserDTO createdBy,
    Long clickCount,
    Instant createdAt
) implements Serializable {}
