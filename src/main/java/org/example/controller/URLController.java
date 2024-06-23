package org.example.controller;

import org.example.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class URLController {
    @Autowired
    private URLService urlService;

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String originalURL, @RequestParam(required = false) String customAlias) {
        return urlService.createShortenedURL(originalURL, customAlias);
    }

    @GetMapping("/{shortenedURL}")
    public String redirect(@PathVariable String shortenedURL) {
        return urlService.getOriginalURL(shortenedURL);
    }
}

