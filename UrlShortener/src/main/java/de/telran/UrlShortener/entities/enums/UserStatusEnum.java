package de.telran.UrlShortener.entities.enums;

public enum UserStatusEnum {
    ACTIVE("ACTIVE"),
    BLOCKED("BLOCKED"),
    DELETED("DELETED");

    private String title;

    UserStatusEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
