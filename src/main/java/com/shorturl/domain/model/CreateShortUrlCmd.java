package com.shorturl.domain.model;

public record CreateShortUrlCmd(String originalUrl,boolean isPrivate,Integer expirationInDays,String userId) {
}
