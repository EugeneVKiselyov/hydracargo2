package ua.com.idltd.hydracargo.utils.filehandler.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.multipart.MultipartFile;
import ua.com.idltd.hydracargo.dispatch.entity.Dispatch;
import ua.com.idltd.hydracargo.dispatch.repository.DispatchRepository;
import ua.com.idltd.hydracargo.exception.DispatchIdNullException;
import ua.com.idltd.hydracargo.scan.entity.Declaration_scan;
import ua.com.idltd.hydracargo.scan.repository.Declaration_scanRepository;
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
import java.text.MessageFormat;
import java.util.Iterator;

import static ua.com.idltd.hydracargo.utils.StaticUtils.ConvertTraceExceptionToText;

//import org.apache.tika.Tika;

public class FileUploadHandlerVEX_SCAN extends IFileUploadHandlerPostImpl {

    private final DispatchRepository dispatchRepository;
    private final Declaration_scanRepository declaration_scanRepository;

    private final Dispatch dispatch;
    private final ObjectMapper mapper;

    public FileUploadHandlerVEX_SCAN(FilehandlerLog fhl, MultipartFile file, FileHandlerBufferRepository fileHandlerBufferRepository, FileHandlerLogRepository fileHandlerLogRepository, FileHandlerDetailLogRepository fileHandlerDetailLogRepository, FileHandlerAtomLogRepository fileHandlerAtomLogRepository,
                                     Long dis_id, DispatchRepository dispatchRepository, Declaration_scanRepository declaration_scanRepository) throws UnsupportedFileFormatException, IOException, DispatchIdNullException {
        super(FileTypeEnum.VEX, fhl,file, fileHandlerBufferRepository, fileHandlerLogRepository, fileHandlerDetailLogRepository,fileHandlerAtomLogRepository,
              dis_id, 0L,0L,"","");
        this.dispatchRepository=dispatchRepository;
        this.declaration_scanRepository = declaration_scanRepository;
        // find dispatch
        this.dispatch = dispatchRepository.findById(dis_id).orElse(null);
        if (this.dispatch == null) throw new DispatchIdNullException("Dispatch dis_id="+dis_id+" not found");
        mapper = new ObjectMapper();
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

    //Method to identify the color pattern
    private static String getColorHex(short colorIdx){
        short[] triplet = null;
        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFPalette palette = workbook.getCustomPalette();
        HSSFColor color = palette.getColor(colorIdx);
        triplet = color.getTriplet();
//        System.out.println("color : " + triplet[0] +"," + triplet[1] + "," +     triplet[2]);
        return String.format("#%02x%02x%02x", triplet[0], triplet[1], triplet[2]);
    }
    @Override
    protected boolean process(Row row) throws JsonProcessingException {
        boolean result = false;
        ObjectMapper mapper = new ObjectMapper();

        Declaration_scan declaration_scan = new Declaration_scan();
        try {
            declaration_scan.dis_id = dis_id;
            Cell cell = row.getCell(0);
            declaration_scan.ds_shipment = formatter.formatCellValue(cell);
            if (cell.getCellStyle().getFillPattern() != FillPatternType.NO_FILL) {
                Short cellColor = cell.getCellStyle().getFillForegroundColor();
                declaration_scan.ds_color = getColorHex(cellColor);
            }
            declaration_scanRepository.save(declaration_scan);

            saveatomlog(FileLogStatusEnum.SUCCESS,mapper.writeValueAsString(declaration_scan),null);
            result = true;
        } catch (JpaSystemException e) {
            saveatomlog(FileLogStatusEnum.ERROR,mapper.writeValueAsString(declaration_scan), String.format("Номер строки: %d%n Ошибка: %s", row.getRowNum()+1, e.getMostSpecificCause().getLocalizedMessage()));
            result=false;
        }  catch (Exception e) {
            saveatomlog(FileLogStatusEnum.ERROR,mapper.writeValueAsString(declaration_scan),String.format("Номер строки: %d%n Ошибка: %s", row.getRowNum()+1, e.getLocalizedMessage()));
            result=false;
        }
        return result;
    }
}
