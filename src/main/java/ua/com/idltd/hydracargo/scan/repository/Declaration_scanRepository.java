package ua.com.idltd.hydracargo.scan.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.scan.entity.Declaration_scan;

import java.util.List;

@Repository
public interface Declaration_scanRepository extends CrudRepository<Declaration_scan, Long> {
    @Query(nativeQuery = true, value = "select ds_id,dis_id,ds_shipment,ds_color,dc_id,ds_scanfound, ds_order from Declaration_scan where dis_id = ?1 and dc_id is null and DS_SCANFOUND is null" +
                                       " order by DS_ORDER desc")
    List<Declaration_scan> findByDis_idandDc_idIsNullandDs_scanfoundIsNull(Long dis_id);
    @Query(nativeQuery = true, value = "select ds_id,dis_id,ds_shipment,ds_color,dc_id,ds_scanfound, ds_order" +
                                       " from Declaration_scan where dis_id = ?1 and DS_SCANFOUND<>0 and DS_SCANFOUND is not null" +
                                       " order by DS_ORDER desc")
    List<Declaration_scan> findByDis_idandScanWithError(Long dis_id);
    @Query(nativeQuery = true, value = "select ds_id,dis_id,ds_shipment,ds_color,dc_id,ds_scanfound, ds_order from Declaration_scan where dis_id = ?1 and DS_SCANFOUND=0" +
                                        " order by DS_ORDER desc")
    List<Declaration_scan> findByDis_idandScanSuccess(Long dis_id);
}
