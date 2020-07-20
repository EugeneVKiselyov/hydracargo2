package ua.com.idltd.hydracargo.ukrpost;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class UkrpostProccessing {

    public static String bearer="9ac5b338-828c-3347-871a-0f9f447ffd05";
    public static String apiSupportCounterPartyAdminToken="15662cda-5d90-4221-9e47-6a84baf36d31";

    public static String queryAddress=String.format("https://ukrposhta.ua/ecom/0.0.1/addresses");
    public static String queryClient=String.format("https://ukrposhta.ua/ecom/0.0.1/clients?token=%s",apiSupportCounterPartyAdminToken);
    public static String queryShipment=String.format("https://ukrposhta.ua/ecom/0.0.1/shipments?token=%s",apiSupportCounterPartyAdminToken);

    private static HttpHeaders headers = new HttpHeaders();

    public UkrpostProccessing() {
        headers.set("Authorization", "Bearer " + bearer);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

}
