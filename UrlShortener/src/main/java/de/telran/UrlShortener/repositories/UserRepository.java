package de.telran.UrlShortener.repositories;

import de.telran.UrlShortener.entities.UrlEntity;
import de.telran.UrlShortener.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity,Long> {
    @Query(value = "SELECT * FROM Users us WHERE us.Email=?1", nativeQuery = true)
    public UserEntity findUserByEmail(String email);

    @Query(value = "SELECT * FROM Users us WHERE us.Email=?1" +
            " AND us.Role != 'ADMIN' AND us.Status != 'DELETED'",
            nativeQuery = true)
    public UserEntity findUserNotAdminNotDeletedByEmail(String email);



/*
    UserID BIGINT primary key auto_increment NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Role ENUM('CLIENT', 'ADMIN') NULL, -- default 'CLIENT'
    Status ENUM('ACTIVE', 'BLOCKED', 'DELETED') NULL, -- default 'ACTIVE'
    RegisteredAt datetime NOT NULL,
    LastActiveAt datetime, -- NOT NULL, -- can be get from last created url
    UpdatedAt datetime NULL,
    PasswordHash VARCHAR(255) NULL
 */

    @Query(value = "SELECT * FROM Users us " +
            "WHERE us.Role != 'ADMIN' AND us.Status = (:status)",
            nativeQuery = true)
    public List<UserEntity> findNotAdminUsersByStatus(String status);

    @Query(value = "SELECT * FROM Users us  ORDER BY us.Status, us.Role DESC",
 //   @Query(value = "SELECT * FROM Users us "
 //           + " WHERE us.Role in (:userRoles) "
 //           + " AND us.Status in (:userStatuses) "
 //           + " (:userEmail) " // use when
 //           + " ORDER BY us.Status, us.Role DESC ",
            nativeQuery = true)
    public List<UserEntity> findUsersNative();
//    public List<UserEntity> findUsersNative(List<String> userRoles,
//                                           List<String> userStatuses,
//                                           String userEmail);
}
