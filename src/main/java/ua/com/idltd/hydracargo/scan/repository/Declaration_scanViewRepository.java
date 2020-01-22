package ua.com.idltd.hydracargo.scan.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.scan.entity.Declaration_scan;
import ua.com.idltd.hydracargo.scan.entity.Declaration_scanView;

import java.util.List;

@Repository
public interface Declaration_scanViewRepository extends CrudRepository<Declaration_scanView, Long> {
    @Query(nativeQuery = true, value = "select ds_id,dis_id,ds_shipment,ds_color,dc_id,ds_scanfound,ds_scanfound_name,ds_order" +
                                       " from vDeclaration_scan where dis_id = ?1 and DS_SCANFOUND<>0 and DS_SCANFOUND is not null" +
                                       " order by DS_ORDER desc")
    List<Declaration_scanView> findByDis_idandScanWithError(Long dis_id);
}
