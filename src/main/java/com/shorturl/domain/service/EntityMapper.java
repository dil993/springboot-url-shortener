package com.shorturl.domain.service;

import com.shorturl.domain.dto.ShortUrlDTO;
import com.shorturl.domain.dto.UserDTO;
import com.shorturl.domain.entities.ShortUrl;
import com.shorturl.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public ShortUrl convertShortUrlDtoToShortUrl(ShortUrlDTO shortUrlDTO) {
        if (shortUrlDTO == null) {
            return null;
        }

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(shortUrlDTO.id());
        shortUrl.setShortKey(shortUrlDTO.shortKey());
        shortUrl.setOriginalUrl(shortUrlDTO.originalUrl());
        shortUrl.setExpiresAt(shortUrlDTO.expiresAt());
        shortUrl.setIsPrivate(shortUrlDTO.isPrivate());
        shortUrl.setCreatedBy(convertUserDtoToUser(shortUrlDTO.createdBy()));
        shortUrl.setClickCount(shortUrlDTO.clickCount());
        shortUrl.setCreatedAt(shortUrlDTO.createdAt());
        return shortUrl;
    }

    public ShortUrlDTO convertShortUrlToShortUrlDto(ShortUrl shortUrl) {
        if (shortUrl == null) {
            return null;
        }

        return new ShortUrlDTO(
                shortUrl.getId(),
                shortUrl.getShortKey(),
                shortUrl.getOriginalUrl(),
                shortUrl.getIsPrivate(),
                shortUrl.getExpiresAt(),
                convertUserToUserDto(shortUrl.getCreatedBy()),
                shortUrl.getClickCount(),
                shortUrl.getCreatedAt()
        );
    }

    public User convertUserDtoToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.id());
        user.setEmail(userDTO.email());
        user.setName(userDTO.name());
        user.setRole(userDTO.role());
        user.setCreatedAt(userDTO.createdAt());
        return user;
    }

    public UserDTO convertUserToUserDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}