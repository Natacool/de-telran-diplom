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

    @Value("${scheduler.clean.for.users}")
    private String deleteAfterDaysStr;
    private Long deleteAfterDays;

    @Value("${scheduler.block.users}")
    private String blockNotActiveUsersStr;
    private Long blockNotActiveUsers;

    @Value("${scheduler.delete.users}")
    private String deleteBlockedUsersStr;
    private Long deleteBlockedUsers;

    @Value("${scheduler.remove.users}")
    private String removeDeletedUsersStr;
    private Long removeDeletedUsers;

    //@Autowired
    //private UrlRepository urlRepository;
    private final UrlRepository urlRepository;
    private final UserRepository userRepository;

    @PostConstruct
    void init() {
//        log.info("---------------------- INIT 1 "+ LocalDateTime.now());
//        log.info("Long: "+ deleteAfterDays + ", String: " + deleteAfterDaysStr);
        deleteAfterDays = Long.parseLong(deleteAfterDaysStr);
        blockNotActiveUsers = Long.parseLong(blockNotActiveUsersStr);
        deleteBlockedUsers = Long.parseLong(deleteBlockedUsersStr);
        removeDeletedUsers = Long.parseLong(removeDeletedUsersStr);
//        log.info("---------------------- INIT 2 "+ LocalDateTime.now());
//        log.info("Long: "+ deleteAfterDays + ", String: " + deleteAfterDaysStr);

//        System.out.println("Выполняем логику при создании объекта "
//                + this.getClass().getName());
    }




    //@Async
    // cron job for anonym: at 2 am every day
    // cron.clean_anonym = 0 0 2 * * *
    //@Scheduled(cron = "${cron.clean.anonym}")
    //@Scheduled(cron = "0 0 * * * *") // every hour:00:00
///    @Scheduled(cron = "0 */2 * * * *") // every 5 min
///    @SchedulerLock(name = "cleanShortUrlAnonymCronJob")
    // delete short urls in db which old then 1/6/12 months
    public void cleanShortUrlAnonymCronJob() {
        log.info("!!!!!!!! scheduledCronTaskAnonym -> "+ LocalDateTime.now());
        //List<UrlEntity> urlEntityList = urlRepository.findAll();
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

//                if (toDelete) {
                    log.info("!!!!!!!!  PROP deleteAfterDays -> "+ this.deleteAfterDays);
                    log.info("!!!!!!!!  DB deleteAfterDays -> "+ urlEntity.getDeleteAfterDays());
                    log.info("!!!!!!!!  getUrlId -> "+ urlEntity.getUrlId());
                    log.info("!!!!!!!!  getShortUrlId -> "+ urlEntity.getShortUrlId());
                    log.info("!!!!!!!!  getCreatedAt -> "+ urlEntity.getCreatedAt());
                    log.info("!!!!!!!! getClickedAt -> "+ urlEntity.getClickedAt());
                    log.info("!!!!!!!!  getUser -> "+ urlEntity.getUser());
                    log.info("------------------------------------------ ");
                if (toDelete) {
                    //urlRepository.deleteById(urlEntity.getUrlId());
                }
            }
        }
    }

    //@Async
    // cron job for registered user: at 3 am every day
    // cron.clean_user = 0 0 3 * * *
    //@Scheduled(cron = "${cron.clean.user}")
////    @Scheduled(cron = "0 58 * * * *") // every hour:58:00 (the 58th minute of every hour)
    ///@Scheduled(cron = "0 */2 * * * *") // every 2 min
    //@Scheduled(cron="15 * * * * *") //каждую минуту на 15 секунде
///    @SchedulerLock(name = "cleanShortUrlUserCronJob")
    // delete short urls in db which old then 1/6/12 months
///    @Scheduled(cron = "0 */3 * * * *")
    public void cleanShortUrlUserCronJob() {
        log.info("************* cleanShortUrlUserCronJob -> "+ LocalDateTime.now() + " "  + this.deleteAfterDays);

        //List<UrlEntity> urlEntityList = urlRepository.findAll();
        List<UrlEntity> urlEntityList = urlRepository.findUrlsRegisteredUsers();

        for (UrlEntity urlEntity : urlEntityList) {
            if (//urlEntity.getUser() == null ||
                    //urlEntity.getUser().getUserId() == 0 ||
                urlEntity.getClickedAt() == null ||
                urlEntity.getDeleteAfterDays() == null ||
                Duration.between(urlEntity.getClickedAt().toLocalDateTime(),
                        LocalDateTime.now()).toDays() > (urlEntity.getDeleteAfterDays() > this.deleteAfterDays?
                        urlEntity.getDeleteAfterDays():this.deleteAfterDays)) {

                log.info("************* PROP deleteAfterDays -> "+ this.deleteAfterDays);
                log.info("************* DB deleteAfterDays -> "+ urlEntity.getDeleteAfterDays());
                log.info("************* getUrlId -> "+ urlEntity.getUrlId());
                log.info("************* getShortUrlId -> "+ urlEntity.getShortUrlId());
                log.info("************* getCreatedAt -> "+ urlEntity.getCreatedAt());
                log.info("************* getClickedAt -> "+ urlEntity.getClickedAt());
                log.info("************* getUser -> "+ urlEntity.getUser());
                log.info("*********************************************************************  ");

                //urlRepository.deleteById(urlEntity.getUrlId());
            }
        }
    }

/// BLOCK
    //@Async
    //@Scheduled(cron = "0 30 3 * * *") // every day at  3:30
    @Scheduled(cron = "${cron.block.users}")
    //@Scheduled(cron = "0 58 * * * *") // every hour:58:00 (the 58th minute of every hour)
    //@Scheduled(cron = "0 */3 * * * *")
    @SchedulerLock(name = "blockNotActiveUsersCronJob")
    public void blockNotActiveUsersCronJob() {
        log.info("************* scheduledCronTaskUser -> "+ LocalDateTime.now() +
                " BLOCK USER NOT ACTIVE > "  + this.blockNotActiveUsers + " days");

        // not admin not blocked/deleted
        List<UserEntity> userEntities = userRepository.findNotAdminUsersByStatus(UserStatusEnum.ACTIVE.getTitle());

        for (UserEntity userEntity : userEntities) {
            if (userEntity.getLastActiveAt() == null ||
                    Duration.between(userEntity.getLastActiveAt().toLocalDateTime(),
                            LocalDateTime.now()).toDays() > this.blockNotActiveUsers) {
                userEntity.setStatus(UserStatusEnum.BLOCKED);
                userEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

                log.info("************* BLOCK USER -> "+ userEntity.getEmail());
                log.info("************* status -> "+ userEntity.getStatus());
                log.info("************* last active -> "+ userEntity.getLastActiveAt());

                userRepository.save(userEntity);
            }
        }
    }

/// DELETE
    //@Async
    //@Scheduled(cron = "0 30 3 * * *") // every day at  3:30
    @Scheduled(cron = "${cron.delete.users}")
    //@Scheduled(cron = "0 58 * * * *") // every hour:58:00 (the 58th minute of every hour)
    //@Scheduled(cron = "0 */4 * * * *")
    @SchedulerLock(name = "deleteBlockedUsersCronJob")
    public void deleteBlockedUsersCronJob() {
        log.info("************* scheduledCronTaskUser -> "+ LocalDateTime.now() +
                " DELETE BLOCKED USER after > "  + this.deleteBlockedUsers + " days");

        // not admin & blocked
        List<UserEntity> userEntities = userRepository.findNotAdminUsersByStatus(UserStatusEnum.BLOCKED.getTitle());

        for (UserEntity userEntity : userEntities) {
            if (userEntity.getUpdatedAt() == null ||
                    Duration.between(userEntity.getUpdatedAt().toLocalDateTime(),
                            LocalDateTime.now()).toDays() > this.deleteBlockedUsers) {
                userEntity.setStatus(UserStatusEnum.DELETED);
                userEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

                log.info("************* DELETE USER -> "+ userEntity.getEmail());
                log.info("************* status -> "+ userEntity.getStatus());
                log.info("************* updated -> "+ userEntity.getUpdatedAt());

                userRepository.save(userEntity);
            }
        }
    }


/// REMOVE
    //@Async
    //@Scheduled(cron = "0 30 3 * * *") // every day at  3:30
    @Scheduled(cron = "${cron.remove.users}")
    //@Scheduled(cron = "0 58 * * * *") // every hour:58:00 (the 58th minute of every hour)
    //@Scheduled(cron = "0 */5 * * * *")
    @SchedulerLock(name = "removeDeletedUsersCronJob")
    public void removeDeletedUsersCronJob() {
        log.info("************* scheduledCronTaskUser -> "+ LocalDateTime.now() +
                " REMOVE DELETED USER after > "  + this.removeDeletedUsers + " days");

        // not admin & blocked
        List<UserEntity> userEntities = userRepository.findNotAdminUsersByStatus(UserStatusEnum.DELETED.getTitle());

        for (UserEntity userEntity : userEntities) {
            if (userEntity.getUpdatedAt() == null ||
                    Duration.between(userEntity.getUpdatedAt().toLocalDateTime(),
                            LocalDateTime.now()).toDays() > this.removeDeletedUsers) {
                log.info("************* REMOVE USER -> "+ userEntity.getEmail());
                log.info("************* status -> "+ userEntity.getStatus());
                log.info("************* updated -> "+ userEntity.getUpdatedAt());
                userRepository.delete(userEntity);
            }
        }
    }

}
