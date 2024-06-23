package org.example.url;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ShortenedURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalURL;
    private String shortenedURL;
    private LocalDateTime createdAt;
    private LocalDateTime expiryDate;
}

