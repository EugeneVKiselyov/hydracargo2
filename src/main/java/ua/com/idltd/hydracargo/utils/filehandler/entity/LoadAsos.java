package ua.com.idltd.hydracargo.utils.filehandler.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "LOAD_ASOS")
public class LoadAsos {
    private long ltId;
    private long dis_id;
    private Long ltPart;
    private String hawb;
    private Date dateofawb;
    private String mawbcode;
    private Date dateoforder;
    private String orderno;
    private String eshopsite;
    private String recepientname;
    private String passportnumber;
    private Date passportdate;
    private String city;
    private String region;
    private String district;
    private String postalcode;
    private String recepientaddress;
    private String recepienttelephone;
    private String recepientemail;
    private Double priceperunit;
    private String currency;
    private Double unitweight;
    private Double fullweight;
    private String unitname;
    private String unitdescription;
    private Long itemquantity;
    private Long totalnumberofpieces;
    private String website;
    private Long unitidSku;
    private Date createDate;
    private String createUser;

    @Id
    @Column(name = "LT_ID")
    @SequenceGenerator(name = "LOAD_ASOS_SEQ", sequenceName = "LOAD_ASOS_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOAD_ASOS_SEQ")
    public long getLtId() {
        return ltId;
    }

    public void setLtId(long ltId) {
        this.ltId = ltId;
    }

    @Basic
    @Column(name = "DIS_ID")
    public long getDis_id() {
        return dis_id;
    }

    public void setDis_id(long dis_id) {
        this.dis_id = dis_id;
    }

    @Basic
    @Column(name = "LT_PART")
    public Long getLtPart() {
        return ltPart;
    }

    public void setLtPart(Long ltPart) {
        this.ltPart = ltPart;
    }

    @Basic
    @Column(name = "HAWB")
    public String getHawb() {
        return hawb;
    }

    public void setHawb(String hawb) {
        this.hawb = hawb;
    }

    @Basic
    @Column(name = "DATEOFAWB")
    public Date getDateofawb() {
        return dateofawb;
    }

    public void setDateofawb(Date dateofawb) {
        this.dateofawb = dateofawb;
    }

    @Basic
    @Column(name = "MAWBCODE")
    public String getMawbcode() {
        return mawbcode;
    }

    public void setMawbcode(String mawbcode) {
        this.mawbcode = mawbcode;
    }

    @Basic
    @Column(name = "DATEOFORDER")
    public Date getDateoforder() {
        return dateoforder;
    }

    public void setDateoforder(Date dateoforder) {
        this.dateoforder = dateoforder;
    }

    @Basic
    @Column(name = "ORDERNO")
    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    @Basic
    @Column(name = "ESHOPSITE")
    public String getEshopsite() {
        return eshopsite;
    }

    public void setEshopsite(String eshopsite) {
        this.eshopsite = eshopsite;
    }

    @Basic
    @Column(name = "RECEPIENTNAME")
    public String getRecepientname() {
        return recepientname;
    }

    public void setRecepientname(String recepientname) {
        this.recepientname = recepientname;
    }

    @Basic
    @Column(name = "PASSPORTNUMBER")
    public String getPassportnumber() {
        return passportnumber;
    }

    public void setPassportnumber(String passportnumber) {
        this.passportnumber = passportnumber;
    }

    @Basic
    @Column(name = "PASSPORTDATE")
    public Date getPassportdate() {
        return passportdate;
    }

    public void setPassportdate(Date passportdate) {
        this.passportdate = passportdate;
    }

    @Basic
    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "REGION")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Basic
    @Column(name = "DISTRICT")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Basic
    @Column(name = "POSTALCODE")
    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    @Basic
    @Column(name = "RECEPIENTADDRESS")
    public String getRecepientaddress() {
        return recepientaddress;
    }

    public void setRecepientaddress(String recepientaddress) {
        this.recepientaddress = recepientaddress;
    }

    @Basic
    @Column(name = "RECEPIENTTELEPHONE")
    public String getRecepienttelephone() {
        return recepienttelephone;
    }

    public void setRecepienttelephone(String recepienttelephone) {
        this.recepienttelephone = recepienttelephone;
    }

    @Basic
    @Column(name = "RECEPIENTEMAIL")
    public String getRecepientemail() {
        return recepientemail;
    }

    public void setRecepientemail(String recepientemail) {
        this.recepientemail = recepientemail;
    }

    @Basic
    @Column(name = "PRICEPERUNIT")
    public Double getPriceperunit() {
        return priceperunit;
    }

    public void setPriceperunit(Double priceperunit) {
        this.priceperunit = priceperunit;
    }

    @Basic
    @Column(name = "CURRENCY")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "UNITWEIGHT")
    public Double getUnitweight() {
        return unitweight;
    }

    public void setUnitweight(Double unitweight) {
        this.unitweight = unitweight;
    }

    @Basic
    @Column(name = "FULLWEIGHT")
    public Double getFullweight() {
        return fullweight;
    }

    public void setFullweight(Double fullweight) {
        this.fullweight = fullweight;
    }

    @Basic
    @Column(name = "UNITNAME")
    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    @Basic
    @Column(name = "UNITDESCRIPTION")
    public String getUnitdescription() {
        return unitdescription;
    }

    public void setUnitdescription(String unitdescription) {
        this.unitdescription = unitdescription;
    }

    @Basic
    @Column(name = "ITEMQUANTITY")
    public Long getItemquantity() {
        return itemquantity;
    }

    public void setItemquantity(Long itemquantity) {
        this.itemquantity = itemquantity;
    }

    @Basic
    @Column(name = "TOTALNUMBEROFPIECES")
    public Long getTotalnumberofpieces() {
        return totalnumberofpieces;
    }

    public void setTotalnumberofpieces(Long totalnumberofpieces) {
        this.totalnumberofpieces = totalnumberofpieces;
    }

    @Basic
    @Column(name = "WEBSITE")
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Basic
    @Column(name = "UNITID_SKU")
    public Long getUnitidSku() {
        return unitidSku;
    }

    public void setUnitidSku(Long unitidSku) {
        this.unitidSku = unitidSku;
    }

    @Basic
    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoadAsos loadAsos = (LoadAsos) o;
        return ltId == loadAsos.ltId &&
                Objects.equals(ltPart, loadAsos.ltPart) &&
                Objects.equals(hawb, loadAsos.hawb) &&
                Objects.equals(dateofawb, loadAsos.dateofawb) &&
                Objects.equals(mawbcode, loadAsos.mawbcode) &&
                Objects.equals(dateoforder, loadAsos.dateoforder) &&
                Objects.equals(orderno, loadAsos.orderno) &&
                Objects.equals(eshopsite, loadAsos.eshopsite) &&
                Objects.equals(recepientname, loadAsos.recepientname) &&
                Objects.equals(passportnumber, loadAsos.passportnumber) &&
                Objects.equals(passportdate, loadAsos.passportdate) &&
                Objects.equals(city, loadAsos.city) &&
                Objects.equals(region, loadAsos.region) &&
                Objects.equals(district, loadAsos.district) &&
                Objects.equals(postalcode, loadAsos.postalcode) &&
                Objects.equals(recepientaddress, loadAsos.recepientaddress) &&
                Objects.equals(recepienttelephone, loadAsos.recepienttelephone) &&
                Objects.equals(recepientemail, loadAsos.recepientemail) &&
                Objects.equals(priceperunit, loadAsos.priceperunit) &&
                Objects.equals(currency, loadAsos.currency) &&
                Objects.equals(unitweight, loadAsos.unitweight) &&
                Objects.equals(fullweight, loadAsos.fullweight) &&
                Objects.equals(unitname, loadAsos.unitname) &&
                Objects.equals(unitdescription, loadAsos.unitdescription) &&
                Objects.equals(itemquantity, loadAsos.itemquantity) &&
                Objects.equals(totalnumberofpieces, loadAsos.totalnumberofpieces) &&
                Objects.equals(website, loadAsos.website) &&
                Objects.equals(unitidSku, loadAsos.unitidSku) &&
                Objects.equals(createDate, loadAsos.createDate) &&
                Objects.equals(createUser, loadAsos.createUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ltId, ltPart, hawb, dateofawb, mawbcode, dateoforder, orderno, eshopsite, recepientname, passportnumber, passportdate, city, region, district, postalcode, recepientaddress, recepienttelephone, recepientemail, priceperunit, currency, unitweight, fullweight, unitname, unitdescription, itemquantity, totalnumberofpieces, website, unitidSku, createDate, createUser);
    }
}
