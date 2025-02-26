package de.telran.UrlShortener.repositories;

import de.telran.UrlShortener.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity,Long> {
    @Query(value = "SELECT * FROM Users us WHERE us.Email=?1", nativeQuery = true)
    public UserEntity findUserByEmail(String email);

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
