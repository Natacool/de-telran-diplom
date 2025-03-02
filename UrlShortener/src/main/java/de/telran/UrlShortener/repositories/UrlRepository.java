package de.telran.UrlShortener.repositories;

import de.telran.UrlShortener.entities.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UrlRepository extends JpaRepository<UrlEntity,Long> {
    @Query(value = "SELECT * FROM Urls ur WHERE ur.ShortUrlId=?1", nativeQuery = true)
    public UrlEntity findByShortUrlIdNative(String shortUrlId);

    @Query("SELECT ur FROM UrlEntity ur WHERE ur.shortUrlId=?1")
    public UrlEntity findByShortUrlId(String shortUrlId);


    @Query(value = "SELECT * FROM Urls ur WHERE ur.LongUrl=?1", nativeQuery = true)
    public UrlEntity findByLongUrlNative(String longUrl);

    @Query(value = "SELECT * FROM Urls ur "
            + " WHERE ur.LongUrl=?1",
            nativeQuery = true)
    public List<UrlEntity> findGeneratedUrlsNative(Long periodDays, Boolean descent);


    @Query(value = "SELECT * FROM Urls ur WHERE (ur.UserID=0 OR ur.UserID is NULL) ", nativeQuery = true)
    public List<UrlEntity> findUrlsNotRegisteredUsers();

    @Query(value = "SELECT * FROM Urls ur WHERE (ur.UserID!=0)", nativeQuery = true)
    public List<UrlEntity> findUrlsRegisteredUsers();



    @Query(value = "SELECT * FROM Urls ur ORDER BY ur.UserID limit 3",
//            + " WHERE DATEDIFF(current_date, ur.CreatedAt) < (:periodDays) "
//            + " ORDER BY (:orderBy) (:descent)"
//            + " (:limitTop)",
            nativeQuery = true)
//    public List<UrlEntity> findGeneratedUrlsNative(String periodDays, String orderBy, String descent, String limitTop);
//    public List<UrlEntity> findGeneratedUrlsNative(String orderBy, String descent, String limitTop);
    public List<UrlEntity> findGeneratedUrlsNative();

    @Query(value = "SELECT * FROM Urls ur ORDER BY ur.UserID limit 1",
//    @Query(value = "SELECT * FROM Urls ur "
//            + " WHERE DATEDIFF(current_date, ur.CreatedAt) < (:periodDays) "
//            + " AND ur.UserID=(:id) "
//            + " WHERE ur.UserID=(:id) "
//            + " ORDER BY (:orderBy) (:descent)"
//            + " (:limitTop)",
            nativeQuery = true)
//    public List<UrlEntity> findGeneratedUrlsUserNative(Long id, String periodDays, String orderBy, String descent, String limitTop);
//    public List<UrlEntity> findGeneratedUrlsUserNative(Long id, String orderBy, String descent, String limitTop);
    public List<UrlEntity> findGeneratedUrlsUserNative();

    @Query(value = "SELECT * FROM Urls ur ORDER BY ur.ClickAmount DESC limit 3",
//            + " WHERE DATEDIFF(current_date, ur.CreatedAt) < (:periodDays) "
//            + " ORDER BY ur.ClickAmount (:descent)"
//            + " (:limitTop)",
            nativeQuery = true)
//    public List<UrlEntity> findClickedUrlsNative(String periodDays, String descent, String limitTop);
//    public List<UrlEntity> findClickedUrlsNative(String descent, String limitTop);
    public List<UrlEntity> findClickedUrlsNative();

    @Query(value = "SELECT * FROM Urls ur ORDER BY ur.ClickAmount DESC limit 1",
//            + " WHERE DATEDIFF(current_date, ur.CreatedAt) < (:periodDays) "
//            + " AND ur.UserID=(:id) "
//            + " WHERE ur.UserID=(:id) "
//            + " ORDER BY ur.ClickAmount (:descent)"
//            + " (:limitTop)",
            nativeQuery = true)
//    public List<UrlEntity> findClickedUrlsUserNative(Long id, String periodDays, String descent, String limitTop);
//    public List<UrlEntity> findClickedUrlsUserNative(Long id, String descent, String limitTop);
    public List<UrlEntity> findClickedUrlsUserNative();
}
