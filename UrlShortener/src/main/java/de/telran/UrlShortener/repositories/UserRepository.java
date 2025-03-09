package de.telran.UrlShortener.repositories;

import de.telran.UrlShortener.entities.StatisticUserInterface;
import de.telran.UrlShortener.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    @Query(value = "SELECT * FROM Users us WHERE us.Email=?1", nativeQuery = true)
    public UserEntity findUserByEmail(String email);

    @Query(value = "SELECT * FROM Users us WHERE us.Email=?1" +
            " AND us.Role != 'ADMIN' AND us.Status != 'DELETED'",
            nativeQuery = true)
    public UserEntity findUserNotAdminNotDeletedByEmail(String email);

    @Query(value = "SELECT * FROM Users us " +
            "WHERE us.Role != 'ADMIN' AND us.Status = (:status)",
            nativeQuery = true)
    public List<UserEntity> findNotAdminUsersByStatus(String status);

    @Query(value =
            " SELECT "
                    + " us.UserID, "
                    + " us.Email as userEmail, "
                    + " count(ur.CreatedAt) as generatedAmount, "
                    + " sum(ur.ClickAmount) as clickedAmount, "
                    + " us.Role as role, "
                    + " us.Status as status, "
                    + " us.RegisteredAt as registeredAt, "
                    + " us.LastActiveAt as lastActiveAt, "
                    + " us.UpdatedAt as updatedAt "
                    + " FROM Users us "
                    + " JOIN Urls ur "
                    + " ON us.UserID = ur.userId "
                    + " WHERE "
                    + " ur.CreatedAt  > (:periodStart) "
                    + " AND "
                    + " us.Role in (:userRoles) "
                    + " AND "
                    + " us.Status in (:userStatuses) "
                    + " GROUP BY us.Email "
                    + " LIMIT (:topLimit) "
            , nativeQuery = true)
    public List<StatisticUserInterface> findAllUsersByRoleAndStatusNative(
            List<String> userRoles,
            List<String> userStatuses,
            Timestamp periodStart,
            Integer topLimit
    );

    @Query(value =
            " SELECT "
                    + " us.UserID, "
                    + " us.Email as userEmail, "
                    + " count(ur.CreatedAt) as generatedAmount, "
                    + " sum(ur.ClickAmount) as clickedAmount, "
                    + " us.Role as role, "
                    + " us.Status as status, "
                    + " us.RegisteredAt as registeredAt, "
                    + " us.LastActiveAt as lastActiveAt, "
                    + " us.UpdatedAt as updatedAt "
                    + " FROM Users us "
                    + " JOIN Urls ur "
                    + " ON us.UserID = ur.userId "
                    + " WHERE "
                    + " ur.CreatedAt  > (:periodStart) "
                    + " AND "
                    + " us.Email in (:userEmails) "
                    + " AND "
                    + " us.Role in (:userRoles) "
                    + " AND "
                    + " us.Status in (:userStatuses) "
                    + " GROUP BY us.Email "
                    + " LIMIT (:topLimit) "
            , nativeQuery = true)
    List<StatisticUserInterface> findSpecifiedUsersByRoleAndStatusNative(
            List<String> userEmails,
            List<String> userRoles,
            List<String> userStatuses,
            Timestamp periodStart,
            Integer topLimit
    );
}
