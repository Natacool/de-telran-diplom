package de.telran.UrlShortener.services;

import de.telran.UrlShortener.configure.MapperUtil;
import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.entities.UrlEntity;
import de.telran.UrlShortener.utils.mapper.Mappers;
import de.telran.UrlShortener.repositories.UrlRepository;
import de.telran.UrlShortener.utils.generator.ShortUrlGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlService {
    private final UrlRepository urlRepository;
    private final Mappers mappers;
    private final ShortUrlGenerator urlGenerator;

    public String getGeneratedUrl(LongUrlDto longUrl){
        UrlEntity urlEntity = urlRepository.findByLongUrlNative(longUrl.getUrl());
        String shortUrl = "";
        if (urlEntity == null){
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());

            shortUrl = urlGenerator.generateShortUrl2();

            urlEntity = new UrlEntity(null,
                    shortUrl,
                    longUrl.getUrl(),
                    now, // now()
                    null,
                    0L,
                    7L,
                    null,
                    null,
                    false
                    );
            UrlEntity saved = urlRepository.save(urlEntity);
            String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
            shortUrl = homeURL + "/" + urlEntity.getShortUrlId();

            if (saved != urlEntity)
            {
                shortUrl = "";
            }
        }
        else {
            String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
            shortUrl = homeURL + "/" + urlEntity.getShortUrlId();
        }
        return shortUrl;
    }

    public String getRedirectUrl(String shortUrlId){
        String longUrl ="";
        UrlEntity urlEntity = urlRepository.findByShortUrlIdNative(shortUrlId);
        if (urlEntity != null && urlEntity.getLongUrl() != null){
            longUrl = urlEntity.getLongUrl();
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            Long plusOneClick = 0L;
            if (urlEntity.getClickAmount() != null) {
                plusOneClick = urlEntity.getClickAmount()+1L;
                urlEntity.setClickAmount(plusOneClick);
            }
            urlEntity.setClickedAt(now);

            urlEntity = urlRepository.save(urlEntity);
            if (urlEntity == null || (urlEntity != null &&
                (urlEntity.getLongUrl() == null || !urlEntity.getLongUrl().equals(longUrl)))){
                    longUrl ="";
            }
            if (urlEntity != null &&
                    (urlEntity.getClickAmount() == null || urlEntity.getClickAmount() != plusOneClick)) {
                    log.error(" Click Amount for URL ID: "+ urlEntity.getUrlId() + " NOT updated");
            }
            if (urlEntity != null &&
                    (urlEntity.getClickedAt() == null || urlEntity.getClickedAt() != now)) {
                log.error(" Date ClickedAt for URL ID: "+ urlEntity.getUrlId() + " NOT updated");
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
            urlEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
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
