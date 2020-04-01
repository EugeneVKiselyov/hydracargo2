package ua.com.idltd.hydracargo.utils.filehandler;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.com.idltd.hydracargo.contragent.contragentdefault.repository.ContragentDefaultRepository;
import ua.com.idltd.hydracargo.declaration_cache.repository.Declaration_cacheRepository;
import ua.com.idltd.hydracargo.dispatch.repository.DispatchRepository;
import ua.com.idltd.hydracargo.entrepot.repository.EntrepotRepository;
import ua.com.idltd.hydracargo.exception.DispatchIdNullException;
import ua.com.idltd.hydracargo.insurancetype.repository.Fin_Insurance_TypeRepository;
import ua.com.idltd.hydracargo.productgroup.repository.Fin_ProductGroupRepository;
import ua.com.idltd.hydracargo.request.repository.RequestRepository;
import ua.com.idltd.hydracargo.scan.repository.Declaration_scanRepository;
import ua.com.idltd.hydracargo.typepackagematerial.ratetype.repository.Fin_TypePackageMaterialRepository;
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
    private final ContragentDefaultRepository contragentDefaultRepository;
    private final Fin_ProductGroupRepository fin_productGroupRepository;
    private final Fin_TypePackageMaterialRepository fin_typePackageMaterialRepository;
    private final Fin_Insurance_TypeRepository fin_insurance_typeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public FileUploadService(FileHandlerBufferRepository fileHandlerBufferRepository, FileHandlerLogRepository fileHandlerLogRepository, FileHandlerDetailLogRepository fileHandlerDetailLogRepository, FileHandlerAtomLogRepository fileHandlerAtomLogRepository, RequestRepository requestRepository, DispatchRepository dispatchRepository, Declaration_cacheRepository declaration_cacheRepository, EntrepotRepository entrepotRepository, LoadAsosRepository loadAsosRepository, Declaration_scanRepository declaration_scanRepository, LoadPackingRepository loadPackingRepository, ContragentDefaultRepository contragentDefaultRepository, Fin_ProductGroupRepository fin_productGroupRepository, Fin_TypePackageMaterialRepository fin_typePackageMaterialRepository, Fin_Insurance_TypeRepository fin_insurance_typeRepository) {
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
        this.contragentDefaultRepository = contragentDefaultRepository;
        this.fin_productGroupRepository = fin_productGroupRepository;
        this.fin_typePackageMaterialRepository = fin_typePackageMaterialRepository;
        this.fin_insurance_typeRepository = fin_insurance_typeRepository;
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
                case BASE :  fuh=new FileUploadHandlerBase(fhl,file,fileHandlerBufferRepository,fileHandlerLogRepository,fileHandlerDetailLogRepository,fileHandlerAtomLogRepository,
                        dis_id,service_id,type_id,scountry_iso2,rcountry_iso2,
                        requestRepository, dispatchRepository, declaration_cacheRepository, entrepotRepository);
                    break;
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
            switch (fte) {
                case PACKING_LIST: fuh=new FileUploadHandlerPackingList(fte,fhl,file,fileHandlerBufferRepository,fileHandlerLogRepository,fileHandlerDetailLogRepository,fileHandlerAtomLogRepository, entityManager, req_id, loadPackingRepository);
                    break;
                case PACKING_LIST_SMALL: fuh=new FileUploadHandlerPackingListSmall(fte,fhl,file,fileHandlerBufferRepository,fileHandlerLogRepository,fileHandlerDetailLogRepository,fileHandlerAtomLogRepository, entityManager, req_id, loadPackingRepository, contragentDefaultRepository, fin_productGroupRepository, fin_typePackageMaterialRepository, fin_insurance_typeRepository, dispatchRepository);
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
}
