package ua.com.idltd.hydracargo.utils.filehandler.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.utils.filehandler.entity.LoadPacking;

@Repository
public interface LoadPackingRepository extends CrudRepository<LoadPacking, Long> {


}
