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
        String shortenedURL;

        // Check if alias is null, if so, generate custom hash.
        if (customAlias != null && !customAlias.trim().isEmpty()) {
            if (urlRepository.findByShortenedURL(customAlias) != null) {
                throw new IllegalArgumentException("Custom alias already in use.");
            }
            shortenedURL = customAlias;
        } else {
            shortenedURL = generateUniqueHash();
        }

        // Ensure the shortened URL is not empty
        if (shortenedURL == null || shortenedURL.trim().isEmpty()) {
            throw new IllegalArgumentException("The shortened URL is empty.");
        }

        // Create and save the new shortened URL
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
        if (url == null || url.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new URLNotFoundException("URL was not found or has expired.");
        }
        return url.getOriginalURL();
    }

    private String generateUniqueHash() {
        return Long.toHexString(System.currentTimeMillis());
    }
}
