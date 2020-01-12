package ua.com.idltd.hydracargo.utils.filehandler.exception;


import ua.com.idltd.hydracargo.utils.filehandler.handler.FileTypeEnum;

public class UnsupportedFileFormatException extends Exception{
    public UnsupportedFileFormatException(String filename, FileTypeEnum fte) {
        super("Format "+filename+" is not valid "+fte.name()+" format");
    }
}
