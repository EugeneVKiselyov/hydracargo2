package ua.com.idltd.hydracargo.utils.filehandler.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.utils.filehandler.entity.FilehandlerAtomLog;

@Repository
public interface FileHandlerAtomLogRepository extends CrudRepository<FilehandlerAtomLog, Long> {

}
