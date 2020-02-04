package ua.com.idltd.hydracargo.utils.filehandler;

public class FileUploadResult {
    private final Long succressCount;
    private final Long errorCount;
    private final Long fhl_id;

    public FileUploadResult(Long succressCount, Long errorCount, Long fhl_id) {
        this.succressCount = succressCount;
        this.errorCount = errorCount;
        this.fhl_id = fhl_id;
    }

    public Long getSuccressCount() {
        return succressCount;
    }

    public Long getErrorCount() {
        return errorCount;
    }

    public Long getFhl_id() {
        return fhl_id;
    }

}
