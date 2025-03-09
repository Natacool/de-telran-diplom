package de.telran.UrlShortener.repositories;

import de.telran.UrlShortener.entities.StatisticClickedUrlInterface;
import de.telran.UrlShortener.entities.StatisticGeneratedUrlInterface;
import de.telran.UrlShortener.entities.UrlEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity,Long> {
    @Query(value = "SELECT * FROM Urls ur WHERE ur.ShortUrlId=?1", nativeQuery = true)
    public UrlEntity findByShortUrlIdNative(String shortUrlId);
    @Query(value = "SELECT * FROM Urls ur WHERE ur.LongUrl=?1", nativeQuery = true)
    public UrlEntity findByLongUrlNative(String longUrl);

    @Query(value = "SELECT * FROM Urls ur WHERE (ur.UserID=0 OR ur.UserID is NULL) ", nativeQuery = true)
    public List<UrlEntity> findUrlsNotRegisteredUsers();

    @Query(value = "SELECT * FROM Urls ur WHERE (ur.UserID!=0)", nativeQuery = true)
    public List<UrlEntity> findUrlsRegisteredUsers();

// CLICKED STAT
    @Query(value =
            "SELECT ur.ShortUrlId as shortUrl, "
            + " ur.ClickAmount as clicked, "
            + " us.Email as client, "
            + " ur.UserID as id, "
            + " ur.LongUrl as longUrl "
            + " FROM Urls ur "
            + " JOIN Users us "
            + " ON ur.userId = us.UserID"
            + " WHERE us.Email in (:userEmails) "
            + " AND ur.ClickedAt  > (:periodStart) "
            + " ORDER BY "
            + " CASE "
                + " WHEN (:sortDirection) = 'DESC' THEN ur.ClickAmount*(-1)  "
                + " WHEN (:sortDirection) = 'ASC' THEN ur.ClickAmount  "
            + " END "
            + " LIMIT (:topLimit) "
            , nativeQuery = true)
    public List<StatisticClickedUrlInterface> findClickedUrlsRegisteredUserNative(
            List<String> userEmails,
            Timestamp periodStart,
            String sortDirection,
            Integer topLimit
    );

    @Query(value =
            "SELECT ur.ShortUrlId as shortUrl, "
                    + " ur.ClickAmount as clicked, "
                    + " us.Email as client, "
                    + " ur.UserID as id, "
                    + " ur.LongUrl as longUrl "
                    + " FROM Urls ur "
                    + " JOIN Users us "
                    + " ON ur.userId = us.UserID"
                    + " WHERE ur.ClickedAt  > (:periodStart) "
                    + " ORDER BY "
                    + " CASE "
                    + " WHEN (:sortDirection) = 'DESC' THEN ur.ClickAmount*(-1)  "
                    + " WHEN (:sortDirection) = 'ASC' THEN ur.ClickAmount  "
                    + " END "
                    + " LIMIT (:topLimit) "
            , nativeQuery = true)
    public List<StatisticClickedUrlInterface> findClickedUrlsRegisteredNative(
            Timestamp periodStart,
            String sortDirection,
            Integer topLimit
    );

    @Query(value =
            "SELECT ur.ShortUrlId as shortUrl, "
                    + " ur.ClickAmount as clicked, "
                    + " ur.UserID as client, "
                    + " ur.LongUrl as longUrl "
                    + " FROM Urls ur "
                    + " WHERE ur.ClickedAt  > (:periodStart) "
                    + " ORDER BY "
                    + " CASE "
                    + " WHEN (:sortDirection) = 'DESC' THEN ur.ClickAmount*(-1)  "
                    + " WHEN (:sortDirection) = 'ASC' THEN ur.ClickAmount  "
                    + " END "
                    + " LIMIT (:topLimit) "
            , nativeQuery = true)
    public List<StatisticClickedUrlInterface> findPopularClickedUrlsIncludeAnonimNative(
            Timestamp periodStart,
            String sortDirection,
            Integer topLimit
    );

// GENERATED STAT
    @Query(value =
            "SELECT"
                    + " us.Email as client, "
                    + " count(ur.CreatedAt) as generated "
                    + " FROM Urls ur "
                    + " JOIN Users us "
                    + " ON ur.userId = us.UserID"
                    + " WHERE us.Email in (:userEmails) "
                    + " AND ur.CreatedAt  > (:periodStart) "
                    + " GROUP BY us.Email "
                    + " ORDER BY generated DESC "
                    + " LIMIT (:topLimit) "
            , nativeQuery = true)
    public List<StatisticGeneratedUrlInterface> findGeneratedUrlsRegisteredUserNative(
            List<String> userEmails,
            Timestamp periodStart,
            Integer topLimit
    );

    @Query(value =
            "SELECT"
                    + " us.Email as client, "
                    + " count(ur.CreatedAt) as generated "

                    + " FROM Urls ur "
                    + " JOIN Users us "
                    + " ON ur.userId = us.UserID"
                    + " WHERE ur.CreatedAt  > (:periodStart) "

                    + " GROUP BY us.Email "
                    + " ORDER BY generated DESC "
                    + " LIMIT (:topLimit) "
            , nativeQuery = true)
    public List<StatisticGeneratedUrlInterface> findGeneratedUrlsRegisteredNative(
            Timestamp periodStart,
            Integer topLimit
    );

    @Query(value =
            "SELECT"
                    + " ur.UserID as client, "
                    + " count(ur.CreatedAt) as generated "
                    + " FROM Urls ur "
                    + " WHERE ur.CreatedAt  > (:periodStart) "
                    + " GROUP BY ur.UserID "
                    + " ORDER BY generated DESC "
                    + " LIMIT (:topLimit) "
            , nativeQuery = true)
    public List<StatisticGeneratedUrlInterface> findGeneratedUrlsIncludeAnonimNative(
            Timestamp periodStart,
            Integer topLimit
    );

// DETAILED
    @Query(value =
            "SELECT"
                    + " us.Email as client, "
                    + " ur.CreatedAt as createdAt, "
                    + " ur.ShortUrlId as shortUrl, "
                    + " ur.LongUrl as longUrl "

                    + " FROM Urls ur "
                    + " JOIN Users us "
                    + " ON ur.userId = us.UserID"

                    + " WHERE us.Email in (:userEmails) "
                    + " AND ur.CreatedAt  > (:periodStart) "

                    + " ORDER BY us.Email, ur.CreatedAt "
                    + " LIMIT (:topLimit) "
            , nativeQuery = true)
    public List<StatisticGeneratedUrlInterface> findGeneratedUrlsRegisteredUserDetailsNative(
            List<String> userEmails,
            Timestamp periodStart,
            Integer topLimit
    );

    @Query(value =
            "SELECT"
                    + " us.Email as client, "
                    + " ur.CreatedAt as createdAt, "
                    + " ur.ShortUrlId as shortUrl, "
                    + " ur.LongUrl as longUrl "

                    + " FROM Urls ur "
                    + " JOIN Users us "
                    + " ON ur.userId = us.UserID"

                    + " WHERE ur.CreatedAt  > (:periodStart) "
                    + " ORDER BY us.Email, ur.CreatedAt "
                    + " LIMIT (:topLimit) "

            , nativeQuery = true)
    public List<StatisticGeneratedUrlInterface> findGeneratedUrlsRegisteredDetailsNative(
            Timestamp periodStart,
            Integer topLimit
    );

    @Query(value =
            "SELECT"
                    + " ur.UserID as client, "
                    + " ur.CreatedAt as createdAt, "
                    + " ur.ShortUrlId as shortUrl, "
                    + " ur.LongUrl as longUrl "
                    + " FROM Urls ur "
                    + " WHERE ur.CreatedAt  > (:periodStart) "
                    + " ORDER BY ur.UserID, ur.CreatedAt "
                    + " LIMIT (:topLimit) "
            , nativeQuery = true)
    public List<StatisticGeneratedUrlInterface> findGeneratedUrlsIncludeAnonimDetailsNative(
            Timestamp periodStart,
            Integer topLimit
    );
}
