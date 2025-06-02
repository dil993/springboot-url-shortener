package com.shorturl.domain.service;

import com.shorturl.ApplicationProperties;
import com.shorturl.domain.dto.ShortUrlDTO;
import com.shorturl.domain.entities.ShortUrl;
import com.shorturl.domain.model.CreateShortUrlCmd;
import com.shorturl.domain.repositories.ShortUrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    private final EntityMapper entityMapper;

    private final ApplicationProperties applicationProperties;


    public ShortUrlService(ShortUrlRepository shortUrlRepository, EntityMapper entityMapper, ApplicationProperties applicationProperties) {
        this.shortUrlRepository = shortUrlRepository;
        this.entityMapper = entityMapper;
        this.applicationProperties = applicationProperties;
    }

   public List<ShortUrl> findPublicShortUrls() {
       return shortUrlRepository.findPublicShortUrls();
   }

   @Transactional
   public ShortUrlDTO createShortUrl(CreateShortUrlCmd cmd) {

        if(applicationProperties.validateUrl()){
            boolean isValidUrl = UrlValidatorService.isValidUrl(cmd.originalUrl());
            if(!isValidUrl) throw new IllegalArgumentException("Invalid url."+ cmd.originalUrl());
        }
        String shortKey = generateShortKey();
        var shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(cmd.originalUrl());
        shortUrl.setCreatedAt(Instant.now());
        shortUrl.setIsPrivate(false);
        shortUrl.setClickCount(0L);
        shortUrl.setShortKey(shortKey);
        shortUrl.setExpiresAt(Instant.now().plus(applicationProperties.defaultExpiryDays(), ChronoUnit.DAYS));
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
