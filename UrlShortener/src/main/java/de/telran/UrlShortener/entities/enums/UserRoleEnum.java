package de.telran.UrlShortener.entities.enums;

public enum UserRoleEnum {
    CLIENT("CLIENT"),
    ADMIN("ADMIN");

    private String title;

    UserRoleEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
