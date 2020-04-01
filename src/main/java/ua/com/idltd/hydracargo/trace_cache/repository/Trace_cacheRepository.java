package ua.com.idltd.hydracargo.trace_cache.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.trace_cache.entity.Trace_cache;

import java.util.List;

@Repository
public interface Trace_cacheRepository extends CrudRepository<Trace_cache, Long> {

    @Query(nativeQuery = true, value = "select * from Trace_cache where trc_shipment = ?1 or trc_tracenum=?1 order by trc_eventtime desc")
    List<Trace_cache> queryByShipment(String shipment);

    @Query(nativeQuery = true, value = "select count(trc_id) from Trace_cache where trc_shipment = ?1 and trc_eventtime=to_date(?2||' '||?3,'YYYY-MM-DD HH24:MI:ss') order by trc_eventtime desc")
    Long queryByShipmentJustinDataTime(String shipment, String justindate, String justintime);
}
