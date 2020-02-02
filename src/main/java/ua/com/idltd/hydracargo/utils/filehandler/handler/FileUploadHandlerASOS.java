package ua.com.idltd.hydracargo.utils.filehandler.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.multipart.MultipartFile;
import ua.com.idltd.hydracargo.declaration_cache.repository.Declaration_cacheRepository;
import ua.com.idltd.hydracargo.dispatch.entity.Dispatch;
import ua.com.idltd.hydracargo.dispatch.repository.DispatchRepository;
import ua.com.idltd.hydracargo.entrepot.entity.Entrepot;
import ua.com.idltd.hydracargo.entrepot.repository.EntrepotRepository;
import ua.com.idltd.hydracargo.exception.DispatchIdNullException;
import ua.com.idltd.hydracargo.request.repository.RequestRepository;
import ua.com.idltd.hydracargo.utils.filehandler.FileUploadResult;
import ua.com.idltd.hydracargo.utils.filehandler.entity.FilehandlerLog;
import ua.com.idltd.hydracargo.utils.filehandler.entity.LoadAsos;
import ua.com.idltd.hydracargo.utils.filehandler.exception.UnsupportedFileFormatException;
import ua.com.idltd.hydracargo.utils.filehandler.repository.*;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import static ua.com.idltd.hydracargo.utils.StaticUtils.ConvertTraceExceptionToText;
import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;


//import org.apache.tika.Tika;

public class FileUploadHandlerASOS extends IFileUploadHandlerPostImpl {

    private final RequestRepository requestRepository;
    private final DispatchRepository dispatchRepository;
    private final Declaration_cacheRepository declaration_cacheRepository;
    private final EntrepotRepository entrepotRepository;
    private final LoadAsosRepository loadAsosRepository;
    private final EntityManager entityManager;

    private final Dispatch dispatch;
    private final Entrepot entrepot;
    private final ObjectMapper mapper;
    private final SimpleDateFormat dateFormater;
    private final Long lt_part;

    public FileUploadHandlerASOS(FilehandlerLog fhl, MultipartFile file, FileHandlerBufferRepository fileHandlerBufferRepository, FileHandlerLogRepository fileHandlerLogRepository, FileHandlerDetailLogRepository fileHandlerDetailLogRepository, FileHandlerAtomLogRepository fileHandlerAtomLogRepository,
                                 Long dis_id, Long service_id, Long type_id, String scountry_iso2, String rcountry_iso2,
                                 RequestRepository requestRepository, DispatchRepository dispatchRepository, Declaration_cacheRepository declaration_cacheRepository, EntrepotRepository entrepotRepository,
                                 EntityManager entityManager,
                                 LoadAsosRepository loadAsosRepository
                                ) throws UnsupportedFileFormatException, IOException, DispatchIdNullException {
        super(FileTypeEnum.VEX, fhl,file, fileHandlerBufferRepository, fileHandlerLogRepository, fileHandlerDetailLogRepository,fileHandlerAtomLogRepository,
              dis_id, service_id,type_id,scountry_iso2,rcountry_iso2);
        this.requestRepository=requestRepository;
        this.dispatchRepository=dispatchRepository;
        this.declaration_cacheRepository=declaration_cacheRepository;
        this.entrepotRepository=entrepotRepository;
        // find dispatch
        this.dispatch = dispatchRepository.findById(dis_id).orElse(null);
        if (this.dispatch == null) throw new DispatchIdNullException("Dispatch dis_id="+dis_id+" not found");
        mapper = new ObjectMapper();
        this.entrepot = entrepotRepository.findById(dispatch.ep_id).orElse(null);
        this.loadAsosRepository=loadAsosRepository;
        this.entityManager=entityManager;
        //получаем LT_PART
        this.lt_part = ((BigDecimal) entityManager
                .createNativeQuery(
                        "select LOAD_ASOS_PART_SEQ.nextval from dual"
                )
                .getSingleResult()).longValue();
        dateFormater = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
    }

    @Override
    public boolean validatefileformat(byte[] body) {
        //проверить формат файла - эксель или эксель 2003
//        Tika tika = new Tika();
//        String filetype = tika.detect(body);
//        return filetype.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")||
//                filetype.equalsIgnoreCase("application/vnd.ms-excel");
        boolean result;
        //проверяем формат файла
        try (InputStream inputStream = new ByteArrayInputStream(fhlb.getFhlb_Body()); HSSFWorkbook wb = new HSSFWorkbook(inputStream)) {
            result=true;
        }catch (Exception e){
            result=false;
        }
        return result;
    }

    @Override
    public void validate() throws UnsupportedFileFormatException {
        //проверяем это поддерживаемый формат файла
        if (!validatefileformat(fhlb.getFhlb_Body())) throw new UnsupportedFileFormatException(fhlb.getFhlb_Name(),FileTypeEnum.ASOS);;
    }

    @Override
    public FileUploadResult upload() throws DispatchIdNullException, IOException {
        savelog(FileLogStatusEnum.SUCCESS, MessageFormat.format("Start parce and load file {0}", fhlb.getFhlb_Name()));


        Long successCount=0L;
        Long errorCount=0L;

        try (InputStream inputStream = new ByteArrayInputStream(fhlb.getFhlb_Body()); HSSFWorkbook wb = new HSSFWorkbook(inputStream)) {
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> it = sheet.iterator();

            //Загружаем в промежуточную таблицу
            savelog(FileLogStatusEnum.SUCCESS, MessageFormat.format("Start load into LOAD_ASOS table LT_PART={0}", lt_part));

            //пропускаем 1-ю строку
            if (it.hasNext()) {
                Row row = it.next();
            }

            //обрабатываем следующие
            while (it.hasNext()) {
                Row row = it.next();
                if (process(row)) successCount += 1;
                else errorCount += 1;
            }

            savelog(FileLogStatusEnum.SUCCESS, MessageFormat.format("End load into LOAD_ASOS table LT_PART={0}, Success load={1}, Error={2}", lt_part,successCount,errorCount));

            //разбираем и вставляем посылки в депешу
            savelog(FileLogStatusEnum.SUCCESS, MessageFormat.format("Start convert to declarations LOAD_ASOS table LT_PART={0}", lt_part));

            StoredProcedureQuery create_request = entityManager
                    .createStoredProcedureQuery("pkg_asos.create_request")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                    .setParameter(1, dis_id)
                    .setParameter(2, lt_part)
                    .setParameter(3, service_id)
                    .setParameter(4, type_id);
            create_request.execute();

            savelog(FileLogStatusEnum.SUCCESS, MessageFormat.format("End convert to declarations LOAD_ASOS table LT_PART={0}", lt_part));


            //считаем количество посылок
            dispatch.dis_seatsnum = declaration_cacheRepository.countByDis_id(dispatch.dis_id);
            dispatch.dis_weight_f = declaration_cacheRepository.sumDc_tweightByDis_id(dispatch.dis_id);
            dispatchRepository.save(dispatch);
            dispatchRepository.refresh(dispatch);

            savelog(FileLogStatusEnum.SUCCESS, MessageFormat.format("End parce and load file {0}", fhlb.getFhlb_Name()));
        } catch (IOException e) {
            e.printStackTrace();
            savelog(FileLogStatusEnum.ERROR, e.getMessage());
            throw e;
        }
        return new FileUploadResult(successCount,errorCount);
    }

    private static boolean isCellEmpty(Cell cell ){
        boolean result;
        CellType type = cell.getCellTypeEnum();
        switch (type){
            case BLANK: result=true; break;
            case STRING : result=cell.getStringCellValue().isEmpty(); break;
            case NUMERIC: result=Double.toString(cell.getNumericCellValue()).isEmpty(); break;
            default: result=false;
        }
        return result;
    }

    @Override
    protected boolean process(Row row) throws JsonProcessingException {
        boolean result = false;
        ObjectMapper mapper = new ObjectMapper();

        LoadAsos loadAsos = new LoadAsos();
        loadAsos.setDis_id(dis_id);
        loadAsos.setLtPart(lt_part);
        loadAsos.setCreateUser(GetUserName());

        try {
            if (!row.getCell(1).getStringCellValue().isEmpty()) loadAsos.setHawb(formatter.formatCellValue(row.getCell(1)));
            if (!row.getCell(2).getStringCellValue().isEmpty()) loadAsos.setDateofawb(new java.sql.Date(dateFormater.parse(formatter.formatCellValue(row.getCell(2))).getTime()));
            if (!row.getCell(3).getStringCellValue().isEmpty()) loadAsos.setMawbcode(formatter.formatCellValue(row.getCell(3)));
            if (!row.getCell(4).getStringCellValue().isEmpty()) loadAsos.setDateoforder(new java.sql.Date(dateFormater.parse(formatter.formatCellValue(row.getCell(4))).getTime()));
            if (!row.getCell(5).getStringCellValue().isEmpty()) loadAsos.setOrderno(formatter.formatCellValue(row.getCell(5)));
            if (!row.getCell(6).getStringCellValue().isEmpty()) loadAsos.setEshopsite(formatter.formatCellValue(row.getCell(6)));
            if (!row.getCell(7).getStringCellValue().isEmpty()) loadAsos.setRecepientname(formatter.formatCellValue(row.getCell(7)));
            if (!row.getCell(8).getStringCellValue().isEmpty()) loadAsos.setPassportnumber(formatter.formatCellValue(row.getCell(8)));
            if (!row.getCell(9).getStringCellValue().isEmpty()) loadAsos.setPassportdate(new java.sql.Date(dateFormater.parse(formatter.formatCellValue(row.getCell(9))).getTime()));
            if (!row.getCell(10).getStringCellValue().isEmpty()) loadAsos.setCity(formatter.formatCellValue(row.getCell(10)));
            if (!row.getCell(11).getStringCellValue().isEmpty()) loadAsos.setRegion(formatter.formatCellValue(row.getCell(11)));
            if (!row.getCell(12).getStringCellValue().isEmpty()) loadAsos.setDistrict(formatter.formatCellValue(row.getCell(12)));
            if (!row.getCell(13).getStringCellValue().isEmpty()) loadAsos.setPostalcode(formatter.formatCellValue(row.getCell(13)));
            if (!row.getCell(14).getStringCellValue().isEmpty()) loadAsos.setRecepientaddress(formatter.formatCellValue(row.getCell(14)));
            if (!row.getCell(15).getStringCellValue().isEmpty()) loadAsos.setRecepienttelephone(formatter.formatCellValue(row.getCell(15)));
            if (!row.getCell(16).getStringCellValue().isEmpty()) loadAsos.setRecepientemail(formatter.formatCellValue(row.getCell(16)));
            if (!row.getCell(17).getStringCellValue().isEmpty()) loadAsos.setPriceperunit(Double.valueOf(formatter.formatCellValue(row.getCell(17)).replace(',','.')));
            if (!row.getCell(18).getStringCellValue().isEmpty()) loadAsos.setCurrency(formatter.formatCellValue(row.getCell(18)));
            if (!row.getCell(19).getStringCellValue().isEmpty()) loadAsos.setUnitweight(Double.valueOf(formatter.formatCellValue(row.getCell(19)).replace(',','.'))/1000);
            if (!row.getCell(20).getStringCellValue().isEmpty()) loadAsos.setFullweight(Double.valueOf(formatter.formatCellValue(row.getCell(20)).replace(',','.'))/1000);
            if (!row.getCell(21).getStringCellValue().isEmpty()) loadAsos.setUnitname(formatter.formatCellValue(row.getCell(21)));
            if (!row.getCell(22).getStringCellValue().isEmpty()) loadAsos.setUnitdescription(formatter.formatCellValue(row.getCell(22)));
            if (!row.getCell(23).getStringCellValue().isEmpty()) loadAsos.setItemquantity(Long.parseLong(formatter.formatCellValue(row.getCell(23))));
            if (!row.getCell(24).getStringCellValue().isEmpty()) loadAsos.setTotalnumberofpieces(Long.parseLong(formatter.formatCellValue(row.getCell(24))));
            if (!row.getCell(25).getStringCellValue().isEmpty()) loadAsos.setWebsite(formatter.formatCellValue(row.getCell(25)));
            if (!row.getCell(26).getStringCellValue().isEmpty()) loadAsos.setUnitidSku(Long.parseLong(formatter.formatCellValue(row.getCell(26))));

            loadAsosRepository.save(loadAsos);

            saveatomlog(FileLogStatusEnum.SUCCESS,mapper.writeValueAsString(loadAsos),null);
            result = true;
        } catch (JpaSystemException e) {
            saveatomlog(FileLogStatusEnum.ERROR,mapper.writeValueAsString(loadAsos), String.format("Номер строки: %d%n Ошибка: %s", row.getRowNum()+1, e.getMostSpecificCause().getLocalizedMessage()));
            result=false;
        }  catch (Exception e) {
            saveatomlog(FileLogStatusEnum.ERROR,mapper.writeValueAsString(loadAsos),String.format("Номер строки: %d%n Ошибка: %s", row.getRowNum()+1, ConvertTraceExceptionToText(e)));
            result=false;
        }
        return result;
    }
}
