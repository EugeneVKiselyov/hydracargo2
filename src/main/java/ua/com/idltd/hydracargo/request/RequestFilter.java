package ua.com.idltd.hydracargo.request;

public class RequestFilter {
    private Long cnt_id;
    private Long ep_id;

    public Long getCnt_id() {
        return cnt_id;
    }

    public void setCnt_id(Long cnt_id) {
        if (cnt_id != null && cnt_id.equals(-1L)) this.cnt_id=null;
        else this.cnt_id=cnt_id;
    }

    public Long getEp_id() {
        return ep_id;
    }

    public void setEp_id(Long ep_id) {
        if (ep_id != null && ep_id.equals(-1L)) this.ep_id=null;
        else this.ep_id=ep_id;
    }

    public RequestFilter(Long cnt_id, Long ep_id) {
        this.cnt_id = cnt_id;
        this.ep_id = ep_id;
    }

    public RequestFilter() {
    }
}
