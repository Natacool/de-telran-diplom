-- liquibase formatted sql

-- changeset Natacool:Urls
create table Urls(
	UrlID INT primary key auto_increment NOT NULL,
    ShortUrlId varchar(70) not null unique,
    LongUrl varchar(2048) not null,
    CreatedAt datetime NOT NULL,
	ClickedAt datetime NULL,
    ClickAmount INT default 0,
    DeleteAfter INT default 7,
    userId INT default 0,
    UpdatedAt datetime NULL,
    Favorite BOOL default false
);


-- changeset Natacool:Users
create table Users(
	UserID BIGINT primary key auto_increment NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Role ENUM('CLIENT', 'ADMIN') NULL, -- default 'CLIENT'
    Status ENUM('ACTIVE', 'BLOCKED', 'DELETED') NULL, -- default 'ACTIVE'
    RegisteredAt datetime NOT NULL,
	LastActiveAt datetime, -- NOT NULL, -- can be get from last created url
    UpdatedAt datetime NULL,
    PasswordHash VARCHAR(255) NULL
);

