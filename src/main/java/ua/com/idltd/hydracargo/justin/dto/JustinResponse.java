package ua.com.idltd.hydracargo.justin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JustinResponse {

    private String status;
    private String msg;
    private List<JustinResponseResult> result;

    public List<JustinResponseResult> getResult() {
        return result;
    }

    public void setResult(List<JustinResponseResult> result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
