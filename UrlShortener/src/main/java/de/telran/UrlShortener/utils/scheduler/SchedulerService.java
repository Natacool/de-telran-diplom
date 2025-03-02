package de.telran.UrlShortener.utils.scheduler;

import de.telran.UrlShortener.entities.UrlEntity;
import de.telran.UrlShortener.entities.UserEntity;
import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import de.telran.UrlShortener.repositories.UrlRepository;

import de.telran.UrlShortener.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class SchedulerService {

    @Value("${scheduler.url.clean.days}")
    private String deleteAfterDaysStr;
    private Long deleteAfterDays;

    @Value("${scheduler.users.block}")
    private String blockNotActiveUsersStr;
    private Long blockNotActiveUsers;

    @Value("${scheduler.users.delete}")
    private String deleteBlockedUsersStr;
    private Long deleteBlockedUsers;

    @Value("${scheduler.users.remove}")
    private String removeDeletedUsersStr;
    private Long removeDeletedUsers;

    private final UrlRepository urlRepository;
    private final UserRepository userRepository;

    @PostConstruct
    void init() {
        deleteAfterDays = Long.parseLong(deleteAfterDaysStr);
        blockNotActiveUsers = Long.parseLong(blockNotActiveUsersStr);
        deleteBlockedUsers = Long.parseLong(deleteBlockedUsersStr);
        removeDeletedUsers = Long.parseLong(removeDeletedUsersStr);
    }

    // delete short urls of anonym users in db which old then DB value (by default = 7)
    // requirements: 1/6/12 months from CreatedAt date; I choose 7 days
    @Async
    @Scheduled(cron = "${cron.clean.url.anonym}")
    @SchedulerLock(name = "cleanShortUrlAnonymCronJob")
    public void cleanShortUrlAnonymCronJob() {
        log.trace("scheduledCronTaskAnonym -> "+ LocalDateTime.now());
        List<UrlEntity> urlEntityList = urlRepository.findUrlsNotRegisteredUsers();

        for (UrlEntity urlEntity : urlEntityList) {
            if (urlEntity.getUser() == null || urlEntity.getUser().getUserId() == 0){
                boolean toDelete = false;

                if (urlEntity.getCreatedAt() == null) {
                    toDelete = true;
                } else if (urlEntity.getCreatedAt() != null &&
                        urlEntity.getDeleteAfterDays() != null) {

                    LocalDateTime created = urlEntity.getCreatedAt().toLocalDateTime();
                    LocalDateTime now = LocalDateTime.now();
                    toDelete = Duration.between(created, now).toDays() > urlEntity.getDeleteAfterDays();
                }

                    log.trace("+ PROP deleteAfterDays -> "+ this.deleteAfterDays);
                    log.trace("+ DB deleteAfterDays -> "+ urlEntity.getDeleteAfterDays());
                    log.trace("+ getUrlId -> "+ urlEntity.getUrlId());
                    log.trace("+ getShortUrlId -> "+ urlEntity.getShortUrlId());
                    log.trace("+ getCreatedAt -> "+ urlEntity.getCreatedAt());
                    log.trace("+ getClickedAt -> "+ urlEntity.getClickedAt());
                    log.trace("+ getUser -> "+ urlEntity.getUser());
                if (toDelete) {
                    log.info("UrlId -> "+ urlEntity.getUrlId() + " DELETED");
                    urlRepository.deleteById(urlEntity.getUrlId());
                }
                log.trace("+++++++++++++++++++++++++++++++++++++++++++");
            }
        }
    }

    // delete short urls of registered users in db which old then {scheduler.clean.for.users}
    // requirements: 1/6/12 months from ClickedAt date
    @Async
    @Scheduled(cron = "${cron.clean.url.users}")
    @SchedulerLock(name = "cleanShortUrlUserCronJob")
    public void cleanShortUrlUserCronJob() {
        log.trace(" cleanShortUrlUserCronJob -> "+ LocalDateTime.now() + " "  + this.deleteAfterDays);

        List<UrlEntity> urlEntityList = urlRepository.findUrlsRegisteredUsers();

        for (UrlEntity urlEntity : urlEntityList) {
            if (urlEntity.getClickedAt() == null ||
                urlEntity.getDeleteAfterDays() == null ||
                Duration.between(urlEntity.getClickedAt().toLocalDateTime(),
                        LocalDateTime.now()).toDays() > (urlEntity.getDeleteAfterDays() > this.deleteAfterDays?
                        urlEntity.getDeleteAfterDays():this.deleteAfterDays)) {

                log.trace("* PROP deleteAfterDays -> "+ this.deleteAfterDays);
                log.trace("* DB deleteAfterDays -> "+ urlEntity.getDeleteAfterDays());
                log.trace("* getUrlId -> "+ urlEntity.getUrlId());
                log.trace("* getShortUrlId -> "+ urlEntity.getShortUrlId());
                log.trace("* getCreatedAt -> "+ urlEntity.getCreatedAt());
                log.trace("* getClickedAt -> "+ urlEntity.getClickedAt());
                log.trace("* getUser -> "+ urlEntity.getUser());

                log.info("UrlId -> "+ urlEntity.getUrlId() + " DELETED");
                urlRepository.deleteById(urlEntity.getUrlId());
                log.trace("*********************************************************************  ");
            }
        }
    }

    // BLOCK USERs which are NOT active > {scheduler.users.block} days
    @Async
    @Scheduled(cron = "${cron.block.users}")
    @SchedulerLock(name = "blockNotActiveUsersCronJob")
    public void blockNotActiveUsersCronJob() {
        log.trace("blockNotActiveUsersCronJob -> "+ LocalDateTime.now() +
                " BLOCK USER NOT ACTIVE > "  + this.blockNotActiveUsers + " days");

        // not admin not blocked/deleted
        List<UserEntity> userEntities = userRepository.findNotAdminUsersByStatus(UserStatusEnum.ACTIVE.getTitle());

        for (UserEntity userEntity : userEntities) {
            if (userEntity.getLastActiveAt() == null ||
                    Duration.between(userEntity.getLastActiveAt().toLocalDateTime(),
                            LocalDateTime.now()).toDays() > this.blockNotActiveUsers) {
                userEntity.setStatus(UserStatusEnum.BLOCKED);
                userEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

                log.info("BLOCK USER -> "+ userEntity.getEmail());
                log.trace("status -> "+ userEntity.getStatus());
                log.trace("last active -> "+ userEntity.getLastActiveAt());
                userRepository.save(userEntity);
            }
        }
    }

    // DELETE USERs which are blocked > {scheduler.users.delete} days
    @Async
    @Scheduled(cron = "${cron.delete.users}")
    @SchedulerLock(name = "deleteBlockedUsersCronJob")
    public void deleteBlockedUsersCronJob() {
        log.trace("deleteBlockedUsersCronJob -> "+ LocalDateTime.now() +
                " DELETE BLOCKED USER after "  + this.deleteBlockedUsers + " days");

        // not admin & blocked
        List<UserEntity> userEntities = userRepository.findNotAdminUsersByStatus(UserStatusEnum.BLOCKED.getTitle());

        for (UserEntity userEntity : userEntities) {
            if (userEntity.getUpdatedAt() == null ||
                    Duration.between(userEntity.getUpdatedAt().toLocalDateTime(),
                            LocalDateTime.now()).toDays() > this.deleteBlockedUsers) {
                userEntity.setStatus(UserStatusEnum.DELETED);
                userEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

                log.info("DELETE USER -> "+ userEntity.getEmail());
                log.trace("status -> "+ userEntity.getStatus());
                log.trace("updated -> "+ userEntity.getUpdatedAt());
                userRepository.save(userEntity);
            }
        }
    }

    // REMOVE USERs which are deleted > {scheduler.users.remove} days
    @Async
    @Scheduled(cron = "${cron.remove.users}")
    @SchedulerLock(name = "removeDeletedUsersCronJob")
    public void removeDeletedUsersCronJob() {
        log.trace("removeDeletedUsersCronJob -> "+ LocalDateTime.now() +
                " REMOVE DELETED USER after "  + this.removeDeletedUsers + " days");

        List<UserEntity> userEntities = userRepository.findNotAdminUsersByStatus(UserStatusEnum.DELETED.getTitle());

        for (UserEntity userEntity : userEntities) {
            if (userEntity.getUpdatedAt() == null ||
                    Duration.between(userEntity.getUpdatedAt().toLocalDateTime(),
                            LocalDateTime.now()).toDays() > this.removeDeletedUsers) {
                log.info("REMOVE USER -> "+ userEntity.getEmail());
                log.trace("status -> "+ userEntity.getStatus());
                log.trace("updated -> "+ userEntity.getUpdatedAt());
                userRepository.delete(userEntity);
            }
        }
    }

}
