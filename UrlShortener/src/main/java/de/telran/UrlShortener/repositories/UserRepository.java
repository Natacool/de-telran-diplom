package de.telran.UrlShortener.repositories;

import de.telran.UrlShortener.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
}
