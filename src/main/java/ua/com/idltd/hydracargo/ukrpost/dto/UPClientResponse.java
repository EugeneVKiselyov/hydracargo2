package ua.com.idltd.hydracargo.ukrpost.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UPClientResponse {

    private String uuid;
    private String name;
    private String firstName;
    private String middleName;
    private String lastName;
    private String latinName;
    private String postId;
    private String externalId;
    private String uniqueRegistrationNumber;
    private String counterpartyUuid;
    private String addressId;
    private String phoneNumber;
    private String email;
    private String type;
    private String postPayPaymentType;
    private String edrpou;
    private String bankAccount;
    private String contactPersonName;
    private boolean resident;
    private boolean GDPRRead;
    private boolean GDPRAccept;
    private boolean personalDataApproved;
    private boolean checkOnDeliveryAllowed;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getUniqueRegistrationNumber() {
        return uniqueRegistrationNumber;
    }

    public void setUniqueRegistrationNumber(String uniqueRegistrationNumber) {
        this.uniqueRegistrationNumber = uniqueRegistrationNumber;
    }

    public String getCounterpartyUuid() {
        return counterpartyUuid;
    }

    public void setCounterpartyUuid(String counterpartyUuid) {
        this.counterpartyUuid = counterpartyUuid;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostPayPaymentType() {
        return postPayPaymentType;
    }

    public void setPostPayPaymentType(String postPayPaymentType) {
        this.postPayPaymentType = postPayPaymentType;
    }

    public String getEdrpou() {
        return edrpou;
    }

    public void setEdrpou(String edrpou) {
        this.edrpou = edrpou;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public boolean isResident() {
        return resident;
    }

    public void setResident(boolean resident) {
        this.resident = resident;
    }

    public boolean isGDPRRead() {
        return GDPRRead;
    }

    public void setGDPRRead(boolean GDPRRead) {
        this.GDPRRead = GDPRRead;
    }

    public boolean isGDPRAccept() {
        return GDPRAccept;
    }

    public void setGDPRAccept(boolean GDPRAccept) {
        this.GDPRAccept = GDPRAccept;
    }

    public boolean isPersonalDataApproved() {
        return personalDataApproved;
    }

    public void setPersonalDataApproved(boolean personalDataApproved) {
        this.personalDataApproved = personalDataApproved;
    }

    public boolean isCheckOnDeliveryAllowed() {
        return checkOnDeliveryAllowed;
    }

    public void setCheckOnDeliveryAllowed(boolean checkOnDeliveryAllowed) {
        this.checkOnDeliveryAllowed = checkOnDeliveryAllowed;
    }
}
