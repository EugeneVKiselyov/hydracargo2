package ua.com.idltd.hydracargo.sheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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

    private final RestTemplate restTemplate = new RestTemplate();

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
//            String bearer="f9027fbb-cf33-3e11-84bb-5484491e2c94";
//            String apiSupportCounterPartyAdminToken="ba5378df-985e-49c5-9cf3-d222fa60aa68";

//                String bearer="5a556cf9-42e6-3165-962e-62feccef6a49";
//                String apiSupportCounterPartyAdminToken="40493123-d94a-42cf-9db0-a509720ca2ce";

                String bearer="9ac5b338-828c-3347-871a-0f9f447ffd05";
                String apiSupportCounterPartyAdminToken="15662cda-5d90-4221-9e47-6a84baf36d31";

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + bearer);
                headers.setContentType(MediaType.APPLICATION_JSON);

                String queryAddress=String.format("https://ukrposhta.ua/ecom/0.0.1/addresses");
                String queryClient=String.format("https://ukrposhta.ua/ecom/0.0.1/clients?token=%s",apiSupportCounterPartyAdminToken);
                String queryShipment=String.format("https://ukrposhta.ua/ecom/0.0.1/shipments?token=%s",apiSupportCounterPartyAdminToken);

                //создаем адреса
                List<Ukrpost_transfer> ukrpost_transferAddressList = ukrpost_transerRepository.getForAddressProcessing();
                for (Ukrpost_transfer ut : ukrpost_transferAddressList) {

                    try {
                        if (ut.ut_sadderssid==null) {
                            //добавляем адрес отправителя

                            //формируем запрос на создание адреса отправителя Ukrpost
                            UPInternationalAddress uPInternationalAddress = new UPInternationalAddress();
                            uPInternationalAddress.setCountry(ut.ut_scountry);
                            uPInternationalAddress.setCity(ut.ut_scity);
                            uPInternationalAddress.setRegion(ut.ut_sregion);
                            uPInternationalAddress.setForeignStreetHouseApartment(ut.ut_sforeignstreethouseapartment);
                            uPInternationalAddress.setPostcode(ut.ut_spostcode);

                            ResponseEntity<UPInternationalAddressResponse> response = restTemplate.exchange(queryAddress, HttpMethod.POST, new HttpEntity<>(uPInternationalAddress, headers), UPInternationalAddressResponse.class);
                            UPInternationalAddressResponse upInternationalAddressResponse = response.getBody();

                            //записываем id укрпочты адреса отправителя
                            ut.ut_sadderssid = upInternationalAddressResponse.getId();
                            ut.ut_saddressresponse = new JSONObject(upInternationalAddressResponse).toString();
                            ukrpost_transerRepository.save(ut);
                        }
                        if (ut.ut_radderssid==null) {
                            //добавляем адрес получателя

                            //формируем запрос на создание адреса отправителя Ukrpost
                            UPAddress uPAddress = new UPAddress();
                            uPAddress.setCountry(ut.ut_rcountry);
                            uPAddress.setCity(ut.ut_rcity);
                            uPAddress.setRegion(ut.ut_rregion);
                            uPAddress.setDistrict(ut.ut_rdistrict);
                            uPAddress.setStreet(ut.ut_rstreet);
                            uPAddress.setHouseNumber(ut.ut_rhousenumber);
                            uPAddress.setApartmentNumber(ut.ut_rapartmentnumber);
                            uPAddress.setPostcode(ut.ut_rpostcode);

                            ResponseEntity<UPInternationalAddressResponse> response = restTemplate.exchange(queryAddress, HttpMethod.POST, new HttpEntity<>(uPAddress, headers), UPInternationalAddressResponse.class);
                            UPInternationalAddressResponse upAddress = response.getBody();

                            //записываем id укрпочты адреса получателя
                            ut.ut_radderssid = upAddress.getId();
                            ut.ut_raddressresponse = new JSONObject(upAddress).toString();
                            ukrpost_transerRepository.save(ut);
                        }
                        if (ut.ut_suuid==null) {
                            //добавляем отправителя

                            //формируем запрос на создание адреса отправителя Ukrpost
    //                        UPSenderClient upSenderClient = new UPSenderClient();
    //                        upSenderClient.setAddressId(ut.ut_sadderssid);
    //                        upSenderClient.setName(ut.ut_sname);
    //                        upSenderClient.setPhoneNumber(ut.ut_sphonenumber);
    //                        upSenderClient.setEmail(ut.ut_semail);
    //                        upSenderClient.setEdrpou(ut.ut_sedrpou);
                            UPInternationalSenderClient upInternationalSenderClient=new UPInternationalSenderClient();
                            upInternationalSenderClient.setLatinName(ut.ut_sname);
                            upInternationalSenderClient.setEmail(ut.ut_semail);
                            upInternationalSenderClient.setType("COMPANY");
                            upInternationalSenderClient.setPhoneNumber(ut.ut_sphonenumber);
                            Addresses addresses=new Addresses();
                            addresses.setAddressId(ut.ut_sadderssid);
                            addresses.setMain(true);
                            List addressesList=new ArrayList();
                            addressesList.add(addresses);
                            upInternationalSenderClient.setAddresses(addressesList);

                            ResponseEntity<UPClientResponse> response = restTemplate.exchange(queryClient, HttpMethod.POST, new HttpEntity<>(upInternationalSenderClient, headers), UPClientResponse.class);
                            UPClientResponse upSenderClientResponse = response.getBody();

                            //записываем id укрпочты адреса получателя
                            ut.ut_suuid =upSenderClientResponse.getUuid();
                            ut.ut_sclientresponse = new JSONObject(upSenderClientResponse).toString();
                            ukrpost_transerRepository.save(ut);
                        }
                        if (ut.ut_ruuid==null) {
                            //добавляем получателя

                            UPReciverClient upReciverClient=new UPReciverClient();
                            upReciverClient.setFirstName(ut.ut_rfirstname);
                            upReciverClient.setMiddleName(ut.ut_rmiddlename);
                            upReciverClient.setLastName(ut.ut_rlastname);
                            upReciverClient.setPhoneNumber(ut.ut_rphonenumber);
                            upReciverClient.setType(ut.ut_rtype);

                            Addresses addresses=new Addresses();
                            addresses.setAddressId(ut.ut_radderssid);
                            addresses.setMain(true);
                            List addressesList=new ArrayList();
                            addressesList.add(addresses);
                            upReciverClient.setAddresses(addressesList);

                            ResponseEntity<UPClientResponse> response = restTemplate.exchange(queryClient, HttpMethod.POST, new HttpEntity<>(upReciverClient, headers), UPClientResponse.class);
                            UPClientResponse upReciverClientResponse = response.getBody();

                            //записываем id укрпочты адреса получателя
                            ut.ut_ruuid =upReciverClientResponse.getUuid();
                            ut.ut_rclientresponse = new JSONObject(upReciverClientResponse).toString();
                            ukrpost_transerRepository.save(ut);
                        }
    //                    if (ut.ut_puuid==null) {
    //                        //добавляем посылку
    //
    //                        UPShipment upShipment=new UPShipment();
    //                        upShipment.setType(ut.ut_ptype);
    //                        upShipment.setSender(new UPUuid(ut.ut_suuid));
    //                        upShipment.setRecipient(new UPUuid(ut.ut_ruuid));
    //                        upShipment.setDeliveryType(ut.ut_pdeliverytype);
    //                        upShipment.setPaidByRecipient(ut.ut_paidbyrecipient.equals(1));
    //                        upShipment.setDescription(ut.ut_description);
    //
    //                        UPParcel upParcel=new UPParcel();
    //                            upParcel.setWeight(ut.ut_weight);
    //                            upParcel.setLength(ut.ut_length);
    //                            upParcel.setWidth(ut.ut_width);
    //                            upParcel.setHeight(ut.ut_height);
    //                            upParcel.setDeclaredPrice(ut.ut_declaredprice);
    //                        List parcels=new ArrayList();
    //                            parcels.add(upParcel);
    //                        upShipment.setParcels(parcels);
    //
    //                        ResponseEntity<UPShipmentResponse> response = restTemplate.exchange(queryShipment, HttpMethod.POST, new HttpEntity<>(upShipment, headers), UPShipmentResponse.class);
    //                        UPShipmentResponse upShipmentResponse = response.getBody();
    //
    //                        //записываем id укрпочты посылки
    //                        ut.ut_puuid =upShipmentResponse.getUuid();
    //                        ut.ut_shipmentresponse = new JSONObject(upShipmentResponse).toString();
    //                        ukrpost_transerRepository.save(ut);
    //                    }
                        //меняем статус на адреса созданны
                        ut.ut_status = 0L;
                        ukrpost_transerRepository.save(ut);

                    } catch (Exception e) {
                        e.printStackTrace();
                        ut.ut_status = -1L;
                        ut.ut_error = e.getMessage();
                        ukrpost_transerRepository.save(ut);
                    }
                }


                //создаем посылки
                List<Ukrpost_transfer> ukrpost_transferParcelsList = ukrpost_transerRepository.getForParcelsProcessing();
                if (ukrpost_transferParcelsList.size()!=0) {
                    List<UPShipment> all_parcels = new ArrayList<>();
                    for (Ukrpost_transfer ut : ukrpost_transferParcelsList) {
                        if (ut.ut_status.equals(0L)) {
                            //добавляем посылку

                            UPShipment upShipment = new UPShipment();
                            upShipment.setType(ut.ut_ptype);
                            upShipment.setSender(new UPUuid(ut.ut_suuid));
                            upShipment.setRecipient(new UPUuid(ut.ut_ruuid));
                            upShipment.setDeliveryType(ut.ut_pdeliverytype);
                            upShipment.setPaidByRecipient(ut.ut_paidbyrecipient.equals(1));
                            upShipment.setDescription(ut.ut_description);
                            upShipment.setOnFailReceiveType("PROCESS_AS_REFUSAL");

                            UPParcel upParcel = new UPParcel();
                            upParcel.setWeight(ut.ut_weight);
                            upParcel.setLength(ut.ut_length);
                            upParcel.setWidth(ut.ut_width);
                            upParcel.setHeight(ut.ut_height);
//                            upParcel.setDeclaredPrice(ut.ut_declaredprice);
                            upParcel.setDeclaredPrice(Double.valueOf(300));

                            List parcels = new ArrayList();
                            parcels.add(upParcel);
                            upShipment.setParcels(parcels);

                            all_parcels.add(upShipment);

                        }
                    }

                    ResponseEntity<UPShipmentResponse[]> response = restTemplate.exchange(queryShipment, HttpMethod.POST, new HttpEntity<>(all_parcels, headers), UPShipmentResponse[].class);
                    UPShipmentResponse[] upShipmentResponse = response.getBody();

                    //записываем разультаты
                    for (UPShipmentResponse usr : upShipmentResponse) {
                        //записываем id укрпочты посылки
                        Ukrpost_transfer ut = ukrpost_transerRepository.getParcelForResponse(usr.getSender().getUuid(), usr.getRecipient().getUuid());
                        ut.ut_puuid = usr.getUuid();
                        ut.ut_shipmentresponse = new JSONObject(usr).toString();
                        ut.ut_status = 3L;
                        ukrpost_transerRepository.save(ut);
                    }
                }

                running = false;
            }
        }
    }
}
