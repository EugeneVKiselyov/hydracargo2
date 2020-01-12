package ua.com.idltd.hydracargo.utils.filehandler;

public class FileUploadResult {
    private final Long succressCount;
    private final Long errorCount;

    public FileUploadResult(Long succressCount, Long errorCount) {
        this.succressCount = succressCount;
        this.errorCount = errorCount;
    }

    public Long getSuccressCount() {
        return succressCount;
    }

    public Long getErrorCount() {
        return errorCount;
    }
}
