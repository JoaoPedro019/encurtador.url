package com.desafio.encurtador.url.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.encurtador.url.model.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    
    Optional<Url> findByShortUrl(String shortUrl);

}
