package de.telran.UrlShortener.repositories;

import de.telran.UrlShortener.entities.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UrlRepository extends JpaRepository<UrlEntity,Long> {
    @Query(value = "SELECT * FROM Urls ur WHERE ur.ShortUrl=?1", nativeQuery = true)
    public UrlEntity findByShortUrlNative(String shortUrl);

    @Query(value = "SELECT * FROM Urls ur WHERE ur.LongUrl=?1", nativeQuery = true)
    public UrlEntity findByLongUrlNative(String longUrl);

    @Query(value = "SELECT * FROM Urls ur "
            + " WHERE ur.LongUrl=?1",
            nativeQuery = true)
    public List<UrlEntity> findGeneratedUrlsNative(Long periodDays, Boolean descent);

    @Query(value = "SELECT * FROM Urls ur "
            + " WHERE DATEDIFF(curdate(), ur.CreatedAt) < (:periodDays) "
            + " ORDER BY (:orderBy) (:descent)"
            + " (:limitTop)",
            nativeQuery = true)
    public List<UrlEntity> findGeneratedUrlsNative(String periodDays, String orderBy, String descent, String limitTop);

    @Query(value = "SELECT * FROM Urls ur "
            + " WHERE DATEDIFF(curdate(), ur.CreatedAt) < (:periodDays) "
            + " AND ur.UserID=(:id) "
            + " ORDER BY (:orderBy) (:descent)"
            + " (:limitTop)",
            nativeQuery = true)
    public List<UrlEntity> findGeneratedUrlsUserNative(Long id, String periodDays, String orderBy, String descent, String limitTop);

    @Query(value = "SELECT * FROM Urls ur "
            + " WHERE DATEDIFF(curdate(), ur.CreatedAt) < (:periodDays) "
            + " ORDER BY Urls.ClickAmount (:descent)"
            + " (:limitTop)",
            nativeQuery = true)
    public List<UrlEntity> findClickedUrlsNative(String periodDays, String descent, String limitTop);

    @Query(value = "SELECT * FROM Urls ur "
            + " WHERE DATEDIFF(curdate(), ur.CreatedAt) < (:periodDays) "
            + " AND ur.UserID=(:id) "
            + " ORDER BY Urls.ClickAmount (:descent)"
            + " (:limitTop)",
            nativeQuery = true)
    public List<UrlEntity> findClickedUrlsUserNative(Long id, String periodDays, String descent, String limitTop);
}
