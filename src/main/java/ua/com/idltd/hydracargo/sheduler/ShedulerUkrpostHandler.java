package ua.com.idltd.hydracargo.sheduler;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.com.idltd.hydracargo.ukrpost.IpexProperty;
import ua.com.idltd.hydracargo.ukrpost.UkrpostProccessing;
import ua.com.idltd.hydracargo.ukrpost.dto.*;
import ua.com.idltd.hydracargo.ukrpost.entity.Ukrpost_transfer;
import ua.com.idltd.hydracargo.ukrpost.repository.Ukrpost_transerRepository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class ShedulerUkrpostHandler {

    @Value("${scheduler.ukrpost.enable}")
    private boolean enabled;

    private static boolean running;

    private final DataSource dataSource;
    private final Ukrpost_transerRepository ukrpost_transerRepository;

    @Autowired
    public ShedulerUkrpostHandler(@Qualifier("dataSource") DataSource dataSource, Ukrpost_transerRepository ukrpost_transerRepository) {
        this.dataSource = dataSource;
        this.ukrpost_transerRepository = ukrpost_transerRepository;
    }

    @Scheduled(fixedRateString = "${scheduler.ukrpost.fixedRate.in.milliseconds}")
    public void handleAll(){
        if (enabled) {
            if (!running) {
                running = true;
                UkrpostProccessing up= new UkrpostProccessing(new IpexProperty(), ukrpost_transerRepository);
                //создаем адреса
                List<Ukrpost_transfer> ukrpost_transferAddressList = ukrpost_transerRepository.getForAddressProcessing();
                for (Ukrpost_transfer ut : ukrpost_transferAddressList) {

                    try {
                        if (ut.ut_sadderssid==null) {
                            //добавляем адрес отправителя
                            UPInternationalAddressResponse upResponse =  up.addSenderAddress(ut);
                        }
                        if (ut.ut_radderssid==null) {
                            //добавляем адрес получателя
                            UPInternationalAddressResponse upResponse =  up.addRecipientAddress(ut);
                        }
                        if (ut.ut_suuid==null) {
                            //добавляем отправителя
                            UPClientResponse upSenderClientResponse = up.addSender(ut);
                        }
                        if (ut.ut_ruuid==null) {
                            //добавляем получателя
                            UPClientResponse upReciverClientResponse = up.addReciver(ut);
                        }

                        //если нет ошибок то меняем статус на адреса созданны
                        if (ut.ut_status == null) ut.ut_status= 0L;

                        if (ut.ut_status== 0L && ut.ut_puuid==null) {
                            //добавляем посылку
                            UPShipmentResponse[] upShipmentResponses = up.addParcel(ut);
                        }

                        if (ut.ut_status== 3L) {
                            //добавляем этикетку на посылку
                            byte[] LabelResponse = up.addLabel(ut);
                        }

                        ukrpost_transerRepository.save(ut);

                    } catch (Exception e) {
                        e.printStackTrace();
                        ut.ut_status = -1L;
                        ut.ut_error = e.getMessage();
                        ukrpost_transerRepository.save(ut);
                    }
                }

                //создаем посылки
//                List<Ukrpost_transfer> ukrpost_transferParcelsList = ukrpost_transerRepository.getForParcelsProcessing();
//                if (ukrpost_transferParcelsList.size()!=0) {
//                    List<UPShipment> all_parcels = new ArrayList<>();
//                    for (Ukrpost_transfer ut : ukrpost_transferParcelsList) {
//                        if (ut.ut_status.equals(0L)) {
//                            //добавляем посылку
//
//                            UPShipment upShipment = new UPShipment();
//                            upShipment.setType(ut.ut_ptype);
//                            upShipment.setSender(new UPUuid(ut.ut_suuid));
//                            upShipment.setRecipient(new UPUuid(ut.ut_ruuid));
//                            upShipment.setDeliveryType(ut.ut_pdeliverytype);
//                            upShipment.setPaidByRecipient(ut.ut_paidbyrecipient.equals(1));
//                            upShipment.setDescription(ut.ut_description);
//                            upShipment.setOnFailReceiveType("PROCESS_AS_REFUSAL");
//
//                            UPParcel upParcel = new UPParcel();
//                            upParcel.setWeight(ut.ut_weight);
//                            upParcel.setLength(ut.ut_length);
//                            upParcel.setWidth(ut.ut_width);
//                            upParcel.setHeight(ut.ut_height);
////                            upParcel.setDeclaredPrice(ut.ut_declaredprice);
//                            upParcel.setDeclaredPrice(Double.valueOf(300));
//
//                            List parcels = new ArrayList();
//                            parcels.add(upParcel);
//                            upShipment.setParcels(parcels);
//
//                            all_parcels.add(upShipment);
//
//                        }
//                    }
//
//                    UPShipmentResponse[] upShipmentResponse = up.addParcels(all_parcels);
//
//                    //записываем разультаты
//                    for (UPShipmentResponse usr : upShipmentResponse) {
//                        //записываем id укрпочты посылки
//                        Ukrpost_transfer ut = ukrpost_transerRepository.getParcelForResponse(usr.getSender().getUuid(), usr.getRecipient().getUuid());
//                        ut.ut_puuid = usr.getUuid();
//                        ut.ut_shipmentresponse = new JSONObject(usr).toString();
//                        ut.ut_status = 3L;
//                        ukrpost_transerRepository.save(ut);
//                    }
//                }

                running = false;
            }
        }
    }
}
