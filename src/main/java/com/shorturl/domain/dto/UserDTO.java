package com.shorturl.domain.dto;

import com.shorturl.domain.model.Role;

import java.io.Serializable;
import java.time.Instant;

public record UserDTO(
    Long id,
    String email,
    String name,
    Role role,
    Instant createdAt
) implements Serializable {}
