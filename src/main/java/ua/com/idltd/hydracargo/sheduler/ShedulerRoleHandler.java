package ua.com.idltd.hydracargo.sheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.com.idltd.hydracargo.user.repository.RolesRepository;

import javax.sql.DataSource;
import java.util.List;

@Component
public class ShedulerRoleHandler {

    private final DataSource dataSource;
    private final RolesRepository rolesRepository;

    @Autowired
    public ShedulerRoleHandler(@Qualifier("dataSource")DataSource dataSource, RolesRepository rolesRepository) {
        this.dataSource = dataSource;

        this.rolesRepository = rolesRepository;
    }

    @Scheduled(fixedRateString = "${scheduler.rolehandler.fixedRate.in.milliseconds}")
    public void handleAll(){
        List<String> role_procedureList = rolesRepository.getAllROL_SHEDULER_PROCEDURE();
        for (String rp : role_procedureList) {
            SqlParameterSource in = new MapSqlParameterSource();
            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource)
                    .withCatalogName("PKG_SCHEDULER")
                    .withProcedureName(rp);
            simpleJdbcCall.execute();
        }

    }
}
