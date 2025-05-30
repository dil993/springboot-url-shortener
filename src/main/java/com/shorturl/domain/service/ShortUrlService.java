package com.shorturl.domain.service;

import com.shorturl.domain.entities.ShortUrl;
import com.shorturl.domain.repositories.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;


    public ShortUrlService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

   public List<ShortUrl> findPublicShortUrls() {
       return shortUrlRepository.findPublicShortUrls();
   }

}
