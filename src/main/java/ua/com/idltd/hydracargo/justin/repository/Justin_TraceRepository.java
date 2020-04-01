package ua.com.idltd.hydracargo.justin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.justin.entity.Justin_Trace;

import java.util.List;


@Repository
public interface Justin_TraceRepository extends CrudRepository<Justin_Trace, Long> {

    @Query(nativeQuery = true, value = "select * from JUSTIN_TRACE where JT_STATUS is null or JT_STATUS<>2")
    List<Justin_Trace> getForProcessing();
}
