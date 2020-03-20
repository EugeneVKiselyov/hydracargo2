package ua.com.idltd.hydracargo.dispatch.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.dispatch.entity.Dispatch;
import ua.com.idltd.hydracargo.repository.CustomRepository;
import ua.com.idltd.hydracargo.request.entity.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface DispatchRepository extends CustomRepository<Dispatch, Long> {

    @Query(nativeQuery = true, value = "select * from Dispatch where dis_id=?1")
    Optional<Dispatch> findByDis_ID(Long dis_id);

    @Query(nativeQuery = true, value = "select * from Dispatch where req_id=?1")
    Optional<Dispatch> findByReq_ID(Long req_id);
}
