package ua.com.idltd.hydracargo.utils.filehandler;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.com.idltd.hydracargo.declaration_cache.repository.Declaration_cacheRepository;
import ua.com.idltd.hydracargo.dispatch.repository.DispatchRepository;
import ua.com.idltd.hydracargo.entrepot.repository.EntrepotRepository;
import ua.com.idltd.hydracargo.exception.DispatchIdNullException;
import ua.com.idltd.hydracargo.request.repository.RequestRepository;
import ua.com.idltd.hydracargo.scan.repository.Declaration_scanRepository;
import ua.com.idltd.hydracargo.utils.filehandler.entity.FilehandlerLog;
import ua.com.idltd.hydracargo.utils.filehandler.exception.UnsupportedFileFormatException;
import ua.com.idltd.hydracargo.utils.filehandler.exception.UnsupportedFileTypeException;
import ua.com.idltd.hydracargo.utils.filehandler.handler.*;
import ua.com.idltd.hydracargo.utils.filehandler.repository.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.sql.Date;

import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;

@Service
public class FileUploadService {

    private final FileHandlerBufferRepository fileHandlerBufferRepository;
    private final FileHandlerLogRepository fileHandlerLogRepository;
    private final FileHandlerDetailLogRepository fileHandlerDetailLogRepository;
    private final FileHandlerAtomLogRepository fileHandlerAtomLogRepository;
    private final RequestRepository requestRepository;
    private final DispatchRepository dispatchRepository;
    private final Declaration_cacheRepository declaration_cacheRepository;
    private final EntrepotRepository entrepotRepository;
    private final LoadAsosRepository loadAsosRepository;
    private final Declaration_scanRepository declaration_scanRepository;
    private final LoadPackingRepository loadPackingRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public FileUploadService(FileHandlerBufferRepository fileHandlerBufferRepository, FileHandlerLogRepository fileHandlerLogRepository, FileHandlerDetailLogRepository fileHandlerDetailLogRepository, FileHandlerAtomLogRepository fileHandlerAtomLogRepository, RequestRepository requestRepository, DispatchRepository dispatchRepository, Declaration_cacheRepository declaration_cacheRepository, EntrepotRepository entrepotRepository, LoadAsosRepository loadAsosRepository, Declaration_scanRepository declaration_scanRepository, LoadPackingRepository loadPackingRepository) {
        this.fileHandlerBufferRepository = fileHandlerBufferRepository;
        this.fileHandlerLogRepository = fileHandlerLogRepository;
        this.fileHandlerDetailLogRepository = fileHandlerDetailLogRepository;
        this.fileHandlerAtomLogRepository = fileHandlerAtomLogRepository;
        this.requestRepository = requestRepository;
        this.dispatchRepository = dispatchRepository;
        this.declaration_cacheRepository = declaration_cacheRepository;
        this.entrepotRepository = entrepotRepository;
        this.loadAsosRepository = loadAsosRepository;
        this.declaration_scanRepository = declaration_scanRepository;
        this.loadPackingRepository = loadPackingRepository;
    }

    private void savelog(FilehandlerLog fhl, FileLogStatusEnum flse, String body){
        fhl.setFhl_Body(body);
        fhl.setFhl_Status(flse.toString());
        fileHandlerLogRepository.save(fhl);
    }

    //    Загрузка файла целиком в базу
    public FileUploadResult upload(FileTypeEnum fte,
                                   Long dis_id,
                                   Long service_id,
                                   Long type_id,
                                   String scountry_iso2,
                                   String rcountry_iso2,
                                   MultipartFile file) throws UnsupportedFileFormatException, UnsupportedFileTypeException, DispatchIdNullException, IOException {

        FilehandlerLog fhl = new FilehandlerLog();
        fhl.setFhl_User(GetUserName());

        //создаем в логе запись когда начали и что записываем
        fhl.setFhl_StartDate(new Date(System.currentTimeMillis()));
        savelog(fhl,FileLogStatusEnum.SUCCESS,"Load file:"+file.getOriginalFilename());

        FileUploadResult result;

        try {
            //записываем в базу загружаемый файл

            IFileUploadHandler fuh = null;
            //    Проверка на формат файла и возвращаем обработчик
            switch (fte) {
                case VEX :  fuh=new FileUploadHandlerVEX(fhl,file,fileHandlerBufferRepository,fileHandlerLogRepository,fileHandlerDetailLogRepository,fileHandlerAtomLogRepository,
                                                         dis_id,service_id,type_id,scountry_iso2,rcountry_iso2,
                                                         requestRepository, dispatchRepository, declaration_cacheRepository, entrepotRepository);
                    break;
                case VEX_DIG :  fuh=new FileUploadHandlerVEX_DIG(fhl,file,fileHandlerBufferRepository,fileHandlerLogRepository,fileHandlerDetailLogRepository,fileHandlerAtomLogRepository,
                                                                dis_id,service_id,type_id,scountry_iso2,rcountry_iso2,
                                                                requestRepository, dispatchRepository, declaration_cacheRepository, entrepotRepository);
                    break;
                case ASOS :  fuh=new FileUploadHandlerASOS(fhl,file,fileHandlerBufferRepository,fileHandlerLogRepository,fileHandlerDetailLogRepository,fileHandlerAtomLogRepository,
                        dis_id,service_id,type_id,scountry_iso2,rcountry_iso2,
                        requestRepository, dispatchRepository, declaration_cacheRepository, entrepotRepository,
                        entityManager,loadAsosRepository);
                    break;
                case VEX_SCAN: fuh=new FileUploadHandlerVEX_SCAN(fhl,file,fileHandlerBufferRepository,fileHandlerLogRepository,fileHandlerDetailLogRepository,fileHandlerAtomLogRepository,
                        dis_id,
                        dispatchRepository, declaration_scanRepository);
                    break;
                default:    throw new UnsupportedFileTypeException(fte);
            }
            //разбираем и загружаем посылки
            result = fuh.upload();
            //записываем успешную загрузку в лог
            fhl.setFhl_EndDate(new Date(System.currentTimeMillis()));
            savelog(fhl,FileLogStatusEnum.SUCCESS,"Load file:"+file.getOriginalFilename());

        } catch (DispatchIdNullException | UnsupportedFileTypeException | UnsupportedFileFormatException | IOException e) {
            //записываем ошибку в лог
            fhl.setFhl_EndDate(new Date(System.currentTimeMillis()));
            savelog(fhl, FileLogStatusEnum.ERROR, e.getMessage());
            throw e;
        }
        return result;
    }

    //    Загрузка файла целиком в базу
    public FileUploadResult upload(long req_id, FileTypeEnum fte,
                                   MultipartFile file) throws UnsupportedFileFormatException, UnsupportedFileTypeException, DispatchIdNullException, IOException {

        FilehandlerLog fhl = new FilehandlerLog();
        fhl.setFhl_User(GetUserName());

        //создаем в логе запись когда начали и что записываем
        fhl.setFhl_StartDate(new Date(System.currentTimeMillis()));
        savelog(fhl,FileLogStatusEnum.SUCCESS,"Load file:"+file.getOriginalFilename());

        FileUploadResult result;

        try {
            //записываем в базу загружаемый файл

            IFileUploadHandler fuh = null;
            //    Проверка на формат файла и возвращаем обработчик

            fuh=new FileUploadHandlerPackingList(fte,fhl,file,fileHandlerBufferRepository,fileHandlerLogRepository,fileHandlerDetailLogRepository,fileHandlerAtomLogRepository, entityManager, req_id, loadPackingRepository);

            //разбираем и загружаем посылки
            result = fuh.upload();
            //записываем успешную загрузку в лог
            fhl.setFhl_EndDate(new Date(System.currentTimeMillis()));
            savelog(fhl,FileLogStatusEnum.SUCCESS,"Load file:"+file.getOriginalFilename());


        } catch (DispatchIdNullException | UnsupportedFileTypeException | UnsupportedFileFormatException | IOException e) {
            //записываем ошибку в лог
            fhl.setFhl_EndDate(new Date(System.currentTimeMillis()));
            savelog(fhl, FileLogStatusEnum.ERROR, e.getMessage());
            throw e;
        }
        return result;
    }
}
