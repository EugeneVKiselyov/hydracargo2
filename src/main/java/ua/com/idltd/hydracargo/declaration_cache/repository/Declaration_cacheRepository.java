package ua.com.idltd.hydracargo.declaration_cache.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.declaration_cache.entity.Declaration_cache;
import ua.com.idltd.hydracargo.repository.CustomRepository;

import java.util.List;

@Repository
public interface Declaration_cacheRepository extends CustomRepository<Declaration_cache, Long> {

    @Query(nativeQuery = true, value = "select * from (select * from Declaration_cache where dc_shipment LIKE ?1 and dc_user_name = ?2 order by dc_id desc) where rownum<50")
    List<Declaration_cache> queryByShipmentLike(String dc_shipment, String dc_user_name);

    @Query(nativeQuery = true, value = "select * from Declaration_cache where dc_shipment= ?1 and dc_user_name = ?2 order by dc_id desc")
    Declaration_cache queryByShipment(String dc_shipment, String dc_user_name);

    @Query(nativeQuery = true, value = "select * from Declaration_cache where dc_id = ?1 and dc_user_name = ?2 order by dc_id desc")
    List<Declaration_cache> queryById(Long dc_id, String dc_user_name);

    @Query(nativeQuery = true, value = "select * from (select * from Declaration_cache where dc_shipment LIKE ?1 order by dc_id desc) where rownum<50 ")
    List<Declaration_cache> queryByShipmentLikeAll(String dc_shipment);

    @Query(nativeQuery = true, value = "select * from Declaration_cache where req_id = ?1 and dc_shipment like ?2 and dc_user_name = ?3 order by dc_id desc")
    List<Declaration_cache> queryByReq_idandShipmentLike(Long req_id, String dc_shipment, String dc_user_name);

    @Query(nativeQuery = true, value = "select * from Declaration_cache where req_id = ?1 and dc_shipment like ?2 order by dc_id desc")
    List<Declaration_cache> queryByReq_idandShipmentLikeAll(Long req_id, String dc_shipment);

    @Query(nativeQuery = true, value = "select * from Declaration_cache where dis_id = ?1 and dc_user_name = ?2 order by dc_id desc")
    List<Declaration_cache> queryByDis_idandUserName(Long dis_id, String dc_user_name);

    @Query(nativeQuery = true, value = "select * from Declaration_cache where dis_id = ?1 and dc_user_name = ?2 order by dc_id desc")
    List<Declaration_cache> queryByDis_id(Long dis_id, String dc_user_name);

    @Query(nativeQuery = true, value = "select * from Declaration_cache where dis_id = ?1 order by dc_id desc")
    List<Declaration_cache> queryByDis_idAll(Long dis_id);

    @Query(nativeQuery = true, value = "select * from Declaration_cache where dis_id = ?1 and dc_shipment like ?2 order by dc_id desc")
    List<Declaration_cache> queryByDis_idandShipmentLikeAll(Long dis_id, String dc_shipment);

    @Query(nativeQuery = true, value = "select * from Declaration_cache where dis_id = ?1 and dc_shipment like ?2 and dc_user_name = ?3 order by dc_id desc")
    List<Declaration_cache> queryByDis_idandShipmentLike(Long dis_id, String dc_shipment, String dc_user_name);

    @Query(nativeQuery = true, value = "select * from Declaration_cache where dis_id = ?1 and req_id = ?2 and dc_shipment like ?3 order by dc_id desc")
    List<Declaration_cache> queryByDis_idandReq_idandShipmentLikeAll(Long dis_id, Long req_id, String dc_shipment);

    @Query(nativeQuery = true, value = "select * from Declaration_cache where dis_id = ?1 and req_id = ?2 and dc_shipment like ?3 and dc_user_name = ?4 order by dc_id desc")
    List<Declaration_cache> queryByDis_idandReq_idandShipmentLike(Long dis_id, Long req_id, String dc_shipment, String dc_user_name);

    @Query(nativeQuery = true, value = "select count(dc_id) from Declaration_cache where dis_id=?1")
    Long countByDis_id(Long dis_id);

    @Query(nativeQuery = true, value = "select nvl(sum(dc_tweight),0) from Declaration_cache where dis_id=?1")
    Double sumDc_tweightByDis_id(Long dis_id);

    @Query(nativeQuery = true, value = "select * from Declaration_cache where dis_id = ?1 and dc_user_name = ?2")
    List<Declaration_cache> findByDis_id_scan(Long dis_id, String dc_user_name);

}
