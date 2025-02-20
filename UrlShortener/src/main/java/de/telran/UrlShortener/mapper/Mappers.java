package de.telran.UrlShortener.mapper;


import de.telran.UrlShortener.dtos.UrlCopyEntityDto;
import de.telran.UrlShortener.dtos.UserCopyEntityDto;
import de.telran.UrlShortener.entities.UrlEntity;
import de.telran.UrlShortener.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class Mappers {
    private final ModelMapper modelMapper;

    public UserCopyEntityDto convertToUserCopyDto(UserEntity userEntity) {
        if(userEntity==null) return new UserCopyEntityDto();

        UserCopyEntityDto userCopyDto = modelMapper.map(userEntity, UserCopyEntityDto.class);
        userCopyDto.setPasswordHash("*****");

        if (userCopyDto.getUpdatedAt() == null)
            userCopyDto.setUpdatedAt(userCopyDto.getRegisteredAt());

        return userCopyDto;
    }

    public UrlCopyEntityDto convertToUrlCopy(UrlEntity urlEntity) {
        if(urlEntity == null) return new UrlCopyEntityDto();

        UrlCopyEntityDto urlCopyDto = modelMapper.map(urlEntity, UrlCopyEntityDto.class);

        return urlCopyDto;
    }

/*
    public class StatisticUserRequestDto {
    private List<String> userEmails;
    private List<UserRoleEnum> userRoles;
    private List<UserStatusEnum> userStatuses;
    private Integer periodGenerated;
    private Integer periodClicked;
    private Boolean onlyUserInfo;
}

*/

/*

  public class StatisticUserResponseDto {
    private Long userId; //?
    private String userEmail;
    private UserRoleEnum role;
    private UserStatusEnum status;
    private Timestamp registeredAt;
    private Timestamp lastActiveAt;
    private Timestamp updatedAt;
    //optional
    private Long generatedAmount;
    private Long clickedAmount;
}

*/

/*
 public class StatisticClickedUrlRequestDto {
    // if userIds = null - get for all users, else for users from the list
    private List<Long> userIds;
    // if periodDays = 0 - get overall, else clickedAmount for the period
    private Integer periodDays;
    private Integer limitTop;
    // if descent = true - non-popular first
    private Boolean descent;

    // if groupUser = true - sort per User, then per amount, else per amount only
    private Boolean groupUser;
    //private List<String> userEmails;
    //private boolean total;
}

 */

/*
public class StatisticClickedUrlResponseDto {
    private String shortUrl;
    private Long clickedAmount;
    private Long userId;
    //private String userEmail;
    private String longUrl;
}

 */

/*
public class StatisticGeneratingUrlRequestDto {
    private List<Long> userIds;
    //private List<String> userEmails;
    //private Timestamp startDate;
    //private Timestamp endDate;
    private Integer periodDays;
    //private boolean total;

    //private Integer limitUser;
    private Boolean descent;

}

 */

/*

public class StatisticGeneratingUrlResponseDto {
    private Integer periodDays;
    private Long total;
    private Map<Long, Long> userInfo;
    //private Map<String, Long> userInfo;

    //private Map<Long, Map<Timestamp, Long>> userInfo;
    //private Map<String, Map<Timestamp, Long>> userInfo;

}

 */



/*
    public UserDto convertToUserDto(UsersEntity usersEntity) {
        if(usersEntity==null) return new UserDto();
        modelMapper.typeMap(UsersEntity.class, UserDto.class)
                .addMappings(mapper -> mapper.skip(UserDto::setEmail)); // исключаем этот метод из работы
        UserDto userDto = modelMapper.map(usersEntity, UserDto.class); //автомат
        if (userDto.getPasswordHash()!=null)
            userDto.setPasswordHash("***"); // замещаем данных

        // преобразовываем
        if (usersEntity.getFavorites()!=null) {
            Set<FavoritesDto> favoritesDtoSet = MapperUtil.convertSet(usersEntity.getFavorites(), this::convertToFavoritesDto);
            userDto.setFavorites(favoritesDtoSet);
        }

        CartDto cartDto = convertToCartDto(usersEntity.getCart()); // второй связанный объект
        userDto.setCart(cartDto);
        return userDto;
    }

    public CartDto convertToCartDto(CartEntity cartEntity) {
        CartDto cartDto = null;
        if(cartEntity!=null)
            cartDto = modelMapper.map(cartEntity, CartDto.class); //автомат
        return cartDto;
    }

    public FavoritesDto convertToFavoritesDto(FavoritesEntity favoritesEntity) {
        FavoritesDto favoritesDto = modelMapper.map(favoritesEntity, FavoritesDto.class); //автомат
        favoritesDto.setUser(null);
        return favoritesDto;
    }

    public UsersEntity convertToUserEntity(UserDto userDto) {
        UsersEntity usersEntity = modelMapper.map(userDto, UsersEntity.class); //автомат
        return usersEntity;
    }

    public ProductsDto convertToProductsDto(ProductsEntity products) {
        ProductsDto productsDto = modelMapper.map(products, ProductsDto.class);
        return productsDto;
    }

    public ProductsEntity convertToProducts(ProductsDto productsDto) {
        ProductsEntity products = modelMapper.map(productsDto, ProductsEntity.class);
        return products;
    }
*/

}
