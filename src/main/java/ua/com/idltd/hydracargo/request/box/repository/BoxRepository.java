package ua.com.idltd.hydracargo.request.box.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.request.box.entity.Box;

import java.util.Optional;

@Repository
public interface BoxRepository extends CrudRepository<Box, Long> {

    @Query(nativeQuery = true, value = "select * from Box where req_id=:req_id")
    Iterable<Box> findByREQ_ID(@Param("req_id") final Long req_id);

}
