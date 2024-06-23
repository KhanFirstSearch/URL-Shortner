package org.example.repository;

import org.example.url.ShortenedURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLRepo extends JpaRepository<ShortenedURL, Long> {
    ShortenedURL findByShortenedURL(String shortenedURL);
}