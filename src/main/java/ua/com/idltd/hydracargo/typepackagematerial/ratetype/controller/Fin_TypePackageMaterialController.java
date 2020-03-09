package ua.com.idltd.hydracargo.typepackagematerial.ratetype.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.typepackagematerial.ratetype.repository.Fin_TypePackageMaterialRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.Collections;

import static ua.com.idltd.hydracargo.utils.StaticUtils.ConvertTraceExceptionToText;


@RestController
@RequestMapping("/typepackagematerial")
public class Fin_TypePackageMaterialController {

    private final Fin_TypePackageMaterialRepository fin_typePackageMaterialRepository;

    public Fin_TypePackageMaterialController(Fin_TypePackageMaterialRepository fin_typePackageMaterialRepository) {
        this.fin_typePackageMaterialRepository = fin_typePackageMaterialRepository;
    }

    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(
    ){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/typepackagematerial/cover");
        return mav;
    }

    @PostMapping("/gettable")
    public JSONDatatable gettable(@RequestParam(name = "ftpm_id", required = false) Long ftpm_id) {
        JSONDatatable result = new JSONDatatable();
        if (ftpm_id != null) {
            result.setData(Collections.singleton(fin_typePackageMaterialRepository.findById(ftpm_id)));
        }else {
            result.setData(fin_typePackageMaterialRepository.findAll());
        }
        return result;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/add_typepackagematerial")
    public ResponseEntity<?> add_typepackagematerial(
            @RequestParam(name = "ftpm_name") String ftpm_name,
            @RequestParam(name = "ftpm_price") Double ftpm_price,
            @RequestParam(name = "ftpm_price_brand") Double ftpm_price_brand
    ) {
        ResponseEntity<?> result;
        try{
            StoredProcedureQuery AddQuery = entityManager
                    .createStoredProcedureQuery("PKG_TYPEPACKAGEMATERIAL.pr_Add")
                    .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, Long.class, ParameterMode.OUT)
                    .setParameter(1, ftpm_name)
                    .setParameter(2, ftpm_price)
                    .setParameter(3, ftpm_price_brand)
                    ;
            AddQuery.execute();
            result = ResponseEntity.ok(AddQuery.getOutputParameterValue(4));
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/edit_typepackagematerial")
    public ResponseEntity<?> edit_typepackagematerial(
            @RequestParam(name = "ftpm_id") Long ftpm_id,
            @RequestParam(name = "ftpm_name") String ftpm_name,
            @RequestParam(name = "ftpm_price") Double ftpm_price,
            @RequestParam(name = "ftpm_price_brand") Double ftpm_price_brand
    ) {
        ResponseEntity<?> result;
        try{
            StoredProcedureQuery EditProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_TYPEPACKAGEMATERIAL.pr_Edit")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, Double.class, ParameterMode.IN)
                    .setParameter(1, ftpm_id)
                    .setParameter(2, ftpm_name)
                    .setParameter(3, ftpm_price)
                    .setParameter(4, ftpm_price_brand)
                    ;
            EditProductQuery.execute();
            result = ResponseEntity.ok(ftpm_id);
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/del_typepackagematerial")
    public ResponseEntity<?> del_ratetype(
            @RequestParam(name = "ftpm_id") Long ftpm_id
    ) {
        ResponseEntity<?> result;
        try{
            StoredProcedureQuery DelProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_TYPEPACKAGEMATERIAL.pr_Del")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .setParameter(1, ftpm_id)
                    ;
            DelProductQuery.execute();
            result = ResponseEntity.ok(ftpm_id);
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
