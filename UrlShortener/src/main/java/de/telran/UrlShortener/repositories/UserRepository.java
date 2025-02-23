package de.telran.UrlShortener.repositories;

import de.telran.UrlShortener.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<UserEntity,Long> {
    @Query(value = "SELECT * FROM Users us WHERE us.Email=?1", nativeQuery = true)
    public UserEntity findUserByEmail(String email);

}
