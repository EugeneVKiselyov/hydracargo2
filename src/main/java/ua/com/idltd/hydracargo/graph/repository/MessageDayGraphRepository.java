package ua.com.idltd.hydracargo.graph.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.graph.entity.MessageDayGraph;


import java.util.List;

@Repository
public interface MessageDayGraphRepository extends CrudRepository<MessageDayGraph, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM TABLE(PKG_GRAPH_VIEW.vMessageDayGraph(?1,?2,?3))")
    List<MessageDayGraph> queryMessageDayGraphByID(Long user_id, String start_date, String end_date);
}