package ua.com.idltd.hydracargo.utils.filehandler.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.utils.filehandler.entity.FilehandlerBuffer;

import java.util.List;

@Repository
public interface FileHandlerBufferRepository extends CrudRepository<FilehandlerBuffer, Long> {
    @Query(nativeQuery = true, value = "select * from FILEHANDLER_BUFFER where fhl_id = PKG_RIGHT.GETFHL_ID( fhl_id, :username )")
    List<FilehandlerBuffer> getAllByUser(@Param("username") String usename);
    @Query(nativeQuery = true, value = "select * from FILEHANDLER_BUFFER where fhl_id = PKG_RIGHT.GETFHL_ID( fhl_id, :username ) and fhl_id=:fhl_id")
    List<FilehandlerBuffer> getByFhl_idandUser(@Param("username") String usename, @Param("fhl_id") Long fhl_id);
}
