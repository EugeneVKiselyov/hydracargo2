package ua.com.idltd.hydracargo.po_user.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PO_USER")
public class Po_User {

  @Id
  @Column(name = "PO_ID", nullable = false)
  @SequenceGenerator(name = "PO_USER_SEQ", sequenceName = "PO_USER_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PO_USER_SEQ")
  public Long po_id;

  @Column(name = "PO_USERNAME")
  public String po_username;

  @Column(name = "PO_PASSWORD")
  public String po_password;

  @Column(name = "PO_EMAIL")
  public String po_email;

  @Column(name = "PO_PHONE")
  public String po_phone;

  @Column(name = "PO_FIRSTNAME")
  public String po_firstname;

  @Column(name = "PO_LASTNAME")
  public String po_lastname;

  @Column(name = "PO_COUNTRY")
  public String po_country;

  @Column(name = "PO_ZIPCODE")
  public String po_zipcode;

  @Column(name = "PO_CITY")
  public String po_city;

  @Column(name = "PO_STREET")
  public String po_street;

  @Column(name = "PO_HOUSE")
  public String po_house;

  @Column(name = "PO_APARTMENT")
  public String po_apartment;

  @Column(name = "PO_ACTIVE")
  public Long po_active;

  @Column(name = "USER_ID")
  public Long user_id;

  @Column(name = "PO_ENCRIPTPASSWORD")
  public String po_encriptpassword;
}
