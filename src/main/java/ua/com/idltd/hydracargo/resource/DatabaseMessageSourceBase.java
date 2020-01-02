package ua.com.idltd.hydracargo.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class DatabaseMessageSourceBase extends AbstractResourceBasedMessageSource {

    private static final Logger LOG = LoggerFactory
            .getLogger(DatabaseMessageSourceBase.class);

    private Messages messages;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = messages.getMessage(code, locale);
        return (msg != null ? createMessageFormat(msg, locale) : createMessageFormat(code, locale));
//        return createMessageFormat(msg, locale);

    }

    @PostConstruct
    public void init() {

        String i18nQuery = this.getI18NSqlQuery();

        LOG.info("Initializing message source using query [{}]", i18nQuery);

        this.messages = jdbcTemplate.query(i18nQuery,
                new ResultSetExtractor<Messages>() {
                    @Override
                    public Messages extractData(ResultSet rs)
                            throws SQLException, DataAccessException {

                        return extractI18NData(rs);
                    }
                });
    }

    /**
     * Returns sql query used to fetch the messages from the database.
     *
     * @return sql query string
     */
    protected abstract String getI18NSqlQuery();

    /**
     * Extracts messages from the given result set.
     *
     * @param rs
     *            is a result set
     * @return initialized {@link Messages} instance
     * @throws SQLException
     *             if a SQLException is encountered getting column values or
     *             navigating (that is, there's no need to catch SQLException)
     * @throws DataAccessException
     *             in case of custom exceptions
     */
    protected abstract Messages extractI18NData(ResultSet rs)
            throws SQLException, DataAccessException;

    /**
     *
     * Messages bundle
     */
    protected static final class Messages {

        /* <code, <locale, message>> */
        private Map<String, Map<Locale, String>> messages;

        public void addMessage(String code, Locale locale, String msg) {
            if (messages == null)
                messages = new HashMap<String, Map<Locale, String>>();

            Map<Locale, String> data = messages.get(code);
            if (data == null) {
                data = new HashMap<Locale, String>();
                messages.put(code, data);
            }

            data.put(locale, msg);
        }

        public String getMessage(String code, Locale locale) {
            Map<Locale, String> data = messages.get(code);
            return data != null ? data.get(locale) : null;
        }
    }

}
