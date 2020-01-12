package ua.com.idltd.hydracargo.utils.filehandler.exception;


import ua.com.idltd.hydracargo.utils.filehandler.handler.FileTypeEnum;

public class UnsupportedFileTypeException extends Exception{
    public UnsupportedFileTypeException(FileTypeEnum fte) {
        super("Unsupported format "+fte.name());
    }
}
