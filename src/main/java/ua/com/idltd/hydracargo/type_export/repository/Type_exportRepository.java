package ua.com.idltd.hydracargo.type_export.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.type_export.entity.Type_export;

import java.util.List;

@Repository
public interface Type_exportRepository extends CrudRepository<Type_export, Long> {

    @Query(nativeQuery = true, value = "select * from Type_export where type_id=?1 order by type_id")
    List<Type_export> queryByType_id(String type_id);
}
