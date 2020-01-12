package ua.com.idltd.hydracargo.utils.filehandler.handler;


import ua.com.idltd.hydracargo.exception.DispatchIdNullException;
import ua.com.idltd.hydracargo.utils.filehandler.FileUploadResult;
import ua.com.idltd.hydracargo.utils.filehandler.exception.UnsupportedFileFormatException;

import java.io.IOException;

public interface IFileUploadHandler {

    // проверка формата файла
    public void validate() throws UnsupportedFileFormatException;
    // записать разобрать и записать в базу
    public FileUploadResult upload() throws DispatchIdNullException, IOException;
    // записать в Log
    public void savelog(FileLogStatusEnum status, String body);
    // записать разбор строки из загружаемого файла в Log
    public void saveatomlog(FileLogStatusEnum status, String atom, String error);
    //проверить формат файла
    public boolean validatefileformat(byte[] body);

}
