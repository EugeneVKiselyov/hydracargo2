package ua.com.idltd.hydracargo.request_status.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.request_status.entity.Request_status;

import java.util.List;

@Repository
public interface Request_statusRepository extends CrudRepository<Request_status, Long> {

    @Query(nativeQuery = true, value = "select * from Request_status where rs_id=?1")
    List<Request_status> queryByRS_ID(Long rs_id);

    @Query(nativeQuery = true, value = "select * from Request_status order by rs_id asc")
    List<Request_status> findAllOrderByRs_idAsc();

    @Query(nativeQuery = true, value = "select * from Request_status where rs_id in :rs_ids order by rs_id")
    List<Request_status> queryByRs_idList(@Param("rs_ids") List<Long> rs_ids);
}
