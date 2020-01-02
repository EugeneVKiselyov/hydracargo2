package ua.com.idltd.hydracargo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.user.entity.Roles;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {

    @Query(nativeQuery = true, value = "select ROL_SHEDULER_PROCEDURE from ROLES where rol_sheduler_procedure is not null")
    List<String> getAllROL_SHEDULER_PROCEDURE();

}
