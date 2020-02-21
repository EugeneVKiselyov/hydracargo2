package ua.com.idltd.hydracargo.request.boxcontent.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.request.boxcontent.entity.Boxcontent;

@Repository
public interface BoxcontentRepository extends CrudRepository<Boxcontent, Long> {

    @Query(nativeQuery = true, value = "select * from BoxContent where box_id=:box_id")
    Iterable<Boxcontent> findByBOX_ID(@Param("box_id") final Long box_id);

}
