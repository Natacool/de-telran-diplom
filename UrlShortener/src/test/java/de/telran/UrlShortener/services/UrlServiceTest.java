package de.telran.UrlShortener.services;

import de.telran.UrlShortener.configure.MapperUtil;
import de.telran.UrlShortener.dtos.LongUrlDto;
import de.telran.UrlShortener.dtos.ShortUrlIdDto;
import de.telran.UrlShortener.dtos.UrlCopyEntityDto;
import de.telran.UrlShortener.dtos.UrlRequestUpdateDeleteTimerDto;
import de.telran.UrlShortener.entities.UrlEntity;
import de.telran.UrlShortener.entities.UserEntity;
import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import de.telran.UrlShortener.repositories.UrlRepository;
import de.telran.UrlShortener.repositories.UserRepository;
import de.telran.UrlShortener.utils.generator.ShortUrlGenerator;
import de.telran.UrlShortener.utils.mapper.Mappers;
import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepository urlRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @Mock
    private ShortUrlGenerator urlGeneratorMock;



    @InjectMocks
    private UrlService urlServiceTest;

    private UserEntity userEntity1;
    private UserEntity userEntity2;
    private UrlEntity urlEntity1;
    private UrlEntity urlEntity2;

    private UrlCopyEntityDto urlCopyEntityDto1;
    private UrlCopyEntityDto urlCopyEntityDto2;

    private ShortUrlIdDto shortUrlIdDto;
    private UrlRequestUpdateDeleteTimerDto urlRequestUpdateDeleteTimerDto2;

    private String shortUrlId = "google";
    private String longUrl = "https://www.google.com";
    private LongUrlDto longUrlDto;

    @BeforeEach
    void setUp() {
        userEntity1 = new UserEntity(
                1L,
                "client1@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345"
        );

        userEntity2 = new UserEntity(
                2L,
                "admin2@example.com",
                UserRoleEnum.ADMIN,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "54321"
        );

        urlEntity1 = new UrlEntity(
                1L,
                "google",
                "https://www.google.com",
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                0L,
                7L,
                userEntity1,
                null,
                false
        );

        urlEntity2 = new UrlEntity(
                2L,
                "wiki",
                "https://en.wikipedia.org/",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                3L,
                10L,
                userEntity2,
                Timestamp.valueOf(LocalDateTime.now()),
                false
        );

        urlCopyEntityDto1 = new UrlCopyEntityDto(
                1L,
                "google",
                "https://www.google.com",
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                0L,
                7L,
                userEntity1.getUserId(),
                null,
                false
        );

        urlCopyEntityDto2 = new UrlCopyEntityDto(
                2L,
                "wiki",
                "https://en.wikipedia.org/",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                3L,
                10L,
                userEntity2.getUserId(),
                Timestamp.valueOf(LocalDateTime.now()),
                false
        );

        shortUrlIdDto = new ShortUrlIdDto(
                "google"
        );

        urlRequestUpdateDeleteTimerDto2 = new UrlRequestUpdateDeleteTimerDto(
                "wiki",
                12L
        );

        longUrlDto = new LongUrlDto(
                "https://www.google.com"
        );

    }




    // findByLongUrlNative(longUrl.getUrl()), urlEntity = null
    // urlEntity = urlRepository.save(urlEntity), savedUrlEntity != urlEntity
    // shortUrl = ""
    @Test
    void getGeneratedUrl() {
        //urlServiceTest.getGeneratedUrl();

        UrlEntity saved = new UrlEntity(
                1L,
                "google",
                "https://www.google.com",
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                0L,
                7L,
                userEntity1,
                null,
                false
        );

        when(urlRepositoryMock.findByLongUrlNative(any(String.class))).thenReturn(null);
        when(urlGeneratorMock.generateShortUrl2()).thenReturn(String.valueOf("google"));
        when(urlRepositoryMock.save(urlEntity1)).thenReturn(urlEntity2);

        String actual = urlServiceTest.getGeneratedUrl(longUrlDto);

        verify(urlRepositoryMock, times(1)).findByLongUrlNative(any(String.class));
        verify(urlRepositoryMock, times(1)).save(any(UrlEntity.class));

        assertNotNull(actual);
        assertEquals("", actual);
    }


    // findByLongUrlNative(longUrl.getUrl()), urlEntity = null
    // urlEntity = urlRepository.save(urlEntity), savedUrlEntity = urlEntity
    // shortUrl = homeURL + "/" + urlEntity.getShortUrlId();
    @Test
    void getGeneratedUrl1() {
        UrlEntity saved = new UrlEntity(
                1L,
                "google",
                "https://www.google.com",
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                0L,
                7L,
                userEntity1,
                null,
                false
        );

        when(urlRepositoryMock.findByLongUrlNative(any(String.class))).thenReturn(null);
        when(urlRepositoryMock.save(urlEntity1)).thenReturn(saved);

        String actual = urlServiceTest.getGeneratedUrl(longUrlDto);

        verify(urlRepositoryMock, times(1)).findByLongUrlNative(any(String.class));
        verify(urlRepositoryMock, times(1)).save(any(UrlEntity.class));

        assertNotNull(actual);
        assertEquals(shortUrlId, actual);

    }




    // findByLongUrlNative(longUrl.getUrl()), urlEntity != null
    // shortUrl = homeURL + "/" + urlEntity.getShortUrlId();
    @Test
    void getGeneratedUrl2() {
        UrlEntity saved = new UrlEntity(
                1L,
                "google",
                "https://www.google.com",
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                0L,
                7L,
                userEntity1,
                null,
                false
        );

        when(urlRepositoryMock.findByLongUrlNative(any(String.class))).thenReturn(urlEntity1);
        when(urlRepositoryMock.save(urlEntity1)).thenReturn(saved);

        String actual = urlServiceTest.getGeneratedUrl(longUrlDto);

        verify(urlRepositoryMock, times(1)).findByLongUrlNative(any(String.class));
        verify(urlRepositoryMock, times(0)).save(any(UrlEntity.class));

        assertNotNull(actual);
        assertEquals(shortUrlId, actual);


    }














    // findByShortUrlIdNative(shortUrlId), urlEntity != null
    // urlEntity.getLongUrl() != null
    // urlEntity.getClickAmount() != null
    // urlEntity = urlRepository.save(urlEntity),
            // (urlEntity == null || (urlEntity != null &&  (urlEntity.getLongUrl() == null || !urlEntity.getLongUrl().equals(longUrl))))
            //    longUrl ="";

            // (urlEntity != null && (urlEntity.getClickAmount() == null || urlEntity.getClickAmount() != plusOneClick))
            // error

            // (urlEntity != null && (urlEntity.getClickedAt() == null || urlEntity.getClickedAt() != now))
            // error
    @Test
    void getRedirectUrl() {
        UrlEntity saved = new UrlEntity(
                1L,
                "google",
                "https://www.google.com",
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                0L,
                7L,
                userEntity1,
                null,
                false
        );


        when(urlRepositoryMock.findByShortUrlIdNative(any(String.class))).thenReturn(urlEntity1);
        when(urlRepositoryMock.save(any(UrlEntity.class))).thenReturn(saved);

        String actual = urlServiceTest.getRedirectUrl(shortUrlId);

        verify(urlRepositoryMock, times(1)).findByShortUrlIdNative(any(String.class));
        verify(urlRepositoryMock, times(1)).save(any(UrlEntity.class));

        assertNotNull(actual);
        assertEquals(longUrl, actual);
    }


    // urlRepository.findByShortUrlIdNative, urlEntity != null
    // urlEntity.getLongUrl() != null
    @Test
    void getLongUrl() {
        when(urlRepositoryMock.findByShortUrlIdNative(any(String.class))).thenReturn(urlEntity1);

        String actual = urlServiceTest.getLongUrl(shortUrlId);

        verify(urlRepositoryMock, times(1)).findByShortUrlIdNative(any(String.class));
        assertNotNull(actual);
        assertNotEquals("", actual);
        assertEquals(longUrl, actual);
    }

    // urlRepository.findByShortUrlIdNative, urlEntity = null
    // urlEntity.getLongUrl() != null
    @Test
    void getLongUrlFindNull() {
        when(urlRepositoryMock.findByShortUrlIdNative(any(String.class))).thenReturn(null);

        String actual = urlServiceTest.getLongUrl(shortUrlId);

        verify(urlRepositoryMock, times(1)).findByShortUrlIdNative(any(String.class));
        assertNotNull(actual);
        assertEquals("", actual);
    }

    // urlRepository.findByShortUrlIdNative, urlEntity != null
    // urlEntity.getLongUrl() = null
    @Test
    void getLongUrlNullLongUrl() {
        UrlEntity found = new UrlEntity(
                1L,
                "google",
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                0L,
                7L,
                userEntity1,
                null,
                false
        );


        when(urlRepositoryMock.findByShortUrlIdNative(any(String.class))).thenReturn(found);

        String actual = urlServiceTest.getLongUrl(shortUrlId);

        verify(urlRepositoryMock, times(1)).findByShortUrlIdNative(any(String.class));
        assertNotNull(actual);
        assertEquals("", actual);
    }










    // urlRepository.findByLongUrlNative, urlEntity != null
    // urlEntity.getShortUrlId() != null
    @Test
    void getShortUrl() {
        when(urlRepositoryMock.findByLongUrlNative(any(String.class))).thenReturn(urlEntity1);

        String actual = urlServiceTest.getShortUrl(longUrl);

        verify(urlRepositoryMock, times(1)).findByLongUrlNative(any(String.class));
        assertNotNull(actual);
        assertNotEquals("", actual);
        assertEquals(shortUrlId, actual);
    }

    // urlRepository.findByLongUrlNative, urlEntity = null
    // urlEntity.getShortUrlId() != null
    @Test
    void getShortUrlfindNull() {
        when(urlRepositoryMock.findByLongUrlNative(any(String.class))).thenReturn(null);

        String actual = urlServiceTest.getShortUrl(longUrl);

        verify(urlRepositoryMock, times(1)).findByLongUrlNative(any(String.class));
        assertNotNull(actual);
        assertEquals("", actual);
    }

    // urlRepository.findByLongUrlNative, urlEntity != null
    // urlEntity.getShortUrlId() = null
    @Test
    void getShortUrlNullshortUrl() {
        UrlEntity found = new UrlEntity(
                1L,
                null,
                "https://www.google.com",
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                0L,
                7L,
                userEntity1,
                null,
                false
        );


        when(urlRepositoryMock.findByLongUrlNative(any(String.class))).thenReturn(found);

        String actual = urlServiceTest.getShortUrl(longUrl);

        verify(urlRepositoryMock, times(1)).findByLongUrlNative(any(String.class));
        assertNotNull(actual);
        assertEquals("", actual);
    }



















    @Test
    void getAllUrls() {
        when(urlRepositoryMock.findAll()).thenReturn(List.of(urlEntity1, urlEntity2));
        when(mappersMock.convertToUrlCopy(urlEntity1)).thenReturn(urlCopyEntityDto1);
        when(mappersMock.convertToUrlCopy(urlEntity2)).thenReturn(urlCopyEntityDto2);

        List<UrlCopyEntityDto> actualUrls = urlServiceTest.getAllUrls();

        verify(urlRepositoryMock).findAll();
        verify(urlRepositoryMock, times(1)).findAll();
        verify(mappersMock, times(2)).convertToUrlCopy(any(UrlEntity.class));

        assertNotNull(actualUrls);
        assertTrue(actualUrls.size() > 0);
        assertEquals(2, actualUrls.size());
        assertEquals(1, actualUrls.get(0).getUrlId());
        assertEquals(7, actualUrls.get(0).getDeleteAfterDays());

    }

    @Test
    void getAllUrlsFindNull() {
        when(urlRepositoryMock.findAll()).thenReturn(null);
        //when(mappersMock.convertToUrlCopy(urlEntity1)).thenReturn(urlCopyEntityDto1);
        //when(mappersMock.convertToUrlCopy(urlEntity2)).thenReturn(urlCopyEntityDto2);

        List<UrlCopyEntityDto> actualUrls = urlServiceTest.getAllUrls();

        verify(urlRepositoryMock).findAll();
        verify(urlRepositoryMock, times(1)).findAll();
        verify(mappersMock, times(0)).convertToUrlCopy(any(UrlEntity.class));

        assertNotNull(actualUrls);
        assertTrue(actualUrls.size() == 0);
    }

    @Test
    void getAllUrlsFindEmpty() {
        when(urlRepositoryMock.findAll()).thenReturn(List.of());
        //when(mappersMock.convertToUrlCopy(urlEntity1)).thenReturn(urlCopyEntityDto1);
        //when(mappersMock.convertToUrlCopy(urlEntity2)).thenReturn(urlCopyEntityDto2);

        List<UrlCopyEntityDto> actualUrls = urlServiceTest.getAllUrls();

        verify(urlRepositoryMock).findAll();
        verify(urlRepositoryMock, times(1)).findAll();
        verify(mappersMock, times(0)).convertToUrlCopy(any(UrlEntity.class));

        assertNotNull(actualUrls);
        assertTrue(actualUrls.size() == 0);
        assertEquals(0, actualUrls.size());
    }


    @Test
    void updateCleanTimer() {
/*
        UrlCopyEntityDto ret = new UrlCopyEntityDto();
        UrlEntity urlEntity = urlRepository.findByShortUrlIdNative(updateUrl.getUrlId());
        if (urlEntity != null){
            urlEntity.setDeleteAfterDays(updateUrl.getNewTimer());
            urlEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            urlEntity = urlRepository.save(urlEntity);
            if (urlEntity != null && urlEntity.getDeleteAfterDays() !=null &&
                    urlEntity.getDeleteAfterDays() == updateUrl.getNewTimer()) {
                ret = mappers.convertToUrlCopy(urlEntity);
            }
        }
        return ret;

  */
        UrlEntity saved = new UrlEntity(
                2L,
                "wiki",
                "https://en.wikipedia.org/",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                3L,
                urlRequestUpdateDeleteTimerDto2.getNewTimer(),
                userEntity2,
                Timestamp.valueOf(LocalDateTime.now()),
                false
        );
        UrlCopyEntityDto expected  = new UrlCopyEntityDto(
                2L,
                "wiki",
                "https://en.wikipedia.org/",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                3L,
                urlRequestUpdateDeleteTimerDto2.getNewTimer(),
                userEntity2.getUserId(),
                Timestamp.valueOf(LocalDateTime.now()),
                false
        );


        when(urlRepositoryMock.findByShortUrlIdNative(any(String.class))).thenReturn(urlEntity2);
        when(urlRepositoryMock.save(any(UrlEntity.class))).thenReturn(saved);
        when(mappersMock.convertToUrlCopy(any(UrlEntity.class))).thenReturn(expected);

        UrlCopyEntityDto actual = urlServiceTest.updateCleanTimer(urlRequestUpdateDeleteTimerDto2);

        verify(urlRepositoryMock, times(1)).findByShortUrlIdNative(any(String.class));
        verify(urlRepositoryMock, times(1)).save(any(UrlEntity.class));
        verify(mappersMock, times(1)).convertToUrlCopy(any(UrlEntity.class));

        assertNotNull(actual);
        assertNotNull(actual.getUrlId());
        assertNotNull(actual.getLongUrl());
        assertNotNull(actual.getDeleteAfterDays());
        assertEquals(expected.getDeleteAfterDays(), actual.getDeleteAfterDays());
        assertNotNull(actual.getUpdatedAt());
        assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
    }

    // urlRepository.findByShortUrlIdNative, urlEntity = null
    // urlEntity = urlRepository.save(urlEntity);  urlEntity != null
    // urlEntity.getDeleteAfterDays() !=null
    // urlEntity.getDeleteAfterDays() == updateUrl.getNewTimer()
    @Test
    void updateCleanTimerNullFind() {
        UrlEntity saved = new UrlEntity(
                2L,
                "wiki",
                "https://en.wikipedia.org/",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                3L,
                urlRequestUpdateDeleteTimerDto2.getNewTimer(),
                userEntity2,
                Timestamp.valueOf(LocalDateTime.now()),
                false
        );
        UrlCopyEntityDto expected  = new UrlCopyEntityDto(
                2L,
                "wiki",
                "https://en.wikipedia.org/",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                3L,
                urlRequestUpdateDeleteTimerDto2.getNewTimer(),
                userEntity2.getUserId(),
                Timestamp.valueOf(LocalDateTime.now()),
                false
        );


        when(urlRepositoryMock.findByShortUrlIdNative(any(String.class))).thenReturn(null);
        //when(urlRepositoryMock.save(any(UrlEntity.class))).thenReturn(saved);
        //when(mappersMock.convertToUrlCopy(any(UrlEntity.class))).thenReturn(expected);

        UrlCopyEntityDto actual = urlServiceTest.updateCleanTimer(urlRequestUpdateDeleteTimerDto2);

        verify(urlRepositoryMock, times(1)).findByShortUrlIdNative(any(String.class));
        verify(urlRepositoryMock, times(0)).save(any(UrlEntity.class));
        verify(mappersMock, times(0)).convertToUrlCopy(any(UrlEntity.class));

        assertNotNull(actual);
        assertNull(actual.getUrlId());
        assertNull(actual.getLongUrl());
        assertNull(actual.getDeleteAfterDays());
//        assertEquals(expected.getDeleteAfterDays(), actual.getDeleteAfterDays());
        assertNull(actual.getUpdatedAt());
//        assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
    }




    // urlRepository.findByShortUrlIdNative, urlEntity != null
    // urlEntity = urlRepository.save(urlEntity);  urlEntity = null
    // urlEntity.getDeleteAfterDays() !=null
    // urlEntity.getDeleteAfterDays() == updateUrl.getNewTimer()
    @Test
    void updateCleanTimerNullSave() {
        UrlEntity saved = new UrlEntity(
                2L,
                "wiki",
                "https://en.wikipedia.org/",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                3L,
                urlRequestUpdateDeleteTimerDto2.getNewTimer(),
                userEntity2,
                Timestamp.valueOf(LocalDateTime.now()),
                false
        );
        UrlCopyEntityDto expected  = new UrlCopyEntityDto(
                2L,
                "wiki",
                "https://en.wikipedia.org/",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                3L,
                urlRequestUpdateDeleteTimerDto2.getNewTimer(),
                userEntity2.getUserId(),
                Timestamp.valueOf(LocalDateTime.now()),
                false
        );


        when(urlRepositoryMock.findByShortUrlIdNative(any(String.class))).thenReturn(urlEntity2);
        when(urlRepositoryMock.save(any(UrlEntity.class))).thenReturn(null);
        //when(mappersMock.convertToUrlCopy(any(UrlEntity.class))).thenReturn(expected);

        UrlCopyEntityDto actual = urlServiceTest.updateCleanTimer(urlRequestUpdateDeleteTimerDto2);

        verify(urlRepositoryMock, times(1)).findByShortUrlIdNative(any(String.class));
        verify(urlRepositoryMock, times(1)).save(any(UrlEntity.class));
        verify(mappersMock, times(0)).convertToUrlCopy(any(UrlEntity.class));

        assertNotNull(actual);
        assertNull(actual.getUrlId());
        assertNull(actual.getLongUrl());
        assertNull(actual.getDeleteAfterDays());
        assertNull(actual.getUpdatedAt());
    }

    // urlRepository.findByShortUrlIdNative, urlEntity != null
    // urlEntity = urlRepository.save(urlEntity);  urlEntity != null
    // urlEntity.getDeleteAfterDays() =null
    // urlEntity.getDeleteAfterDays() == updateUrl.getNewTimer()
    @Test
    void updateCleanTimerNullNewTimer() {
        UrlEntity saved = new UrlEntity(
                2L,
                "wiki",
                "https://en.wikipedia.org/",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                3L,
                null,
                userEntity2,
                Timestamp.valueOf(LocalDateTime.now()),
                false
        );
        UrlCopyEntityDto expected  = new UrlCopyEntityDto(
                2L,
                "wiki",
                "https://en.wikipedia.org/",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                3L,
                urlRequestUpdateDeleteTimerDto2.getNewTimer(),
                userEntity2.getUserId(),
                Timestamp.valueOf(LocalDateTime.now()),
                false
        );


        when(urlRepositoryMock.findByShortUrlIdNative(any(String.class))).thenReturn(urlEntity2);
        when(urlRepositoryMock.save(any(UrlEntity.class))).thenReturn(saved);
        //when(mappersMock.convertToUrlCopy(any(UrlEntity.class))).thenReturn(expected);

        UrlCopyEntityDto actual = urlServiceTest.updateCleanTimer(urlRequestUpdateDeleteTimerDto2);

        verify(urlRepositoryMock, times(1)).findByShortUrlIdNative(any(String.class));
        verify(urlRepositoryMock, times(1)).save(any(UrlEntity.class));
        verify(mappersMock, times(0)).convertToUrlCopy(any(UrlEntity.class));

        assertNotNull(actual);
        assertNull(actual.getUrlId());
        assertNull(actual.getLongUrl());
        assertNull(actual.getDeleteAfterDays());
        assertNull(actual.getUpdatedAt());
    }


    // urlRepository.findByShortUrlIdNative, urlEntity != null
    // urlEntity = urlRepository.save(urlEntity);  urlEntity != null
    // urlEntity.getDeleteAfterDays() !=null
    // urlEntity.getDeleteAfterDays() != updateUrl.getNewTimer()
    @Test
    void updateCleanTimerNewTimerWrong() {
        UrlEntity saved = new UrlEntity(
                2L,
                "wiki",
                "https://en.wikipedia.org/",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                3L,
                10L,
                userEntity2,
                Timestamp.valueOf(LocalDateTime.now()),
                false
        );
        UrlCopyEntityDto expected  = new UrlCopyEntityDto(
                2L,
                "wiki",
                "https://en.wikipedia.org/",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                3L,
                urlRequestUpdateDeleteTimerDto2.getNewTimer(),
                userEntity2.getUserId(),
                Timestamp.valueOf(LocalDateTime.now()),
                false
        );

        when(urlRepositoryMock.findByShortUrlIdNative(any(String.class))).thenReturn(urlEntity2);
        when(urlRepositoryMock.save(any(UrlEntity.class))).thenReturn(saved);
        //when(mappersMock.convertToUrlCopy(any(UrlEntity.class))).thenReturn(expected);

        UrlCopyEntityDto actual = urlServiceTest.updateCleanTimer(urlRequestUpdateDeleteTimerDto2);

        verify(urlRepositoryMock, times(1)).findByShortUrlIdNative(any(String.class));
        verify(urlRepositoryMock, times(1)).save(any(UrlEntity.class));
        verify(mappersMock, times(0)).convertToUrlCopy(any(UrlEntity.class));

        assertNotNull(actual);
        assertNull(actual.getUrlId());
        assertNull(actual.getLongUrl());
        assertNull(actual.getDeleteAfterDays());
        assertNull(actual.getUpdatedAt());
    }

    // urlRepository.findByShortUrlIdNative --> null, !null
// urlRepository.delete
// urlRepository.findByShortUrlIdNative --> null, !null
// !null, void, null
    @Test
    void deleteByShortUrl() {
        ShortUrlIdDto deleteUrl = new ShortUrlIdDto(
                "google"
        );

/*
        Boolean ret = true;
        UrlEntity urlEntity = urlRepository.findByShortUrlIdNative(shortUrlId.getUrlId());
        if(urlEntity != null) {
            urlRepository.delete(urlEntity);
            urlEntity = urlRepository.findByShortUrlIdNative(shortUrlId.getUrlId());
            if (urlEntity != null){
                ret = false;
            }
        } else {
            ret = false;
        }
        return ret;

 */
// Optional.ofNullable(null)

        when(urlRepositoryMock.findByShortUrlIdNative(any(String.class))).thenReturn(urlEntity1, null);
        //when(urlRepositoryMock.findByShortUrlIdNative("call2")).thenReturn(null));
        //when(urlRepositoryMock.findByShortUrlIdNative(any(String.class))).thenReturn(null));
        //        when(usersRepositoryMock.findById(testId)).thenReturn(Optional.of(userEntityTest1));

        //boolean res = urlServiceTest.deleteByShortUrl(deleteUrl);
        boolean res = urlServiceTest.deleteByShortUrl(shortUrlIdDto);


        //verify(usersRepositoryMock).delete(userEntityTest1);
        verify(urlRepositoryMock, times(2)).findByShortUrlIdNative(any(String.class));
        verify(urlRepositoryMock, times(1)).delete(any(UrlEntity.class));
        //verify(mappersMock, times(0)).convertToUrlCopy(any(UrlEntity.class));
        assertTrue(res);
    }

    // null
    @Test
    void deleteByShortUrlNull1Find() {
        when(urlRepositoryMock.findByShortUrlIdNative(any(String.class))).thenReturn(null, null);

        boolean res = urlServiceTest.deleteByShortUrl(shortUrlIdDto);

        //verify(usersRepositoryMock).delete(userEntityTest1);
        verify(urlRepositoryMock, times(1)).findByShortUrlIdNative(any(String.class));
        verify(urlRepositoryMock, times(0)).delete(any(UrlEntity.class));
        //verify(mappersMock, times(0)).convertToUrlCopy(any(UrlEntity.class));
        assertFalse(res);


    }

    // !null, void, !null
    @Test
    void deleteByShortUrlNotNull2Find() {
        when(urlRepositoryMock.findByShortUrlIdNative(any(String.class))).thenReturn(urlEntity1, urlEntity1);
        //when(urlRepositoryMock.findByShortUrlIdNative("call2")).thenReturn(null));
        //when(urlRepositoryMock.findByShortUrlIdNative(any(String.class))).thenReturn(null));
        //        when(usersRepositoryMock.findById(testId)).thenReturn(Optional.of(userEntityTest1));

        //boolean res = urlServiceTest.deleteByShortUrl(deleteUrl);
        boolean res = urlServiceTest.deleteByShortUrl(shortUrlIdDto);


        //verify(usersRepositoryMock).delete(userEntityTest1);
        verify(urlRepositoryMock, times(2)).findByShortUrlIdNative(any(String.class));
        verify(urlRepositoryMock, times(1)).delete(any(UrlEntity.class));
        //verify(mappersMock, times(0)).convertToUrlCopy(any(UrlEntity.class));
        assertFalse(res);
    }

}