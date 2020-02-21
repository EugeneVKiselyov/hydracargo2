package ua.com.idltd.hydracargo.scan.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.repository.CustomRepository;
import ua.com.idltd.hydracargo.scan.entity.Declaration_cache_scan;
import ua.com.idltd.hydracargo.scan.entity.Declaration_scan;

import java.util.List;

@Repository
public interface Declaration_cache_scanRepository extends CustomRepository<Declaration_cache_scan, Long> {
    @Query(nativeQuery = true, value = "select d.dc_id, d.dis_id, d.dc_shipment from Declaration_cache d" +
                                        " where d.dis_id = ?1 and d.dc_user_name = ?2" +
                                        " and d.dc_shipment not in (select ds.ds_shipment from Declaration_scan ds where ds.DIS_ID = d.DIS_ID and ds.DC_ID=d.dc_id)")
    List<Declaration_cache_scan> findByDis_idandNotScan(Long dis_id, String dc_user_name);
}
