package com.shorturl.web.controller;


import com.shorturl.ApplicationProperties;
import com.shorturl.domain.dto.ShortUrlDTO;
import com.shorturl.domain.entities.ShortUrl;
import com.shorturl.domain.model.CreateShortUrlCmd;
import com.shorturl.domain.service.ShortUrlService;
import com.shorturl.web.dto.CreateSHortUrlForm;
import com.shorturl.domain.exception.ShortUrlNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final ShortUrlService shortUrlService;

    private final ApplicationProperties properties;

    public HomeController(ShortUrlService shortUrlService, ApplicationProperties properties) {
        this.shortUrlService = shortUrlService;
        this.properties = properties;
    }

    @GetMapping("/home")
    public String home(Model model) {

        List<ShortUrl> shortUrls = shortUrlService.findPublicShortUrls();
        model.addAttribute("shortUrls", shortUrls);
        model.addAttribute("baseUrl", properties.baseUrl());
        model.addAttribute("createShortUrlForm", new CreateSHortUrlForm(""));
        return "index";
    }


    @PostMapping("/short-urls")
    public String createShortUrl(@ModelAttribute("createShortUrlForm") @Valid CreateSHortUrlForm createSHortUrlForm,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            List<ShortUrl> shortUrls = shortUrlService.findPublicShortUrls();
            model.addAttribute("shortUrls", shortUrls);
            model.addAttribute("baseUrl", properties.baseUrl());
            return "index";
        }
        try{
            CreateShortUrlCmd createShortUrlCmd = new CreateShortUrlCmd(createSHortUrlForm.originalUrl());
            var shortUrl = shortUrlService.createShortUrl(createShortUrlCmd);
            redirectAttributes.addFlashAttribute("successMessage", "Short url created."+
                   properties.baseUrl()+shortUrl.shortKey());

        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Url is required.");
        }
      return "redirect:/home";
    }


    @GetMapping("/s/{shortKey}")
    public String redirect(@PathVariable("shortKey") String shortKey) {

        Optional<ShortUrlDTO> shortUrlOptional = shortUrlService.findShortUrlByShortKey(shortKey);
        if (shortUrlOptional.isEmpty()) {
           throw new ShortUrlNotFoundException("Short url not found.");
        }
        String originalUrl = shortUrlOptional.get().originalUrl();
        return "redirect:"+originalUrl;
    }
}
