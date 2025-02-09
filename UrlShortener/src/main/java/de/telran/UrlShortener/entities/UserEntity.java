package de.telran.UrlShortener.entities;

import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long userId;

    @Column(name = "Email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    private UserRoleEnum role;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private UserRoleEnum status;

    @Column(name = "RegisteredAt")
    private Timestamp registeredAt;

    @Column(name = "LastActiveAt")
    private Timestamp lastActiveAt;

    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

    @Column(name = "PasswordHash")
    private String passwordHash;

}
