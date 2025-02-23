package de.telran.UrlShortener.services;

import de.telran.UrlShortener.configure.MapperUtil;
import de.telran.UrlShortener.dtos.UrlCopyEntityDto;
import de.telran.UrlShortener.dtos.UrlDto;
import de.telran.UrlShortener.dtos.UrlRequestUpdateDto;
import de.telran.UrlShortener.entities.UrlEntity;
import de.telran.UrlShortener.mapper.Mappers;
import de.telran.UrlShortener.repositories.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
        String shortUrl;
        if (urlEntity == null){
            shortUrl = longUrl.getUrl().substring(8,15);
            urlEntity = new UrlEntity(null,
                    shortUrl,
                    longUrl.getUrl(),
                    null, // now()
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
        String longUrl = "llll";
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

    public UrlCopyEntityDto updateCleanTimer(UrlRequestUpdateDto updateUrl) {
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

    public boolean deleteUrlById(Long id) {
        boolean ret = true;
        urlRepository.deleteById(id);
        // ? do we need this check?
        UrlEntity urlEntity = urlRepository.findById(id).orElse(null);
        if (urlEntity != null){
            ret = false;
        }

        return ret;
    }

    public boolean deleteByShortUrl(UrlDto shortUrl) {
        boolean ret = true;
        UrlEntity urlEntity = urlRepository.findByShortUrlNative(shortUrl.getUrl());
        if(urlEntity != null) {
            urlRepository.delete(urlEntity);
            // ? do we need this check?
            urlEntity = urlRepository.findByShortUrlNative(shortUrl.getUrl());
            if (urlEntity != null){
                ret = false;
            }
        }
        // ??? else Exception
        return ret;
    }

    public boolean deleteByShortUrl(String shortUrl) {
        boolean ret = true;
        UrlEntity urlEntity = urlRepository.findByShortUrlNative(shortUrl);
        if(urlEntity != null) {
            urlRepository.delete(urlEntity);
            // ? do we need this check?
            urlEntity = urlRepository.findByShortUrlNative(shortUrl);
            if (urlEntity != null){
                ret = false;
            }
        }
        // ??? else Exception
        return ret;
    }

}
