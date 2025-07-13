package com.desafio.encurtador.url.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.encurtador.url.model.Url;
import com.desafio.encurtador.url.repository.UrlRepository;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public String shortenUrl(String originalUrl) {

        String shortUrl = generateShortUrl();
        Url url = Url.builder()
        .originalUrl(originalUrl)
        .shortUrl(shortUrl)
        .expirationDate(LocalDateTime.now().plusDays(12)).build();

        urlRepository.save(url);

        return shortUrl;
    }

    private String generateShortUrl() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder shortUrl = new StringBuilder();
        Random random = new Random();
        int length = 5 + random.nextInt(6);
        for(int i = 0; i < length; i++ ){
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortUrl.toString();
    }

    public Optional<Url> getOriginalUrl(String shortUrl) {
        Optional<Url> urOptional = urlRepository.findByShortUrl(shortUrl);

        if (urOptional.isPresent()) {
            Url url = urOptional.get();
            if (url.getExpirationDate().isAfter(LocalDateTime.now())) {
                return Optional.of(url);
            } else {
                urlRepository.delete(url);
            }
        }
        return Optional.empty();
    }

}
