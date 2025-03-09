package de.telran.UrlShortener.services;

import de.telran.UrlShortener.configure.MapperUtil;
import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.entities.UrlEntity;
import de.telran.UrlShortener.entities.UserEntity;
import de.telran.UrlShortener.utils.common.CommonUtils;
import de.telran.UrlShortener.utils.mapper.Mappers;
import de.telran.UrlShortener.repositories.UrlRepository;
import de.telran.UrlShortener.utils.generator.ShortUrlGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlService {
    private final UrlRepository urlRepository;
    private final Mappers mappers;
    private final ShortUrlGenerator urlGenerator;
    private final CommonUtils utils;

    public String getGeneratedUrl(LongUrlDto longUrl){
        UrlEntity urlEntity = urlRepository.findByLongUrlNative(longUrl.getUrl());
        String shortUrl = "";
        if (urlEntity == null){
            Timestamp now = utils.getCurrentTimestamp();

            shortUrl = urlGenerator.generateShortUrl2();

            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(0L);

            urlEntity = new UrlEntity(null,
                    shortUrl,
                    longUrl.getUrl(),
                    now,
                    null,
                    0L,
                    7L,
                    userEntity,
                    null,
                    false
                    );
            UrlEntity saved = urlRepository.save(urlEntity);
            String homeURL = utils.getHomeUrl();
            shortUrl = homeURL + "/" + urlEntity.getShortUrlId();

            if (saved == null || (saved != null
                    && (saved.getShortUrlId() != urlEntity.getShortUrlId()
                    || saved.getLongUrl() != urlEntity.getLongUrl()
            )))
            {
                shortUrl = "";
            }
        }
        else {
            String homeURL = utils.getHomeUrl();
            shortUrl = homeURL + "/" + urlEntity.getShortUrlId();
        }
        return shortUrl;
    }

    public String getRedirectUrl(String shortUrlId){
        String longUrl ="";
        UrlEntity urlEntity = urlRepository.findByShortUrlIdNative(shortUrlId);
        if (urlEntity != null && urlEntity.getLongUrl() != null){
            longUrl = urlEntity.getLongUrl();
            Timestamp now = utils.getCurrentTimestamp();
            Long plusOneClick;
            if (urlEntity.getClickAmount() == null) {
                plusOneClick = 1L;
            } else {
                plusOneClick = urlEntity.getClickAmount()+1L;
            }

            urlEntity.setClickAmount(plusOneClick);
            urlEntity.setClickedAt(now);

            urlEntity = urlRepository.save(urlEntity);
            if (urlEntity == null || (urlEntity != null &&
                (urlEntity.getLongUrl() == null || !urlEntity.getLongUrl().equals(longUrl)))){
                    longUrl ="";
            }
            if (urlEntity != null &&
                    (urlEntity.getClickAmount() == null || urlEntity.getClickAmount() != plusOneClick)) {
                    log.warn(" Click Amount for URL ID: "+ urlEntity.getUrlId() + " NOT updated");
            }
            if (urlEntity != null &&
                    (urlEntity.getClickedAt() == null || urlEntity.getClickedAt() != now)) {
                log.warn(" Date ClickedAt for URL ID: "+ urlEntity.getUrlId() + " NOT updated");
            }
        }
        return longUrl;
    }

    public String getLongUrl(String shortUrlId){
        UrlEntity urlEntity = urlRepository.findByShortUrlIdNative(shortUrlId);
        String longUrl = "";
        if (urlEntity != null && urlEntity.getLongUrl() != null){
            longUrl = urlEntity.getLongUrl();
        }
        return longUrl;
    }

    public String getShortUrl(String longUrl){
        UrlEntity urlEntity = urlRepository.findByLongUrlNative(longUrl);
        String shortUrl = "";
        if (urlEntity != null && urlEntity.getShortUrlId() != null){
            shortUrl = urlEntity.getShortUrlId();
        }
        return shortUrl;
    }

    public List<UrlCopyEntityDto> getAllUrls(){
        List<UrlCopyEntityDto> urlsCopy = new ArrayList<>();
        List<UrlEntity> urls = urlRepository.findAll();
        if (urls != null && urls.size() > 0) {
            urlsCopy = MapperUtil.convertList(urls, mappers::convertToUrlCopy);
        }
        return urlsCopy;
    }

    public UrlCopyEntityDto updateCleanTimer(UrlRequestUpdateDeleteTimerDto updateUrl) {
        UrlCopyEntityDto ret = new UrlCopyEntityDto();
        UrlEntity urlEntity = urlRepository.findByShortUrlIdNative(updateUrl.getUrlId());
        if (urlEntity != null){
            urlEntity.setDeleteAfterDays(updateUrl.getNewTimer());
            Timestamp now = utils.getCurrentTimestamp();
            urlEntity.setUpdatedAt(now);
            urlEntity = urlRepository.save(urlEntity);
            if (urlEntity != null && urlEntity.getDeleteAfterDays() !=null &&
                    urlEntity.getDeleteAfterDays() == updateUrl.getNewTimer()) {
                ret = mappers.convertToUrlCopy(urlEntity);
            }
        }
        return ret;
    }

    public Boolean deleteByShortUrl(ShortUrlIdDto shortUrlId) {
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
    }
}
