package org.example.service;

import org.example.exception.URLNotFoundException;
import org.example.url.ShortenedURL;
import org.example.repository.URLRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class URLService {
    @Autowired
    private URLRepo urlRepository;

    private final int URL_EXPIRY_YEARS = 100;


    public String createShortenedURL(String originalURL, String customAlias) {
        String shortenedURL = (customAlias != null && customAlias.length() <= 16) ? customAlias : generateUniqueHash();
        ShortenedURL url = new ShortenedURL();
        url.setOriginalURL(originalURL);
        url.setShortenedURL(shortenedURL);
        url.setCreatedAt(LocalDateTime.now());
        url.setExpiryDate(LocalDateTime.now().plusYears(URL_EXPIRY_YEARS));
        urlRepository.save(url);
        return shortenedURL;
    }

    public String getOriginalURL(String shortenedUrl) {
        ShortenedURL url = urlRepository.findByShortenedURL(shortenedUrl);
        if (url != null && url.getExpiryDate().isAfter(LocalDateTime.now())) {
            return url.getOriginalURL();
        } else {
            throw new URLNotFoundException("URL was not found or has expired.");
        }
    }

    private String generateUniqueHash() {
        return Long.toHexString(System.currentTimeMillis());
    }
}