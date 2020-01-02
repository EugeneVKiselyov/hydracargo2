package ua.com.idltd.hydracargo.resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

@Component("messageSource")
public class DatabaseI18NMessageSource extends DatabaseMessageSourceBase {

    private static final String I18N_QUERY = "select code, locale, msg from i18n";

    @Override
    protected String getI18NSqlQuery() {
        return I18N_QUERY;
    }

    @Override
    protected Messages extractI18NData(ResultSet rs) throws SQLException, DataAccessException {

        Messages messages = new Messages();
        while (rs.next()) {
            Locale locale = new Locale(rs.getString("locale"));
            messages.addMessage(rs.getString("code"), locale, rs.getString("msg"));
        }
        return messages;
    }
}
