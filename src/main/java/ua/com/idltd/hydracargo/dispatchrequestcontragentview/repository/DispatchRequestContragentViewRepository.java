package ua.com.idltd.hydracargo.dispatchrequestcontragentview.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.dispatchrequestcontragentview.entity.DispatchRequestContragentView;
import ua.com.idltd.hydracargo.repository.CustomRepository;

import java.util.List;

@Repository
public interface DispatchRequestContragentViewRepository extends CustomRepository<DispatchRequestContragentView, Long> {

    @Query(nativeQuery = true, value = "SELECT  " +
            "d.DIS_ID, d.DIS_NUM, d.REQ_ID,  " +
            "   r.REQ_SEATSNUM_P, r.REQ_WEIGHT_P, " +
            "   d.DIS_SEATSNUM, d.DIS_AGENT_ARRIVAL_DATE, d.DIS_AIRPORT_ARRIVAL_DATE,  " +
            "   d.DIS_TRANSIT_AIRPORT_DATE, d.DIS_DEPARTURE_AIRPORT_DATE_F, d.DIS_ARRIVAL_DATE_KIEV_F,  " +
            "   d.DIS_WEIGHT_F, d.DIS_VOLUME_WEIGHT_F, d.DIS_CSS_DATE,  " +
            "   d.ROUTE_ID, d.DIS_ARRIVAL_DATE_KIEV_P, d.AWB_ID, a.AWB_NUM, " +
            "   d.EP_ID, d.DST_ID, d.RS_ID, " +
            "   r.REQ_NUM, r.CNT_ID, c.CNT_CODE, c.CNT_NAME, " +
            "   e.EP_CODE, b.bs_id, b.bs_name, d.dis_documentdate, " +
            "   (select count(*) from DECLARATION_CACHE dc where dc.req_id=r.req_id and dc.dis_id=d.dis_id) countshipment " +
            "FROM DISPATCH d, request r, contragent c, entrepot e, awb a, BUSINESS b " +
            "where r.req_id=d.req_id " +
            "and c.CNT_ID = r.CNT_ID " +
            "and e.EP_ID = d.EP_ID " +
            "and r.bs_id = b.bs_id "+
            "and a.AWB_ID (+) = d.AWB_ID "+
            "and c.cnt_id = PKG_RIGHT.GETCNT_ID( c.cnt_id, :username ) "+
            "and d.rs_id in :rs_ids order by d.dis_id desc")
    List<DispatchRequestContragentView> queryByRs_idList(@Param("rs_ids")List<Long> rs_ids, @Param("username") String usename);

    @Query(nativeQuery = true, value = "SELECT  " +
            "d.DIS_ID, d.DIS_NUM, d.REQ_ID,  " +
            "   r.REQ_SEATSNUM_P, r.REQ_WEIGHT_P, " +
            "   d.DIS_SEATSNUM, d.DIS_AGENT_ARRIVAL_DATE, d.DIS_AIRPORT_ARRIVAL_DATE,  " +
            "   d.DIS_TRANSIT_AIRPORT_DATE, d.DIS_DEPARTURE_AIRPORT_DATE_F, d.DIS_ARRIVAL_DATE_KIEV_F,  " +
            "   d.DIS_WEIGHT_F, d.DIS_VOLUME_WEIGHT_F, d.DIS_CSS_DATE,  " +
            "   d.ROUTE_ID, d.DIS_ARRIVAL_DATE_KIEV_P, d.AWB_ID, a.AWB_NUM, " +
            "   d.EP_ID, d.DST_ID, d.RS_ID, " +
            "   r.REQ_NUM, r.CNT_ID, c.CNT_CODE, c.CNT_NAME, " +
            "   e.EP_CODE, b.bs_id, b.bs_name, d.dis_documentdate, " +
            "   (select count(*) from DECLARATION_CACHE dc where dc.req_id=r.req_id and dc.dis_id=d.dis_id) countshipment " +
            "FROM DISPATCH d, request r, contragent c, entrepot e, awb a, BUSINESS b   " +
            "where r.req_id=d.req_id " +
            "and c.cnt_id = PKG_RIGHT.GETCNT_ID( c.cnt_id, :username ) "+
            "and c.CNT_ID = r.CNT_ID " +
            "and e.EP_ID = d.EP_ID " +
            "and r.bs_id = b.bs_id "+
            "and a.AWB_ID (+) = d.AWB_ID "+
            "and d.dis_id = :dis_id " +
            "and d.req_id = :req_id ")
    DispatchRequestContragentView queryByDis_idandReq_id(@Param("dis_id")Long dis_id,@Param("req_id")Long req_id, @Param("username") String usename);

}
