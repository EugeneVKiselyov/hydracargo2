package ua.com.idltd.hydracargo.request.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.request.entity.Request;
import ua.com.idltd.hydracargo.request.entity.VRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface VRequestRepository extends CrudRepository<VRequest, Long> {

    @Query(nativeQuery = true, value = "select" +
            " CNT_ID, CNT_NAME, REQ_ID, REQ_NUM, BS_ID, BS_NAME, RS_ID, RS_NAME, REQ_DATE, EP_ID, EP_CODE, EP_ID_DEST, EP_CODE_DEST," +
            " REQ_WEIGHT_P, REQ_WEIGHT_F, REQ_SEATSNUM_P, REQ_SEATSNUM_F," +
            " REQ_ARRIVAL_DATE_P, REQ_ARRIVAL_DATE_F, REQ_DEPARTURE_DATE, REQ_EP_DEST_DATE, REQ_CONTRAGENT_DATE, REQ_FEE, REQ_ADDEXPENSES " +
            " from VRequest r where r.cnt_id = PKG_RIGHT.GETCNT_ID( r.cnt_id, :username )")
    List<VRequest> getAllByUser(@Param("username") String usename);
    @Query(nativeQuery = true, value = "select" +
            " CNT_ID, CNT_NAME, REQ_ID, REQ_NUM, BS_ID, BS_NAME, RS_ID, RS_NAME, REQ_DATE, EP_ID, EP_CODE, EP_ID_DEST, EP_CODE_DEST," +
            " REQ_WEIGHT_P, REQ_WEIGHT_F, REQ_SEATSNUM_P, REQ_SEATSNUM_F," +
            " REQ_ARRIVAL_DATE_P, REQ_ARRIVAL_DATE_F, REQ_DEPARTURE_DATE, REQ_EP_DEST_DATE, REQ_CONTRAGENT_DATE, REQ_FEE, REQ_ADDEXPENSES " +
            " from VRequest r where r.req_id=:req_id and r.cnt_id = PKG_RIGHT.GETCNT_ID( r.cnt_id, :username )")
    List<VRequest> getByReq_idandUser(@Param("username") String usename, @Param("req_id") Long req_id);
    @Query(nativeQuery = true, value = "select" +
            " CNT_ID, CNT_NAME, REQ_ID, REQ_NUM, BS_ID, BS_NAME, RS_ID, RS_NAME, REQ_DATE, EP_ID, EP_CODE, EP_ID_DEST, EP_CODE_DEST," +
            " REQ_WEIGHT_P, REQ_WEIGHT_F, REQ_SEATSNUM_P, REQ_SEATSNUM_F," +
            " REQ_ARRIVAL_DATE_P, REQ_ARRIVAL_DATE_F, REQ_DEPARTURE_DATE, REQ_EP_DEST_DATE, REQ_CONTRAGENT_DATE, REQ_FEE, REQ_ADDEXPENSES " +
            " from VRequest r where cnt_id=:cnt_id and r.cnt_id = PKG_RIGHT.GETCNT_ID( r.cnt_id, :username )")
    List<VRequest> getByCnt_idandUser(@Param("username") String usename, @Param("cnt_id") Long cnt_id);
    @Query(nativeQuery = true, value = "select" +
            " CNT_ID, CNT_NAME, REQ_ID, REQ_NUM, BS_ID, BS_NAME, RS_ID, RS_NAME, REQ_DATE, EP_ID, EP_CODE, EP_ID_DEST, EP_CODE_DEST," +
            " REQ_WEIGHT_P, REQ_WEIGHT_F, REQ_SEATSNUM_P, REQ_SEATSNUM_F," +
            " REQ_ARRIVAL_DATE_P, REQ_ARRIVAL_DATE_F, REQ_DEPARTURE_DATE, REQ_EP_DEST_DATE, REQ_CONTRAGENT_DATE, REQ_FEE, REQ_ADDEXPENSES " +
            " from VRequest r where r.req_id=:req_id and cnt_id=:cnt_id and r.cnt_id = PKG_RIGHT.GETCNT_ID( r.cnt_id, :username )")
    List<VRequest> getByReq_idandCnt_idandUser(@Param("username") String usename, @Param("req_id") Long req_id, @Param("cnt_id") Long cnt_id);
    @Query(nativeQuery = true, value = "select" +
            " CNT_ID, CNT_NAME, REQ_ID, REQ_NUM, BS_ID, BS_NAME, RS_ID, RS_NAME, REQ_DATE, EP_ID, EP_CODE, EP_ID_DEST, EP_CODE_DEST," +
            " REQ_WEIGHT_P, REQ_WEIGHT_F, REQ_SEATSNUM_P, REQ_SEATSNUM_F," +
            " REQ_ARRIVAL_DATE_P, REQ_ARRIVAL_DATE_F, REQ_DEPARTURE_DATE, REQ_EP_DEST_DATE, REQ_CONTRAGENT_DATE, REQ_FEE, REQ_ADDEXPENSES " +
            " from VRequest r where ep_id=:ep_id and r.cnt_id = PKG_RIGHT.GETCNT_ID( r.cnt_id, :username )")
    List<VRequest> getByEp_idandUser(@Param("username") String usename, @Param("ep_id") Long ep_id);
    @Query(nativeQuery = true, value = "select" +
            " CNT_ID, CNT_NAME, REQ_ID, REQ_NUM, BS_ID, BS_NAME, RS_ID, RS_NAME, REQ_DATE, EP_ID, EP_CODE, EP_ID_DEST, EP_CODE_DEST," +
            " REQ_WEIGHT_P, REQ_WEIGHT_F, REQ_SEATSNUM_P, REQ_SEATSNUM_F," +
            " REQ_ARRIVAL_DATE_P, REQ_ARRIVAL_DATE_F, REQ_DEPARTURE_DATE, REQ_EP_DEST_DATE, REQ_CONTRAGENT_DATE, REQ_FEE, REQ_ADDEXPENSES " +
            " from VRequest r where r.req_id=:req_id and ep_id=:ep_id and r.cnt_id = PKG_RIGHT.GETCNT_ID( r.cnt_id, :username )")
    List<VRequest> getByReq_idandEp_idandUser(@Param("username") String usename, @Param("req_id") Long req_id, @Param("ep_id") Long ep_id);
    @Query(nativeQuery = true, value = "select" +
            " CNT_ID, CNT_NAME, REQ_ID, REQ_NUM, BS_ID, BS_NAME, RS_ID, RS_NAME, REQ_DATE, EP_ID, EP_CODE, EP_ID_DEST, EP_CODE_DEST," +
            " REQ_WEIGHT_P, REQ_WEIGHT_F, REQ_SEATSNUM_P, REQ_SEATSNUM_F," +
            " REQ_ARRIVAL_DATE_P, REQ_ARRIVAL_DATE_F, REQ_DEPARTURE_DATE, REQ_EP_DEST_DATE, REQ_CONTRAGENT_DATE, REQ_FEE, REQ_ADDEXPENSES " +
            " from VRequest r where cnt_id=:cnt_id and ep_id=:ep_id and r.cnt_id = PKG_RIGHT.GETCNT_ID( r.cnt_id, :username )")
    List<VRequest> getByCnt_idandEp_idandUser(@Param("username") String usename, @Param("cnt_id") Long cnt_id, @Param("ep_id") Long ep_id);
    @Query(nativeQuery = true, value = "select" +
            " CNT_ID, CNT_NAME, REQ_ID, REQ_NUM, BS_ID, BS_NAME, RS_ID, RS_NAME, REQ_DATE, EP_ID, EP_CODE, EP_ID_DEST, EP_CODE_DEST," +
            " REQ_WEIGHT_P, REQ_WEIGHT_F, REQ_SEATSNUM_P, REQ_SEATSNUM_F," +
            " REQ_ARRIVAL_DATE_P, REQ_ARRIVAL_DATE_F, REQ_DEPARTURE_DATE, REQ_EP_DEST_DATE, REQ_CONTRAGENT_DATE, REQ_FEE, REQ_ADDEXPENSES " +
            " from VRequest r where r.req_id=:req_id and cnt_id=:cnt_id and ep_id=:ep_id and r.cnt_id = PKG_RIGHT.GETCNT_ID( r.cnt_id, :username )")
    List<VRequest> getByReq_idandCnt_idandEp_idandUser(@Param("username") String usename, @Param("req_id") Long req_id,@Param("cnt_id") Long cnt_id, @Param("ep_id") Long ep_id);
}
