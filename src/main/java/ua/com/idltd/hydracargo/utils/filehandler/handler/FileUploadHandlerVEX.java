package ua.com.idltd.hydracargo.utils.filehandler.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.multipart.MultipartFile;
import ua.com.idltd.hydracargo.declaration_cache.entity.Declaration_cache;
import ua.com.idltd.hydracargo.declaration_cache.repository.Declaration_cacheRepository;
import ua.com.idltd.hydracargo.dispatch.entity.Dispatch;
import ua.com.idltd.hydracargo.dispatch.repository.DispatchRepository;
import ua.com.idltd.hydracargo.entrepot.entity.Entrepot;
import ua.com.idltd.hydracargo.entrepot.repository.EntrepotRepository;
import ua.com.idltd.hydracargo.exception.DispatchIdNullException;
import ua.com.idltd.hydracargo.request.repository.RequestRepository;
import ua.com.idltd.hydracargo.utils.filehandler.FileUploadResult;
import ua.com.idltd.hydracargo.utils.filehandler.entity.FilehandlerLog;
import ua.com.idltd.hydracargo.utils.filehandler.exception.UnsupportedFileFormatException;
import ua.com.idltd.hydracargo.utils.filehandler.repository.FileHandlerAtomLogRepository;
import ua.com.idltd.hydracargo.utils.filehandler.repository.FileHandlerBufferRepository;
import ua.com.idltd.hydracargo.utils.filehandler.repository.FileHandlerDetailLogRepository;
import ua.com.idltd.hydracargo.utils.filehandler.repository.FileHandlerLogRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.MessageFormat;
import java.util.Iterator;

import static ua.com.idltd.hydracargo.utils.StaticUtils.*;

//import org.apache.tika.Tika;

public class FileUploadHandlerVEX extends IFileUploadHandlerPostImpl {

    private final RequestRepository requestRepository;
    private final DispatchRepository dispatchRepository;
    private final Declaration_cacheRepository declaration_cacheRepository;
    private final EntrepotRepository entrepotRepository;

    private final Dispatch dispatch;
    private final Entrepot entrepot;
    private final ObjectMapper mapper;

    public FileUploadHandlerVEX(FilehandlerLog fhl, MultipartFile file, FileHandlerBufferRepository fileHandlerBufferRepository, FileHandlerLogRepository fileHandlerLogRepository, FileHandlerDetailLogRepository fileHandlerDetailLogRepository, FileHandlerAtomLogRepository fileHandlerAtomLogRepository,
                                Long dis_id, Long service_id, Long type_id, String scountry_iso2, String rcountry_iso2,
                                RequestRepository requestRepository, DispatchRepository dispatchRepository, Declaration_cacheRepository declaration_cacheRepository, EntrepotRepository entrepotRepository
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
        if (!validatefileformat(fhlb.getFhlb_Body())) throw new UnsupportedFileFormatException(fhlb.getFhlb_Name(),FileTypeEnum.VEX);;
    }

    @Override
    public FileUploadResult upload() throws DispatchIdNullException, IOException {
        savelog(FileLogStatusEnum.SUCCESS, MessageFormat.format("Start parce and load file {0}", fhlb.getFhlb_Name()));

        Long successCount=0L;
        Long errorCount=0L;

        try (InputStream inputStream = new ByteArrayInputStream(fhlb.getFhlb_Body()); HSSFWorkbook wb = new HSSFWorkbook(inputStream)) {
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> it = sheet.iterator();

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

            //считаем количество посылок
            dispatch.dis_seatsnum = declaration_cacheRepository.countByDis_id(dispatch.dis_id);
            dispatch.dis_weight_f = declaration_cacheRepository.sumDc_tweightByDis_id(dispatch.dis_id);
            dispatchRepository.save(dispatch);

            savelog(FileLogStatusEnum.SUCCESS, MessageFormat.format("End parce and load file {0}", fhlb.getFhlb_Name()));
        } catch (IOException e) {
            e.printStackTrace();
            savelog(FileLogStatusEnum.ERROR, e.getMessage());
            throw e;
        }
        return new FileUploadResult(successCount,errorCount, fhl.getFhl_Id());
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

        Declaration_cache declaration_cache = new Declaration_cache();
        try {
            declaration_cache.req_id = dispatch.req_id;
            declaration_cache.dis_id = dis_id;
            declaration_cache.dc_shipment = formatter.formatCellValue(row.getCell(0));
            declaration_cache.dc_box = formatter.formatCellValue(row.getCell(1));
            declaration_cache.dc_description = formatter.formatCellValue(row.getCell(2));
            if (!isCellEmpty(row.getCell(3))) declaration_cache.dc_qty = formatter.formatCellValue(row.getCell(3));
            if (!isCellEmpty(row.getCell(4)))
                declaration_cache.dc_value = formatter.formatCellValue(row.getCell(4));
            if (!isCellEmpty(row.getCell(5)))
                declaration_cache.dc_tweight = Double.valueOf(formatter.formatCellValue(row.getCell(5)).replace(',', '.'));
            if (!isCellEmpty(row.getCell(6)))
                declaration_cache.dc_tvalue = Double.valueOf(formatter.formatCellValue(row.getCell(6)).replace(',', '.'));
            //      declaration_cache.dc_comment=row.getCell(7).getStringCellValue(); добавить
            declaration_cache.dc_tracenum = formatter.formatCellValue(row.getCell(8));
            declaration_cache.dc_sname = formatter.formatCellValue(row.getCell(9));
            declaration_cache.dc_scity = formatter.formatCellValue(row.getCell(10));
            declaration_cache.dc_saddress = formatter.formatCellValue(row.getCell(11));
            declaration_cache.dc_szip = formatter.formatCellValue(row.getCell(12));
            declaration_cache.dc_sphone = formatter.formatCellValue(row.getCell(13));
            declaration_cache.dc_rfname = formatter.formatCellValue(row.getCell(14));
            declaration_cache.dc_rlname = formatter.formatCellValue(row.getCell(15));
            declaration_cache.dc_rregion = formatter.formatCellValue(row.getCell(16));
//                declaration_cache.dc_rctype=formatter.formatCellValue(row.getCell(17));
            declaration_cache.dc_rcity = formatter.formatCellValue(row.getCell(18));
//                declaration_cache.dc_rstype=formatter.formatCellValue(row.getCell(19));
            declaration_cache.dc_rstreet = formatter.formatCellValue(row.getCell(20));
            declaration_cache.dc_rbld = formatter.formatCellValue(row.getCell(21));
            declaration_cache.dc_rapt = formatter.formatCellValue(row.getCell(22));
            declaration_cache.dc_rzip = formatter.formatCellValue(row.getCell(23));
            if ((row.getCell(24)!=null) && !isCellEmpty(row.getCell(24)))
                declaration_cache.dc_rphone = formatter.formatCellValue(row.getCell(24));
            if ((row.getCell(25)!=null) && !isCellEmpty(row.getCell(25)))
                declaration_cache.dc_currency = formatter.formatCellValue(row.getCell(25));
            else declaration_cache.dc_currency = "USD";

            if ((row.getCell(26)!=null) && !isCellEmpty(row.getCell(26)))
                if (DateUtil.isCellDateFormatted(row.getCell(26))) declaration_cache.dc_createdate = row.getCell(26).getDateCellValue();
                else declaration_cache.dc_createdate = new Date(System.currentTimeMillis());
            else declaration_cache.dc_createdate = new Date(System.currentTimeMillis());

            declaration_cache.dc_user_name = GetUserName();
            declaration_cache.dc_scountry = scountry_iso2;
            declaration_cache.dc_rcountry = rcountry_iso2;
            declaration_cache.dc_service_id = service_id.toString();
            declaration_cache.dc_type_id = type_id.toString();

            declaration_cacheRepository.save(declaration_cache);

            saveatomlog(FileLogStatusEnum.SUCCESS,mapper.writeValueAsString(declaration_cache),null);
            result = true;
        } catch (JpaSystemException e) {
            saveatomlog(FileLogStatusEnum.ERROR,mapper.writeValueAsString(declaration_cache), String.format("Номер строки: %d%n Ошибка: %s", row.getRowNum()+1, e.getMostSpecificCause().getLocalizedMessage()));
            result=false;
        }  catch (Exception e) {
            saveatomlog(FileLogStatusEnum.ERROR,mapper.writeValueAsString(declaration_cache),String.format("Номер строки: %d%n Ошибка: %s", row.getRowNum()+1, e.getLocalizedMessage()));
            result=false;
        }

        return result;
    }
}
