package ua.com.idltd.hydracargo.justin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JustinResponseResult {
    private String orderNumber;
    private String orderDescription;
    private String date;
    private String time;
    private String status;
    private String departmentNumber;
    private String departmentAdress;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public String getDepartmentAdress() {
        return departmentAdress;
    }

    public void setDepartmentAdress(String departmentAdress) {
        this.departmentAdress = departmentAdress;
    }

    public String getEvent(){
        String result;
        result=this.status;
        if (this.departmentNumber!=null && !this.departmentNumber.equalsIgnoreCase("")) result=result+","+this.departmentNumber;
        if (this.departmentAdress!=null && !this.departmentAdress.equalsIgnoreCase("")) result=result+","+this.departmentAdress;

        return result;
    }
}
