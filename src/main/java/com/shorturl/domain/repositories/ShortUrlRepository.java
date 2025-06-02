package com.shorturl.domain.repositories;

import com.shorturl.domain.entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    List<ShortUrl> findByIsPrivateIsFalseOrderByCreatedAtDesc();

    @Query("SELECT s FROM ShortUrl s WHERE s.isPrivate = false ORDER BY s.createdAt DESC")
    List<ShortUrl> findPublicShortUrls();

    boolean existsByShortKey(String shortKey);

    Optional<ShortUrl> findByShortKey(String shortKey);
}
