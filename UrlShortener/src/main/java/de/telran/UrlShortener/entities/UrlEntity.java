package de.telran.UrlShortener.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "Urls")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UrlID", nullable = false)
    private Long urlId;

    @Column(name = "ShortUrl", unique = true)
    private String shortUrl;

    @Column(name = "LongUrl", nullable = false, columnDefinition = "TEXT")
    private String longUrl;

    @Column(name = "CreatedAt")
    private Timestamp createdAt;

    @Column(name = "ClickedAt")
    private Timestamp clickedAt;

    @Column(name = "ClickAmount", nullable = false)
    private Long clickAmount;

    @Column(name = "DeleteAfter", nullable = false)
    private Long deleteAfterDays;

    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private UserEntity user;

    // for future
    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

    @Column(name = "Favorite")
    private Boolean isFavorite;

}

// if UserID == 0 --> delete after   (deleteAfterDays - (now - create_date).days) > 0
// if UserID != 0 --> delete after   (deleteAfterDays - (now - clicked_date).days) > 0

