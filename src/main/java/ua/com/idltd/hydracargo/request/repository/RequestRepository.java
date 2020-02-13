package ua.com.idltd.hydracargo.request.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.request.entity.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    @Query(nativeQuery = true, value = "select * from Request where req_id=:req_id")
    Optional<Request> findByREQ_ID(@Param("req_id") final Long req_id);

    @Query(nativeQuery = true, value = "select * from Request r where r.cnt_id = PKG_RIGHT.GETCNT_ID( r.cnt_id, :username )")
    List<Request> getAllByUser(@Param("username") String usename);
    @Query(nativeQuery = true, value = "select * from Request r where r.req_id=:req_id and r.cnt_id = PKG_RIGHT.GETCNT_ID( r.cnt_id, :username )")
    List<Request> getByReq_idandUser(@Param("username") String usename, @Param("req_id") Long req_id);
}
