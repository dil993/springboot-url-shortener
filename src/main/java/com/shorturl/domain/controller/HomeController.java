package com.shorturl.domain.controller;


import com.shorturl.domain.entities.ShortUrl;
import com.shorturl.domain.service.ShortUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller("/")
public class HomeController {

   private final ShortUrlService shortUrlService;

    public HomeController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping("/home")
    public String home(Model model) {

      List<ShortUrl> shortUrls = shortUrlService.findPublicShortUrls();
      model.addAttribute("shortUrls", shortUrls);
        return "index";
    }


}
