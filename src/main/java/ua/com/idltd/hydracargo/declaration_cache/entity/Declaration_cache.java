package ua.com.idltd.hydracargo.declaration_cache.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Declaration_cache")
public class Declaration_cache {

    @Id
    @SequenceGenerator( name = "DECLARATION_CACHE_SEQ", sequenceName = "DECLARATION_CACHE_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "DECLARATION_CACHE_SEQ")
    public Long dc_id;

    @Column(name = "dc_user_name", nullable = false, updatable = false)
    public String dc_user_name;

    @Column(name = "dc_createdate", nullable = false, updatable = false, columnDefinition = "DATETIME")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public Date dc_createdate;

    @Column(name = "dc_tracenum", nullable = false, updatable = false)
    public String dc_tracenum;


    @Column(name = "dc_shipment", nullable = false, updatable = false)
//    @Generated(GenerationTime.INSERT)
    public String dc_shipment;

    @Column(name = "dc_tweight", nullable = false, updatable = false, scale = 4, precision = 22)
    public Double dc_tweight;

    @Column(name = "dc_tvalue", nullable = false, updatable = false, scale = 2, precision = 22)
    public Double dc_tvalue;

    @Column(name = "dc_sname", nullable = false, updatable = false)
    public String dc_sname;

    @Column(name = "dc_scity", nullable = false, updatable = false)
    public String dc_scity;

    @Column(name = "dc_saddress", nullable = false, updatable = false)
    public String dc_saddress;

    @Column(name = "dc_szip", nullable = false, updatable = false)
    public String dc_szip;

    @Column(name = "dc_sphone", nullable = false, updatable = false)
    public String dc_sphone;


    @Column(name = "dc_rfname", nullable = false, updatable = false)
    public String dc_rfname;

    @Column(name = "dc_rlname", nullable = false, updatable = false)
    public String dc_rlname;

    @Column(name = "dc_rregion", nullable = false, updatable = false)
    public String dc_rregion;

//    @Column(name = "dc_rctype", nullable = false, updatable = false)
//    public String dc_rctype;

    @Column(name = "dc_rcity", nullable = false, updatable = false)
    public String dc_rcity;

//    @Column(name = "dc_rstype", nullable = false, updatable = false)
//    public String dc_rstype;

    @Column(name = "dc_rstreet", nullable = false, updatable = false)
    public String dc_rstreet;

    @Column(name = "dc_rbld", nullable = false, updatable = false)
    public String dc_rbld;

    @Column(name = "dc_rapt", nullable = false, updatable = false)
    public String dc_rapt;

    @Column(name = "dc_rzip", nullable = false, updatable = false)
    public String dc_rzip;

    @Column(name = "dc_box", nullable = false, updatable = false)
    public String dc_box;

    @Column(name = "dc_description", nullable = false, updatable = false)
    public String dc_description;

    @Column(name = "dc_qty", nullable = false, updatable = false)
    public String dc_qty;

    @Column(name = "dc_value", nullable = false, updatable = false)
    public String dc_value;

    @Column(name = "dc_rphone", nullable = false, updatable = false)
    public String dc_rphone;

    @Column(name = "dc_scountry", nullable = false, updatable = false)
    public String dc_scountry;

    @Column(name = "dc_rcountry", nullable = false, updatable = false)
    public String dc_rcountry;

    @Column(name = "dc_service_id", nullable = false, updatable = false)
    public String dc_service_id;

    @Column(name = "dc_type_id", nullable = false, updatable = false)
    public String dc_type_id;

    @Column(name = "DC_SHIPMENT_UKRPOST", nullable = false, updatable = false)
    public String dc_shipment_ukrpost;

    @Column(name = "DC_WIDTH", nullable = false)
    public Integer dc_width;

    @Column(name = "DC_LENGTH", nullable = false)
    public Integer dc_length;

    @Column(name = "DCLP_ID", nullable = false)
    public Long dclp_id;

    @Column(name = "REQ_ID", nullable = false)
    public Long req_id;

    @Column(name = "DIS_ID", nullable = false)
    public Long dis_id;

    @Column(name = "DC_CURRENCY")
    public String dc_currency;
}
