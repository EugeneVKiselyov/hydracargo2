package ua.com.idltd.hydracargo.request.box.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.request.box.entity.Box;
import ua.com.idltd.hydracargo.request.box.entity.VBox;


@Repository
public interface VBoxRepository extends CrudRepository<VBox, Long> {

    @Query(nativeQuery = true, value = "select * from VBox where req_id=:req_id")
    Iterable<VBox> findByREQ_ID(@Param("req_id") final Long req_id);

}
