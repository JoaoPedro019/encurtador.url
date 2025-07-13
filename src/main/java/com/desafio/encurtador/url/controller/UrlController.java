package com.desafio.encurtador.url.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.encurtador.url.model.Url;
import com.desafio.encurtador.url.services.UrlService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("url")
public class UrlController {

    @Autowired
    private UrlService service;

    @PostMapping("shorten")
    public ResponseEntity<Map<String, String>> postMethodName(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        if (Objects.isNull(originalUrl)) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "URL não pode ser nula");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        String shortUrl = service.shortenUrl(originalUrl);
        Map<String, String> response = new HashMap<String, String>();

        response.put("url", "https://xxx.com/"+shortUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Object> getMethodName(@PathVariable String shortUrl) {
        Optional<Url> urlOptional = service.getOriginalUrl(shortUrl);
        if (urlOptional.isPresent()) {
            Url url = urlOptional.get();
            System.out.println("Redirecionando para: "+url.getOriginalUrl());
            return ResponseEntity.status(302).location(URI.create(url.getOriginalUrl())).build();
        }

        System.out.println("URL não encontrada ou expirada: "+shortUrl);
        return ResponseEntity.notFound().build();

    }
    
    
}
