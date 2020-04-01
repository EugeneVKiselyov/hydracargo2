package ua.com.idltd.hydracargo.zammler.z1c.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ДанныеДтКт", namespace="ExchangeCRM")
@XmlAccessorType(XmlAccessType.FIELD)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class DebCred {

    @XmlElement(name = "Дата", namespace="ExchangeCRM")
    private String date;

}
