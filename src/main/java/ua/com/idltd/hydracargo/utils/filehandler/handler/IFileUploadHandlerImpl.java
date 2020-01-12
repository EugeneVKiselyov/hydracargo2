package ua.com.idltd.hydracargo.utils.filehandler.handler;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.springframework.web.multipart.MultipartFile;
import ua.com.idltd.hydracargo.utils.filehandler.entity.FilehandlerAtomLog;
import ua.com.idltd.hydracargo.utils.filehandler.entity.FilehandlerBuffer;
import ua.com.idltd.hydracargo.utils.filehandler.entity.FilehandlerDetailLog;
import ua.com.idltd.hydracargo.utils.filehandler.entity.FilehandlerLog;
import ua.com.idltd.hydracargo.utils.filehandler.exception.UnsupportedFileFormatException;
import ua.com.idltd.hydracargo.utils.filehandler.repository.FileHandlerAtomLogRepository;
import ua.com.idltd.hydracargo.utils.filehandler.repository.FileHandlerBufferRepository;
import ua.com.idltd.hydracargo.utils.filehandler.repository.FileHandlerDetailLogRepository;
import ua.com.idltd.hydracargo.utils.filehandler.repository.FileHandlerLogRepository;

import java.io.IOException;
import java.sql.Date;

import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;

public abstract class IFileUploadHandlerImpl implements IFileUploadHandler {

    private final FileHandlerLogRepository fileHandlerLogRepository;
    private final FileHandlerDetailLogRepository fileHandlerDetailLogRepository;
    private final FileHandlerBufferRepository fileHandlerBufferRepository;
    private final FileHandlerAtomLogRepository fileHandlerAtomLogRepository;
    protected final FileTypeEnum fte;
    protected final FilehandlerLog fhl;
    protected final FilehandlerBuffer fhlb;

    protected DataFormatter formatter;

    public IFileUploadHandlerImpl(FileTypeEnum fte, FilehandlerLog fhl, MultipartFile file, FileHandlerBufferRepository fileHandlerBufferRepository, FileHandlerLogRepository fileHandlerLogRepository, FileHandlerDetailLogRepository fileHandlerDetailLogRepository,
                                  FileHandlerAtomLogRepository fileHandlerAtomLogRepository) throws UnsupportedFileFormatException, IOException {
        this.fte=fte;
        this.fileHandlerBufferRepository=fileHandlerBufferRepository;
        this.fileHandlerLogRepository = fileHandlerLogRepository;
        this.fileHandlerDetailLogRepository = fileHandlerDetailLogRepository;
        this.fileHandlerAtomLogRepository = fileHandlerAtomLogRepository;
        this.fhl = fhl;
        this.fhlb = new FilehandlerBuffer();
        this.formatter = new DataFormatter();

        try {
            //загружаем файл в базу

            //создаем запись в детальном логе
            savelog(FileLogStatusEnum.SUCCESS,"Start load "+file.getOriginalFilename()+" to database");

            fhlb.setFhl_Id(fhl.getFhl_Id());
            fhlb.setFhlb_Date(new Date(System.currentTimeMillis()));
            fhlb.setFhlb_User(GetUserName());
            fhlb.setFhlb_Name(file.getOriginalFilename());
            fhlb.setFhlb_Extention(fhlb.getFhlb_Name().substring(fhlb.getFhlb_Name().lastIndexOf(".") + 1));
            fhlb.setFhlb_Body(file.getBytes());
            fileHandlerBufferRepository.save(fhlb);
            //записываем успешную загрузку в детальный лог
            savelog(FileLogStatusEnum.SUCCESS,"End load "+file.getOriginalFilename()+" to database");

            //проверяем формат файла
            validate();
            savelog(FileLogStatusEnum.SUCCESS,"File "+fhlb.getFhlb_Name()+" validated");

        } catch (IOException | UnsupportedFileFormatException e) {
            savelog(FileLogStatusEnum.ERROR,e.getMessage());
            throw e;
        }
    }

    @Override
    public void savelog(FileLogStatusEnum status, String body) {
            FilehandlerDetailLog fhld;
            fhld = new FilehandlerDetailLog();
            fhld.setFhl_Id(fhl.getFhl_Id());
            fhld.setFhld_Date(new Date(System.currentTimeMillis()));
            fhld.setFhld_User(GetUserName());

            fhld.setFhld_Body(body);

            fhld.setFhld_Status(status.toString());
            fileHandlerDetailLogRepository.save(fhld);
    }
    @Override
    public void saveatomlog(FileLogStatusEnum status, String atom, String error) {
        FilehandlerAtomLog fhal;
        fhal=new FilehandlerAtomLog();
        fhal.setFHL_ID(fhl.getFhl_Id());
        fhal.setFhal_atom(atom);
        fhal.setFhal_error(error);
        fhal.setFhal_status(status.toString());
        fileHandlerAtomLogRepository.save(fhal);
    }

}
