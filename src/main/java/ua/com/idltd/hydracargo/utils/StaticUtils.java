package ua.com.idltd.hydracargo.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

//Возвращает имя польхователя сессии
public class StaticUtils {
    public static String GetUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        return userDetail.getUsername();
    }
}
