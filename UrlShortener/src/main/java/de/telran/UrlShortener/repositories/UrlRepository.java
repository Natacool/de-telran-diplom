package de.telran.UrlShortener.repositories;

import de.telran.UrlShortener.entities.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlEntity,Long> {
}
