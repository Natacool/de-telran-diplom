package de.telran.UrlShortener.services;

import de.telran.UrlShortener.configure.MapperUtil;
import de.telran.UrlShortener.dtos.LongUrlDto;
import de.telran.UrlShortener.dtos.UrlCopyEntityDto;
import de.telran.UrlShortener.dtos.ShortUrlIdDto;
import de.telran.UrlShortener.dtos.UrlRequestUpdateDeleteTimerDto;
import de.telran.UrlShortener.entities.UrlEntity;
import de.telran.UrlShortener.mapper.Mappers;
import de.telran.UrlShortener.repositories.UrlRepository;
import de.telran.UrlShortener.utils.ShortUrlGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
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
        if (urlEntity != null){
            longUrl = urlEntity.getLongUrl();
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
        List<UrlEntity> urls = urlRepository.findAll();
        List<UrlCopyEntityDto> urlsCopy = MapperUtil.convertList(urls, mappers::convertToUrlCopy);
        return urlsCopy;
    }

    public UrlCopyEntityDto updateCleanTimer(UrlRequestUpdateDeleteTimerDto updateUrl) {
        UrlCopyEntityDto ret = new UrlCopyEntityDto();
        UrlEntity urlEntity = urlRepository.findByShortUrlIdNative(updateUrl.getUrlId());
        if (urlEntity != null){
            urlEntity.setDeleteAfterDays(updateUrl.getNewTimer());
            urlEntity = urlRepository.save(urlEntity);
            if (urlEntity != null) {
                ret = mappers.convertToUrlCopy(urlEntity);
            }
        }
        return ret;
    }

/*
    public Boolean deleteUrlById(Long id) {
        Boolean ret = true;
        urlRepository.deleteById(id);
        // ? do we need this check?
        UrlEntity urlEntity = urlRepository.findById(id).orElse(null);
        if (urlEntity != null){
            ret = false;
        }
        return ret;
    }
*///
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
/*
    public Boolean deleteByShortUrl(String shortUrlId) {
        Boolean ret = true;
        UrlEntity urlEntity = urlRepository.findByShortUrlIdNative(shortUrlId);
        if(urlEntity != null) {
            urlRepository.delete(urlEntity);
            // ? do we need this check?
            urlEntity = urlRepository.findByShortUrlIdNative(shortUrlId);
            if (urlEntity != null){
                ret = false;
            }
        } else {
            ret = false;
        }
        return ret;
    }
*/
}
