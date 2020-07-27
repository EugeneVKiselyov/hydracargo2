package ua.com.idltd.hydracargo.ukrpost;

public class IpexProperty implements UkrpostProperyInterface {

    public static String BEARER="9ac5b338-828c-3347-871a-0f9f447ffd05";
    public static String APISUPPORTCOUNTERPARTYADMINTOKEN="15662cda-5d90-4221-9e47-6a84baf36d31";

    public static String COUNTRY = "UA";
    public static String SITY = "Киів";
    public static String REGION = "";
    public static String STREET = "вул. Євгенія Харченка";
    public static String HOUSENUMBER = "55-А";
    public static String POSTCODE = "02088";

    @Override
    public String getBEARER() {
        return BEARER;
    }

    @Override
    public String getAPISUPPORTCOUNTERPARTYADMINTOKEN() {
        return APISUPPORTCOUNTERPARTYADMINTOKEN;
    }

    @Override
    public String getCOUNTRY() {
        return COUNTRY;
    }

    @Override
    public String getSITY() {
        return SITY;
    }

    @Override
    public String getREGION() {
        return REGION;
    }

    @Override
    public String getSTREET() {
        return STREET;
    }

    @Override
    public String getHOUSENUMBER() {
        return HOUSENUMBER;
    }

    @Override
    public String POSTCODE() {
        return POSTCODE;
    }
}
