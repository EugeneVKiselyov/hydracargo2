package ua.com.idltd.hydracargo.utils.filehandler.json;

public class FilehandlerDetailLogBody {
    private String event;
    private String error;

    public FilehandlerDetailLogBody(String event, String error) {
        this.event = event;
        this.error = error;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
