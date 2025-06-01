package com.shorturl.domain.service;

import com.shorturl.domain.dto.ShortUrlDTO;
import com.shorturl.domain.entities.ShortUrl;
import com.shorturl.domain.model.CreateShortUrlCmd;
import com.shorturl.domain.repositories.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    private final EntityMapper entityMapper;

    public ShortUrlService(ShortUrlRepository shortUrlRepository, EntityMapper entityMapper) {
        this.shortUrlRepository = shortUrlRepository;
        this.entityMapper = entityMapper;
    }

   public List<ShortUrl> findPublicShortUrls() {
       return shortUrlRepository.findPublicShortUrls();
   }

   public ShortUrlDTO createShortUrl(CreateShortUrlCmd cmd) {

        String shortKey = generateShortKey();
        var shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(cmd.originalUrl());
        shortUrl.setCreatedAt(Instant.now());
        shortUrl.setIsPrivate(false);
        shortUrl.setClickCount(0L);
        shortUrl.setShortKey(shortKey);
        shortUrl.setExpiresAt(Instant.now().plus(30, ChronoUnit.DAYS));
        shortUrl.setCreatedBy(null);
        shortUrlRepository.save(shortUrl);
       return entityMapper.convertShortUrlToShortUrlDto(shortUrl);
   }

   private String generateShortKey() {
       String shortkey;
      do{
           shortkey = RandomUtils.generateRandomShortKey();
      }while (shortUrlRepository.existsByShortKey(shortkey));
      return shortkey;
   }
}
