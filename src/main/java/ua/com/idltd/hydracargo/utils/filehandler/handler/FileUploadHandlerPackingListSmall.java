package ua.com.idltd.hydracargo.utils.filehandler.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.multipart.MultipartFile;
import ua.com.idltd.hydracargo.contragent.contragentdefault.entity.ContragentDefault;
import ua.com.idltd.hydracargo.contragent.contragentdefault.repository.ContragentDefaultRepository;
import ua.com.idltd.hydracargo.declaration_cache.entity.Declaration_cache;
import ua.com.idltd.hydracargo.dispatch.entity.Dispatch;
import ua.com.idltd.hydracargo.dispatch.repository.DispatchRepository;
import ua.com.idltd.hydracargo.exception.DispatchIdNullException;
import ua.com.idltd.hydracargo.insurancetype.entity.Fin_Insurance_Type;
import ua.com.idltd.hydracargo.insurancetype.repository.Fin_Insurance_TypeRepository;
import ua.com.idltd.hydracargo.productgroup.entity.Fin_ProductGroup;
import ua.com.idltd.hydracargo.productgroup.repository.Fin_ProductGroupRepository;
import ua.com.idltd.hydracargo.typepackagematerial.ratetype.entity.Fin_TypePackageMaterial;
import ua.com.idltd.hydracargo.typepackagematerial.ratetype.repository.Fin_TypePackageMaterialRepository;
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
import java.util.Date;
import java.util.Iterator;

import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;
import static ua.com.idltd.hydracargo.utils.filehandler.handler.FileTypeEnum.PACKING_LIST_SMALL;

public class FileUploadHandlerPackingListSmall extends IFileUploadHandlerImpl {
    private final EntityManager entityManager;
    private final Long lt_part;
    private final Long req_id;

    private final LoadPackingRepository loadPackingRepository;
    private final ContragentDefaultRepository contragentDefaultRepository;
    private final Fin_ProductGroupRepository fin_productGroupRepository;
    private final Fin_TypePackageMaterialRepository fin_typePackageMaterialRepository;
    private final Fin_Insurance_TypeRepository fin_insurance_typeRepository;
    private final DispatchRepository dispatchRepository;

    private ContragentDefault contragentDefault;

    public FileUploadHandlerPackingListSmall(FileTypeEnum fte, FilehandlerLog fhl, MultipartFile file, FileHandlerBufferRepository fileHandlerBufferRepository, FileHandlerLogRepository fileHandlerLogRepository, FileHandlerDetailLogRepository fileHandlerDetailLogRepository, FileHandlerAtomLogRepository fileHandlerAtomLogRepository,
                                             EntityManager entityManager, Long req_id, LoadPackingRepository loadPackingRepository, ContragentDefaultRepository contragentDefaultRepository, Fin_ProductGroupRepository fin_productGroupRepository, Fin_TypePackageMaterialRepository fin_typePackageMaterialRepository, Fin_Insurance_TypeRepository fin_insurance_typeRepository, DispatchRepository dispatchRepository) throws UnsupportedFileFormatException, IOException, UnsupportedFileTypeException {
        super(fte, fhl, file, fileHandlerBufferRepository, fileHandlerLogRepository, fileHandlerDetailLogRepository, fileHandlerAtomLogRepository);
        this.req_id = req_id;
        this.loadPackingRepository = loadPackingRepository;
        this.contragentDefaultRepository = contragentDefaultRepository;
        this.fin_productGroupRepository = fin_productGroupRepository;
        this.fin_typePackageMaterialRepository = fin_typePackageMaterialRepository;
        this.fin_insurance_typeRepository = fin_insurance_typeRepository;
        this.dispatchRepository = dispatchRepository;
        if (fte!=PACKING_LIST_SMALL) throw new UnsupportedFileTypeException(fte);

        this.entityManager = entityManager;
        this.lt_part = ((BigDecimal) this.entityManager
                .createNativeQuery(
                        "select LOAD_PACKING_PART_SEQ.nextval from dual"
                )
                .getSingleResult()).longValue();
        contragentDefault = contragentDefaultRepository.getDefaultByUsername(GetUserName());
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
            //создаем посылки
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

        String s;
        try {
            loadPacking.setLp_box_num(formatter.formatCellValue(row.getCell(0)));
            loadPacking.setLp_box_description(formatter.formatCellValue(row.getCell(1)));
            loadPacking.setLp_box_weight(convertExelStringToDouble(formatter.formatCellValue(row.getCell(2))));
            loadPacking.setLp_bc_description(formatter.formatCellValue(row.getCell(3)));
            loadPacking.setLp_bc_count(Long.parseLong(formatter.formatCellValue(row.getCell(4))));
            loadPacking.setLp_bc_unitprice(convertExelStringToDouble(formatter.formatCellValue(row.getCell(5))));

            s=formatter.formatCellValue(row.getCell(6)); if (s.equalsIgnoreCase("")) s="0";
            loadPacking.setLp_bc_mark(Long.parseLong(s));

            loadPacking.setLp_fpg_name(formatter.formatCellValue(row.getCell(7)));
            loadPacking.setLp_ftpm_name(formatter.formatCellValue(row.getCell(8)));
            loadPacking.setLp_fit_name(formatter.formatCellValue(row.getCell(9)));
            loadPacking.setLp_box_lenght(convertExelStringToDouble(formatter.formatCellValue(row.getCell(10))));
            loadPacking.setLp_box_height(convertExelStringToDouble(formatter.formatCellValue(row.getCell(11))));
            loadPacking.setLp_box_width(convertExelStringToDouble(formatter.formatCellValue(row.getCell(12))));

            //проверяем на заполненность полей если каких то не хватает то берем изнастроек клиента
            if (loadPacking.getLp_box_num()==null) throw new Exception("Номер коробки пустой");
            if (loadPacking.getLp_box_description()==null) throw new Exception("Номер общее описание вложения пустое");
            if (loadPacking.getLp_box_weight()==null) throw new Exception("Вес пустой");
            if (loadPacking.getLp_bc_description()==null) throw new Exception("Номер описание вложения пустое");
            if (loadPacking.getLp_bc_count()==null) throw new Exception("Поле количество пустое");
            if (loadPacking.getLp_bc_unitprice()==null) throw new Exception("Поле цена за единицу пустое");

            if (loadPacking.getLp_bc_mark()==null) loadPacking.setLp_bc_mark(0L);

            if (contragentDefault != null) {
                if (loadPacking.getLp_fpg_name().equalsIgnoreCase("")) {
                    Fin_ProductGroup fin_productGroup=fin_productGroupRepository.findById(contragentDefault.cntd_fpg_id).orElse(new Fin_ProductGroup());
                    loadPacking.setLp_fpg_name(fin_productGroup.getFpg_name());
                }
                if (loadPacking.getLp_ftpm_name().equalsIgnoreCase("")) {
                   Fin_TypePackageMaterial fin_typePackageMaterial =fin_typePackageMaterialRepository.findById(contragentDefault.cntd_ftpm_id).orElse(new Fin_TypePackageMaterial());
                    loadPacking.setLp_ftpm_name(fin_typePackageMaterial.getFtpm_name());
                }
                if (loadPacking.getLp_fit_name().equalsIgnoreCase("")) {
                    Fin_Insurance_Type fin_insurance_type =fin_insurance_typeRepository.findById(contragentDefault.cntd_fit_id).orElse(new Fin_Insurance_Type());
                    loadPacking.setLp_fit_name(fin_insurance_type.getFit_name());
                }
                if (loadPacking.getLp_box_lenght()==null) {
                    loadPacking.setLp_box_lenght(contragentDefault.cntd_box_lenght);
                }
                if (loadPacking.getLp_box_width()==null) {
                    loadPacking.setLp_box_width(contragentDefault.cntd_box_width);
                }
                if (loadPacking.getLp_box_height()==null) {
                    loadPacking.setLp_box_height(contragentDefault.cntd_box_height);
                }
            }
            if (loadPacking.getLp_bc_unitprice()==null) throw new Exception("Поле цена за единицу пустое");


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
