package ua.com.idltd.hydracargo.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.PrintWriter;
import java.io.StringWriter;


public class StaticUtils {
    //Возвращает имя пользователя сессии
    public static String GetUserName() {
        String result=null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        result = auth.getName();
        return result;
    }

    //Возвращает имя польхователя сессии
    public static String ConvertTraceExceptionToText(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
