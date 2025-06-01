package com.shorturl.web.controller;


import com.shorturl.domain.entities.ShortUrl;
import com.shorturl.domain.model.CreateShortUrlCmd;
import com.shorturl.domain.service.ShortUrlService;
import com.shorturl.web.dto.CreateSHortUrlForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {

    private final ShortUrlService shortUrlService;

    public HomeController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping("/home")
    public String home(Model model) {

        List<ShortUrl> shortUrls = shortUrlService.findPublicShortUrls();
        model.addAttribute("shortUrls", shortUrls);
        model.addAttribute("baseUrl", "http://localhost:8080/");
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
            model.addAttribute("baseUrl", "http://localhost:8080/");
            return "index";
        }
        try{
            CreateShortUrlCmd createShortUrlCmd = new CreateShortUrlCmd(createSHortUrlForm.originalUrl());
            var shortUrl = shortUrlService.createShortUrl(createShortUrlCmd);
            redirectAttributes.addFlashAttribute("successMessage", "Short url created."+
                    "http://localhost:8080/"+shortUrl.shortKey());

        }catch (Exception e){
            model.addAttribute("errorMessage", "Url is required.");
        }
      return "redirect:/home";
    }
}
