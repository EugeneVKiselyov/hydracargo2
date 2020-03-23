package ua.com.idltd.hydracargo.utils.filehandler.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.multipart.MultipartFile;
import ua.com.idltd.hydracargo.exception.DispatchIdNullException;
import ua.com.idltd.hydracargo.utils.filehandler.FileUploadResult;
import ua.com.idltd.hydracargo.utils.filehandler.entity.FilehandlerLog;
import ua.com.idltd.hydracargo.utils.filehandler.entity.LoadPacking;
import ua.com.idltd.hydracargo.utils.filehandler.exception.UnsupportedFileFormatException;
import ua.com.idltd.hydracargo.utils.filehandler.exception.UnsupportedFileTypeException;
import ua.com.idltd.hydracargo.utils.filehandler.repository.*;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Iterator;

import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;
import static ua.com.idltd.hydracargo.utils.filehandler.handler.FileTypeEnum.PACKING_LIST;

public class FileUploadHandlerPackingList extends IFileUploadHandlerImpl {
    private final EntityManager entityManager;
    private final Long lt_part;
    private final Long req_id;

    private final LoadPackingRepository loadPackingRepository;

    public FileUploadHandlerPackingList(FileTypeEnum fte, FilehandlerLog fhl, MultipartFile file, FileHandlerBufferRepository fileHandlerBufferRepository, FileHandlerLogRepository fileHandlerLogRepository, FileHandlerDetailLogRepository fileHandlerDetailLogRepository, FileHandlerAtomLogRepository fileHandlerAtomLogRepository,
                                        EntityManager entityManager, Long req_id, LoadPackingRepository loadPackingRepository) throws UnsupportedFileFormatException, IOException, UnsupportedFileTypeException {
        super(fte, fhl, file, fileHandlerBufferRepository, fileHandlerLogRepository, fileHandlerDetailLogRepository, fileHandlerAtomLogRepository);
        this.req_id = req_id;
        this.loadPackingRepository = loadPackingRepository;
        if (fte!=PACKING_LIST) throw new UnsupportedFileTypeException(fte);

        this.entityManager = entityManager;
        this.lt_part = ((BigDecimal) this.entityManager
                .createNativeQuery(
                        "select LOAD_PACKING_PART_SEQ.nextval from dual"
                )
                .getSingleResult()).longValue();
    }


    @Override
    public void validate() throws UnsupportedFileFormatException {
        //проверяем это поддерживаемый формат файла
        if (!validatefileformat(fhlb.getFhlb_Body())) throw new UnsupportedFileFormatException(fhlb.getFhlb_Name(),fte);
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
            savelog(FileLogStatusEnum.SUCCESS, MessageFormat.format("Start load into LOAD_PACKING table LT_PART={0}", lt_part));

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

            savelog(FileLogStatusEnum.SUCCESS, MessageFormat.format("End load into LOAD_PACKING table LT_PART={0}, Success load={1}, Error={2}", lt_part,successCount,errorCount));

            StoredProcedureQuery create_request = entityManager
                    .createStoredProcedureQuery("PKG_REQUEST.parceAndLoad")
                    .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                    .setParameter(1, GetUserName())
                    .setParameter(2, req_id)
                    .setParameter(3, lt_part);
            create_request.execute();

            savelog(FileLogStatusEnum.SUCCESS, MessageFormat.format("End parce and load file {0}", fhlb.getFhlb_Name()));
        } catch (IOException e) {
            e.printStackTrace();
            savelog(FileLogStatusEnum.ERROR, e.getMessage());
            throw e;
        }
        return new FileUploadResult(successCount,errorCount, fhl.getFhl_Id());
    }

    private boolean process(Row row) throws JsonProcessingException {
        boolean result = false;
        ObjectMapper mapper = new ObjectMapper();

        LoadPacking loadPacking = new LoadPacking();
        loadPacking.setLt_part(lt_part);
        loadPacking.setLp_create_user(GetUserName());

        try {
            loadPacking.setLp_box_num(formatter.formatCellValue(row.getCell(0)));
            loadPacking.setLp_box_description(formatter.formatCellValue(row.getCell(1)));
            loadPacking.setLp_box_weight(convertExelStringToDouble(formatter.formatCellValue(row.getCell(2))));
            loadPacking.setLp_box_lenght(convertExelStringToDouble(formatter.formatCellValue(row.getCell(3))));
            loadPacking.setLp_box_height(convertExelStringToDouble(formatter.formatCellValue(row.getCell(4))));
            loadPacking.setLp_box_width(convertExelStringToDouble(formatter.formatCellValue(row.getCell(5))));
            loadPacking.setLp_box_volume_weight(convertExelStringToDouble(formatter.formatCellValue(row.getCell(6))));
            loadPacking.setLp_bc_description(formatter.formatCellValue(row.getCell(7)));
            loadPacking.setLp_bc_count(Long.parseLong(formatter.formatCellValue(row.getCell(8))));
            loadPacking.setLp_bc_unitprice(convertExelStringToDouble(formatter.formatCellValue(row.getCell(9))));
            loadPacking.setLp_totalcost(convertExelStringToDouble(formatter.formatCellValue(row.getCell(10))));
            loadPacking.setLp_bc_mark(Long.parseLong(formatter.formatCellValue(row.getCell(11))));
            loadPacking.setLp_box_inshipment(formatter.formatCellValue(row.getCell(12)));
            loadPacking.setLp_box_carplate(formatter.formatCellValue(row.getCell(13)));
            loadPacking.setLp_fpg_name(formatter.formatCellValue(row.getCell(14)));
            loadPacking.setLp_ftpm_name(formatter.formatCellValue(row.getCell(15)));
            loadPacking.setLp_fit_name(formatter.formatCellValue(row.getCell(16)));

            loadPackingRepository.save(loadPacking);

            saveatomlog(FileLogStatusEnum.SUCCESS,mapper.writeValueAsString(loadPacking),null);
            result = true;
        } catch (JpaSystemException e) {
            saveatomlog(FileLogStatusEnum.ERROR,mapper.writeValueAsString(loadPacking), String.format("Номер строки: %d%n Ошибка: %s", row.getRowNum()+1, e.getMostSpecificCause().getLocalizedMessage()));
            result=false;
        }  catch (Exception e) {
            saveatomlog(FileLogStatusEnum.ERROR,mapper.writeValueAsString(loadPacking),String.format("Номер строки: %d%n Ошибка: %s", row.getRowNum()+1, e.getLocalizedMessage()));
            result=false;
        }
        return result;
    }

    @Override
    public boolean validatefileformat(byte[] body) {
        boolean result;
        //проверяем формат файла
        try (InputStream inputStream = new ByteArrayInputStream(fhlb.getFhlb_Body()); HSSFWorkbook wb = new HSSFWorkbook(inputStream)) {
            result=true;
        }catch (Exception e){
            result=false;
        }
        return result;
    }
}
