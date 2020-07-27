package ua.com.idltd.hydracargo.ukrpost;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ua.com.idltd.hydracargo.ukrpost.dto.*;
import ua.com.idltd.hydracargo.ukrpost.entity.Ukrpost_transfer;
import ua.com.idltd.hydracargo.ukrpost.repository.Ukrpost_transerRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UkrpostProccessing {

    private UkrpostProperyInterface property;
    private String queryAddress;
    private String queryClient;
    private String queryShipment;
    private String queryShipmentLabel;

    private static HttpHeaders headers = new HttpHeaders();

    private final Ukrpost_transerRepository ukrpost_transerRepository;
    private final RestTemplate restTemplate;

    public UkrpostProccessing(UkrpostProperyInterface property, Ukrpost_transerRepository ukrpost_transerRepository) {
        this.property=property;
        this.ukrpost_transerRepository = ukrpost_transerRepository;
        this.restTemplate = new RestTemplate();

        headers.set("Authorization", "Bearer " + property.getBEARER());
        headers.setContentType(MediaType.APPLICATION_JSON);

        queryAddress=String.format("https://ukrposhta.ua/ecom/0.0.1/addresses");
        queryClient=String.format("https://ukrposhta.ua/ecom/0.0.1/clients?token=%s",property.getAPISUPPORTCOUNTERPARTYADMINTOKEN());
        queryShipment=String.format("https://ukrposhta.ua/ecom/0.0.1/shipments?token=%s",property.getAPISUPPORTCOUNTERPARTYADMINTOKEN());
        queryShipmentLabel="https://ukrposhta.ua/ecom/0.0.1/shipments/%s/sticker?token="+property.getAPISUPPORTCOUNTERPARTYADMINTOKEN();

    }

    public UPInternationalAddressResponse addSenderAddress(Ukrpost_transfer ut){
        //записываем или ищем адрес отправителя, отправляем от одного отправителя, возвращаем id адреса укрпочты
        UPAddress upAddress = new UPAddress();
            upAddress.setCountry(property.getCOUNTRY());
            upAddress.setCity(property.getSITY());
            upAddress.setRegion(property.getREGION());
            upAddress.setStreet(property.getSTREET());
            upAddress.setHouseNumber(property.getHOUSENUMBER());
            upAddress.setPostcode(IpexProperty.POSTCODE);

        UPInternationalAddressResponse upInternationalAddressResponse = null;
        try {

            ut.ut_saddress= new JSONObject(upAddress).toString();

            ResponseEntity<UPInternationalAddressResponse> response = restTemplate.exchange(queryAddress, HttpMethod.POST, new HttpEntity<>(upAddress, headers), UPInternationalAddressResponse.class);
            upInternationalAddressResponse = response.getBody();

            ut.ut_sadderssid = upInternationalAddressResponse.getId();
            ut.ut_saddressresponse = new JSONObject(upInternationalAddressResponse).toString();

        } catch (Exception e) {
            e.printStackTrace();
            ut.ut_status = -1L;
            ut.ut_error = e.getMessage();
        }

        return upInternationalAddressResponse;
    }

    public UPInternationalAddressResponse addRecipientAddress(Ukrpost_transfer ut){
        //добавляем адрес получателя
        //формируем запрос на создание адреса получателя Ukrpost
        UPAddress upAddress = new UPAddress();
            upAddress.setCountry(ut.ut_rcountry);
            upAddress.setCity(ut.ut_rcity);
            upAddress.setRegion(ut.ut_rregion);
            upAddress.setDistrict(ut.ut_rdistrict);
            upAddress.setStreet(ut.ut_rstreet);
            upAddress.setHouseNumber(ut.ut_rhousenumber);
            upAddress.setApartmentNumber(ut.ut_rapartmentnumber);
            upAddress.setPostcode(ut.ut_rpostcode);

        UPInternationalAddressResponse upInternationalAddressResponse = null;
        try {

            ut.ut_raddress= new JSONObject(upAddress).toString();

            ResponseEntity<UPInternationalAddressResponse> response = restTemplate.exchange(queryAddress, HttpMethod.POST, new HttpEntity<>(upAddress, headers), UPInternationalAddressResponse.class);
            upInternationalAddressResponse = response.getBody();

            //записываем id укрпочты адреса получателя
            ut.ut_radderssid = upInternationalAddressResponse.getId();
            ut.ut_raddressresponse = new JSONObject(upInternationalAddressResponse).toString();

        } catch (Exception e) {
            e.printStackTrace();
            ut.ut_status = -1L;
            ut.ut_error = e.getMessage();
        }

        return upInternationalAddressResponse;
    }

    public UPClientResponse addSender(Ukrpost_transfer ut){
        //добавляем отправителя
        //формируем запрос на создание отправителя Ukrpost
        UPSenderClient upSenderClient = new UPSenderClient();
//                            upSenderClient.setAddressId(ut.ut_sadderssid);
            upSenderClient.setName(ut.ut_sname);
            upSenderClient.setPhoneNumber(ut.ut_sphonenumber);
            upSenderClient.setEmail(ut.ut_semail);
            upSenderClient.setEdrpou(ut.ut_sedrpou);
            upSenderClient.setType(ut.ut_stype);

        Addresses addresses=new Addresses();
            addresses.setAddressId(ut.ut_sadderssid);
            addresses.setMain(true);

        List addressesList=new ArrayList();
            addressesList.add(addresses);
        upSenderClient.setAddresses(addressesList);

        UPClientResponse upSenderClientResponse = null;
        try{
            ut.ut_sclient=new JSONObject(upSenderClient).toString();

            ResponseEntity<UPClientResponse> response = restTemplate.exchange(queryClient, HttpMethod.POST, new HttpEntity<>(upSenderClient, headers), UPClientResponse.class);
            upSenderClientResponse = response.getBody();

            ut.ut_suuid =upSenderClientResponse.getUuid();
            ut.ut_sclientresponse = new JSONObject(upSenderClientResponse).toString();

        } catch (Exception e) {
            e.printStackTrace();
            ut.ut_status = -1L;
            ut.ut_error = e.getMessage();
        }

        return upSenderClientResponse;
    }

    public UPClientResponse addReciver(Ukrpost_transfer ut){
        //добавляем получателя
        //формируем запрос на создание получателя Ukrpost

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

        UPClientResponse upReciverClientResponse = null;
        try{
            ut.ut_rclient=new JSONObject(upReciverClient).toString();

            ResponseEntity<UPClientResponse> response = restTemplate.exchange(queryClient, HttpMethod.POST, new HttpEntity<>(upReciverClient, headers), UPClientResponse.class);
            upReciverClientResponse = response.getBody();

            ut.ut_ruuid =upReciverClientResponse.getUuid();
            ut.ut_rclientresponse = new JSONObject(upReciverClientResponse).toString();

        } catch (Exception e) {
            e.printStackTrace();
            ut.ut_status = -1L;
            ut.ut_error = e.getMessage();
        }

        return upReciverClientResponse;
    }

    public UPShipmentResponse[] addParcel(Ukrpost_transfer ut){
        //добавляем посылку
        UPShipmentResponse[] upShipmentResponse = new UPShipmentResponse[0];
        if (ut.ut_status.equals(0L)) {
            List<UPShipment> all_parcels = new ArrayList<>();
            UPShipment upShipment = new UPShipment();
                upShipment.setType(ut.ut_ptype);
                upShipment.setSender(new UPUuid(ut.ut_suuid));
                upShipment.setRecipient(new UPUuid(ut.ut_ruuid));
                upShipment.setDeliveryType(ut.ut_pdeliverytype);
                upShipment.setPaidByRecipient(ut.ut_paidbyrecipient.equals(1));
                upShipment.setDescription(ut.ut_description);
                upShipment.setOnFailReceiveType(ut.ut_onfailreceivetype);

            UPParcel upParcel = new UPParcel();
                upParcel.setWeight(ut.ut_weight);
                upParcel.setLength(ut.ut_length);
                upParcel.setWidth(ut.ut_width);
                upParcel.setHeight(ut.ut_height);
                upParcel.setDeclaredPrice(ut.ut_declaredprice);
//                upParcel.setDeclaredPrice(Double.valueOf(300));

            List parcels = new ArrayList();
                parcels.add(upParcel);

            upShipment.setParcels(parcels);
            all_parcels.add(upShipment);

            try {
                ut.ut_shipment=new JSONArray(all_parcels).toString();

                ResponseEntity<UPShipmentResponse[]> response = restTemplate.exchange(queryShipment, HttpMethod.POST, new HttpEntity<>(all_parcels, headers), UPShipmentResponse[].class);
                upShipmentResponse = response.getBody();

                ut.ut_puuid = upShipmentResponse[0].getUuid();
                ut.ut_shipmentresponse = new JSONArray(upShipmentResponse).toString();
                ut.ut_status = 3L;

            } catch (Exception e) {
                e.printStackTrace();
                ut.ut_status = -1L;
                ut.ut_error = e.getMessage();
            }
        }

        return upShipmentResponse;
    }

    public byte[] addLabel(Ukrpost_transfer ut){
        //добавляем этикетки на посылки
        String queryLabel=String.format(queryShipmentLabel,ut.ut_puuid);
        ResponseEntity<byte[]> response = restTemplate.exchange(queryLabel, HttpMethod.GET, new HttpEntity<>(null, headers), byte[].class);
        byte[] labelResponse = response.getBody();

        try {
            ut.ut_label = new javax.sql.rowset.serial.SerialBlob(labelResponse);
            ut.ut_status = 4L;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return labelResponse;
    }
}
