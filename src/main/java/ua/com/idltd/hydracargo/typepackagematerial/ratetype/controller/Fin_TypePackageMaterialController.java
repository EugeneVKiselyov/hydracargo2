package ua.com.idltd.hydracargo.typepackagematerial.ratetype.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.typepackagematerial.ratetype.repository.Fin_TypePackageMaterialRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.Collections;


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
    public ModelAndView add_typepackagematerial(
            @RequestParam(name = "ftpm_name") String ftpm_name
    ) {
        try{
            StoredProcedureQuery AddProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_TYPEPACKAGEMATERIAL.pr_Add")
                    .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                    .setParameter(1, ftpm_name)
                    ;
            AddProductQuery.execute();
        }
        catch (Exception e) {
            System.out.println("Error:> "+e);
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/edit_typepackagematerial")
    public ModelAndView edit_typepackagematerial(
            @RequestParam(name = "ftpm_id") Long ftpm_id,
            @RequestParam(name = "ftpm_name") String ftpm_name
    ) {
        try{
            StoredProcedureQuery EditProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_TYPEPACKAGEMATERIAL.pr_Edit")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                    .setParameter(1, ftpm_id)
                    .setParameter(2, ftpm_name)
                    ;
            EditProductQuery.execute();
        }
        catch (Exception e) {
            System.out.println("Error:> "+e);
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/del_typepackagematerial")
    public ModelAndView del_ratetype(
            @RequestParam(name = "ftpm_id") Long ftpm_id
    ) {
        try{
            StoredProcedureQuery DelProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_TYPEPACKAGEMATERIAL.pr_Del")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .setParameter(1, ftpm_id)
                    ;
            DelProductQuery.execute();
        }
        catch (Exception e) {
            System.out.println("Error:> "+e);
            e.printStackTrace();
        }
        return null;
    }
}
