package ua.com.idltd.hydracargo.utils.filehandler.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.utils.filehandler.entity.FilehandlerAtomLog;

import java.util.List;

@Repository
public interface FileHandlerAtomLogRepository extends CrudRepository<FilehandlerAtomLog, Long> {
    @Query(nativeQuery = true, value = "SELECT fhal_id, fhl_id, fhal_atom, fhal_error, fhal_status FROM FILEHANDLER_ATOM_LOG where fhl_id = PKG_RIGHT.GETFHL_ID( :fhl_id, :username ) and fhl_id=:fhl_id")
    List<FilehandlerAtomLog> getByFhl_id(@Param("username") String usename, @Param("fhl_id") Long fhl_id);
    @Query(nativeQuery = true, value = "SELECT fhal_id, fhl_id, fhal_atom, fhal_error, fhal_status FROM FILEHANDLER_ATOM_LOG where fhl_id = PKG_RIGHT.GETFHL_ID( :fhl_id, :username ) and fhl_id=:fhl_id and fhal_status=:fhal_status")
    List<FilehandlerAtomLog> getByFhl_idandFhal_status(@Param("username") String usename, @Param("fhl_id") Long fhl_id, @Param("fhal_status") String fhal_status);
}
