package ua.com.idltd.hydracargo.fileloadlogs.filehandler_buffer.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.fileloadlogs.filehandler_buffer.entity.VFileHandlerBuffer;

import java.util.List;

@Repository
public interface VFileHandlerBufferRepository extends CrudRepository<VFileHandlerBuffer, Long> {
    @Query(nativeQuery = true, value = "select fhlb_id, fhlb_name, fhl_id, fhlb_date, fhlb_user from FILEHANDLER_BUFFER where fhl_id = PKG_RIGHT.GETFHL_ID( fhl_id, :username ) and fhl_id=:fhl_id")
    List<VFileHandlerBuffer> getByFhl_id(@Param("username") String usename, @Param("fhl_id") Long fhl_id);
    @Query(nativeQuery = true, value = "select fhlb_id, fhlb_name, fhl_id, fhlb_date, fhlb_user from FILEHANDLER_BUFFER where fhl_id = PKG_RIGHT.GETFHL_ID( fhl_id, :username )")
    List<VFileHandlerBuffer> getAllByUser(@Param("username") String usename);
}
