package com.shorturl.domain.service;

import com.shorturl.ApplicationProperties;
import com.shorturl.domain.dto.ShortUrlDTO;
import com.shorturl.domain.entities.ShortUrl;
import com.shorturl.domain.entities.User;
import com.shorturl.domain.model.CreateShortUrlCmd;
import com.shorturl.domain.repositories.ShortUrlRepository;
import com.shorturl.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    private final EntityMapper entityMapper;

    private final ApplicationProperties applicationProperties;

    private final UserRepository userRepository;

    public ShortUrlService(ShortUrlRepository shortUrlRepository, EntityMapper entityMapper, ApplicationProperties applicationProperties, UserRepository userRepository) {
        this.shortUrlRepository = shortUrlRepository;
        this.entityMapper = entityMapper;
        this.applicationProperties = applicationProperties;
        this.userRepository = userRepository;
    }

   public List<ShortUrlDTO> findPublicShortUrls() {
       return shortUrlRepository.findPublicShortUrls().stream()
               .map(entityMapper::convertShortUrlToShortUrlDto)
               .toList();
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
        if(cmd.userId() == null) {
            shortUrl.setCreatedBy(null);
            shortUrl.setIsPrivate(null);
            shortUrl.setExpiresAt(Instant.now().plus(applicationProperties.defaultExpiryDays(), ChronoUnit.DAYS));
        }else {
           shortUrl.setCreatedBy(userRepository.findByEmail(cmd.userId()).orElseThrow());
           shortUrl.setIsPrivate(cmd.isPrivate());
            shortUrl.setExpiresAt(cmd.expirationInDays() != null ? Instant.now().plus(cmd.expirationInDays(), ChronoUnit.DAYS) : null);
        }
        shortUrl.setClickCount(0L);
        shortUrl.setShortKey(shortKey);
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

   @Transactional
    public Optional<ShortUrlDTO> findShortUrlByShortKey(String shortKey, User user) {
     Optional<ShortUrl> shortUrlOptional=  shortUrlRepository.findByShortKey(shortKey);
     if(shortUrlOptional.isEmpty()) {
        return Optional.empty();
     }
     ShortUrl shortUrl = shortUrlOptional.get();
     if(shortUrl.getExpiresAt() != null && shortUrl.getExpiresAt().isBefore(Instant.now())) {
        return Optional.empty();
     }

     if(shortUrl.getIsPrivate() && !shortUrl.getCreatedBy().getEmail().equals(user.getEmail())) {
         return Optional.empty();
     }
     shortUrl.setClickCount(shortUrl.getClickCount() + 1);
     shortUrlRepository.save(shortUrl);
     return shortUrlOptional.map(entityMapper::convertShortUrlToShortUrlDto);
    }
}
