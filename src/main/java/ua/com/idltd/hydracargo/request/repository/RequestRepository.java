package ua.com.idltd.hydracargo.request.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.request.entity.Request;
import java.util.Optional;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    @Query(nativeQuery = true, value = "select * from Request where req_id=:req_id")
    Optional<Request> findByREQ_ID(@Param("req_id") final Long req_id);

}
