package ua.com.idltd.hydracargo.ukrpost.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.justin.entity.Justin_Trace;
import ua.com.idltd.hydracargo.ukrpost.entity.Ukrpost_transfer;

import java.util.List;


@Repository
public interface Ukrpost_transerRepository extends CrudRepository<Ukrpost_transfer, Long> {

    @Query(nativeQuery = true, value = "select * from UKRPOST_TRANSFER where UT_STATUS is null")
    List<Ukrpost_transfer> getForAddressProcessing();

    @Query(nativeQuery = true, value = "select * from UKRPOST_TRANSFER where UT_STATUS=0")
    List<Ukrpost_transfer> getForParcelsProcessing();

    @Query(nativeQuery = true, value = "select * from UKRPOST_TRANSFER where ut_suuid=?1 and ut_ruuid=?2")
    Ukrpost_transfer getParcelForResponse(String suuid, String ruuid);

}
