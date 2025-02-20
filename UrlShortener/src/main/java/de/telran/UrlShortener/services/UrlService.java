package de.telran.UrlShortener.services;

import de.telran.UrlShortener.configure.MapperUtil;
import de.telran.UrlShortener.dtos.UrlCopyEntityDto;
import de.telran.UrlShortener.dtos.UrlDto;
import de.telran.UrlShortener.entities.UrlEntity;
import de.telran.UrlShortener.mapper.Mappers;
import de.telran.UrlShortener.repositories.UrlRepository;
import de.telran.UrlShortener.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
//@NoArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final Mappers mappers;

    private Map<String, String> mapUrls;


    @PostConstruct
    void init() {
        //urlRepository. delete find save
        mapUrls = new HashMap<>();
        mapUrls.put("w", "https://en.wikipedia.org/");
        mapUrls.put("g", "https://google.com/");
        mapUrls.put("y", "https://ya.ru/");
        System.out.println("Выполняем логику при создании объекта "
                + this.getClass().getName());
    }

    public String getGeneratedUrl(UrlDto longUrl){
        // String longUrl = longUrl.getUrl
        // OR UrlEntity = mapper.convert(UrlDto)
        // UrlEntity1 = UrlRepo.findByLongUrl(longUrl)
        // if(UrlEntity1 != null) return UrlEntity1.shortUrl
        // else shortUrl = generateNewShortUrl(longUrl)
        // UrlEntityNew(long, short)
        // UrlEntitySaved = UrlRepo.save(UrlEntityNew)
        // return UrlEntitySaved.getShortUrl
        String shortUrl = longUrl.getUrl().substring(8,15);
        return shortUrl;
    }

//    public String getRedirectUrl(UrlDto shortUrlDto){
    public String getRedirectUrl(String shortUrl){
        // shortUrl = UrlDto.getUrl()
        // String LongUrl = UrlRepo.findByShortUrl(shortUrl)
        // if (LongUrl != null) return LongUrl
        // else Exception (not found)
        // OR null OR ""
        String longUrl;
        //String shortUrl = shortUrlDto.getUrl();
        if (shortUrl.equals("w")){
            longUrl = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BE%D0%BA%D1%80%D0%B0%D1%89%D0%B5%D0%BD%D0%B8%D0%B5_URL";
        } else {
            longUrl = "https://www.google.com/";
        }
        return longUrl;
    }


    public List<UrlCopyEntityDto> getAllUrls(){
        List<UrlEntity> urls = urlRepository.findAll();
        List<UrlCopyEntityDto> urlsCopy = MapperUtil.convertList(urls, mappers::convertToUrlCopy);
        return urlsCopy;


    }

    //GET
    //??? GetAllUrls(USER) – user, admin [statistic]
    //??? GetAllUrls() – admin [statistic]
    //RedirectUrl(ShortUrl) – anon, user, admin [url]


    //POST
    //GenerateUrl(LongUrl) – anon, user, admin [url]


    //PUT
    //? UpdateCleanTimer(url_id, newTimer) – admin [url]
    //UpdateCleanTimer(short_url, newTimer) – admin [url]
    //? UpdateCleanTimer(user_id, newTimer) – admin [url]

    public UrlDto updateCleanTimer(UrlDto shortUrl, Integer newTimer) {
        UrlDto ret = new UrlDto();
        // UrlEntity = mapper.convert(UrlDto)
        // UrlEntity(update timer -- newTimer)
        // UrlEntity1 = UrlRepo.save(UpdateEntity)
        // UrlDto = mapper.convert(UrlEntity1)
        return ret;
    }

    public void deleteUrl(UrlDto shortUrl) {
        // UrlEntity = UrlRepo.findByShortUrl(shortUrl.getUrl)
        // if(UrlRepo != null) UrlRepo.delete(UrlEntity)
        // else Exception

    }

    //DELETE
    //?? DeleteUrl(id) – user, admin [url]
    //DeleteUrl(short_url) – user, admin [url]

    //?? DeleteUrl(userId, urlId) – admin [url]
    //? DeleteUrl(userId, short_url) – admin [url]
    //? DeleteUrls(userId) – admin [url]


    @PreDestroy
    void destroy() {
        mapUrls.clear();
        System.out.println("Выполняем логику при окончании работы с  объектом "+this.getClass().getName());
    }
}
