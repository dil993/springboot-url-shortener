package com.shorturl.domain.repositories;

import com.shorturl.domain.entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    List<ShortUrl> findByIsPrivateIsFalseOrderByCreatedAtDesc();

    @Query("SELECT s FROM ShortUrl s WHERE s.isPrivate = false ORDER BY s.createdAt DESC")
    List<ShortUrl> findPublicShortUrls();

}
