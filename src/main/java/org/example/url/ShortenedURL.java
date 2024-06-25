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
    @Column(nullable = false, unique = true) //Prevents duplicated URLs
    private String shortenedURL;

    @Column(nullable = false) //URLs should NOT be null
    private String originalURL;

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime expiryDate;
}

