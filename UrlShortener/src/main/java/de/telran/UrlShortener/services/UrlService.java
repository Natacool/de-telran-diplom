package de.telran.UrlShortener.services;

import de.telran.UrlShortener.configure.MapperUtil;
import de.telran.UrlShortener.dtos.UrlCopyEntityDto;
import de.telran.UrlShortener.dtos.UrlDto;
import de.telran.UrlShortener.dtos.UrlRequestUpdateDeleteTimerDto;
import de.telran.UrlShortener.entities.UrlEntity;
import de.telran.UrlShortener.mapper.Mappers;
import de.telran.UrlShortener.repositories.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final Mappers mappers;

    public void createUrl(){

    }

    public String getGeneratedUrl(UrlDto longUrl){
        UrlEntity urlEntity = urlRepository.findByLongUrlNative(longUrl.getUrl());
        String shortUrl = "";
        if (urlEntity == null){
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            shortUrl = longUrl.getUrl().substring(8,15);
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
            if (saved != urlEntity)
            {
                //shortUrl = saved.getShortUrl();
                shortUrl = "";
            }
        }
        else {
            shortUrl = urlEntity.getShortUrl();
        }

        return shortUrl;
    }

    public String getRedirectUrl(String shortUrl){
        String longUrl ="";
        UrlEntity urlEntity = urlRepository.findByShortUrlNative(shortUrl);
        if (urlEntity != null){
            longUrl = urlEntity.getLongUrl();
        }

        return longUrl;
    }

    public String getLongUrl(String shortUrl){
        UrlEntity urlEntity = urlRepository.findByShortUrlNative(shortUrl);
        String longUrl = "";
        if (urlEntity != null && urlEntity.getLongUrl() != null){
            longUrl = urlEntity.getLongUrl();
        }
        return longUrl;
    }

    public String getShortUrl(String longUrl){
        UrlEntity urlEntity = urlRepository.findByLongUrlNative(longUrl);
        String shortUrl = "ssss";
        if (urlEntity != null && urlEntity.getShortUrl() != null){
            shortUrl = urlEntity.getShortUrl();
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

        UrlEntity urlEntity = urlRepository.findByShortUrlNative(updateUrl.getUrl());
        if (urlEntity != null){
            urlEntity.setDeleteAfterDays(updateUrl.getNewTimer());
            urlEntity = urlRepository.save(urlEntity);
            //urlEntity = urlRepository.findById(urlEntity.getUrlId()).orElse(null);
            if (urlEntity != null) {
                ret = mappers.convertToUrlCopy(urlEntity);
            }
        }
        return ret;
    }

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

    public Boolean deleteByShortUrl(UrlDto shortUrl) {
        Boolean ret = true;
        UrlEntity urlEntity = urlRepository.findByShortUrlNative(shortUrl.getUrl());
        if(urlEntity != null) {
            urlRepository.delete(urlEntity);
            // ? do we need this check?
            urlEntity = urlRepository.findByShortUrlNative(shortUrl.getUrl());
            if (urlEntity != null){
                ret = false;
            }
        } else {
            ret = false;
        }

        // ??? else Exception
        return ret;
    }

    public Boolean deleteByShortUrl(String shortUrl) {
        Boolean ret = true;
        UrlEntity urlEntity = urlRepository.findByShortUrlNative(shortUrl);
        if(urlEntity != null) {
            urlRepository.delete(urlEntity);
            // ? do we need this check?
            urlEntity = urlRepository.findByShortUrlNative(shortUrl);
            if (urlEntity != null){
                ret = false;
            }
        } else {
            ret = false;
        }
        // ??? else Exception
        return ret;
    }

}
