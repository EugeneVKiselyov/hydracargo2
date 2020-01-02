package ua.com.idltd.hydracargo.bot.telegram.bot;


import ua.com.idltd.hydracargo.bot.telegram.bot.entity.TelegramUser;

import java.util.Locale;

public class BotUser {
    private final String DEFAULT_LANG="ru";

    private final Long tu_id;
    private final Long telegram_id;
    private final Long role_id;
    private final String lang;
    private Locale locale;
    private final String userName;
    private final String firstName;
    private final String lastName;

    public BotUser(Long tu_id, Long telegram_id, Long role_id, String lang, String userName, String firstName, String lastName) {
        this.tu_id = tu_id;
        this.telegram_id = telegram_id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        if (role_id==null) this.role_id=0L; else this.role_id = role_id;
        if (lang==null) this.lang=DEFAULT_LANG; else this.lang=lang;
        this.locale=new Locale.Builder().setLanguage(this.lang).build();
    }

    public BotUser(TelegramUser telegramUser) {
        this.tu_id = telegramUser.tu_id;
        this.telegram_id = telegramUser.tu_telegramid;
        this.userName = telegramUser.tu_username;
        this.firstName = telegramUser.tu_firstname;
        this.lastName = telegramUser.tu_lastname;
        if (telegramUser.rol_id==null) this.role_id=0L; else this.role_id = telegramUser.rol_id;
        if (telegramUser.tu_languagecode==null) this.lang=DEFAULT_LANG; else this.lang=telegramUser.tu_languagecode;
        this.locale=new Locale.Builder().setLanguage(this.lang).build();
    }

    public Long getTu_id() {
        return tu_id;
    }

    public Long getTelegram_id() {
        return telegram_id;
    }

    public Long getRole_id() {
        return role_id;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLang() {
        return lang;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
