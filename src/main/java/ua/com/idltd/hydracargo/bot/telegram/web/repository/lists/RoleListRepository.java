package ua.com.idltd.hydracargo.bot.telegram.web.repository.lists;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.bot.telegram.web.entity.lists.RolesList;

import java.util.List;

@Repository
public interface RoleListRepository extends CrudRepository<RolesList, Long> {
    @Query(nativeQuery = true, value = "select * from ROLES")
    List<RolesList> getAllRoles();

    @Query(nativeQuery = true, value = "select * from ROLES WHERE ROL_ID in (4,5)")
    List<RolesList> getSendListRoles();
}
