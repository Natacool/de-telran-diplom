package de.telran.UrlShortener.repositories;

import de.telran.UrlShortener.entities.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlEntity,Long> {
/*
    @Query("SELECT ce FROM CategoriesEntity ce WHERE ce.name=?1")
    CategoriesEntity findByName(String name);

    // Чистый SQL
    @Query(value = "SELECT * FROM Categories ce WHERE ce.Name=?1", nativeQuery = true)
    CategoriesEntity findByNameNative(String name);
*/
    // getGeneratedUrls
    // getClickedUrls

    // getGeneratedUrl
    // findByLongUrl

    // getRedirectUrl
    // findByShortUrl

    // findByShortUrl or findById???
    // updateCleanTimer
    // deleteUrl
}
