package ua.com.idltd.hydracargo.productgroup.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.productgroup.repository.Fin_ProductGroupRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/productgroup")
public class ProductGroupController {

    private final Fin_ProductGroupRepository fin_productGroupRepository;

    public ProductGroupController(Fin_ProductGroupRepository fin_productGroupRepository) {
        this.fin_productGroupRepository = fin_productGroupRepository;
    }

    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(
    ){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/productgroup/cover");
        return mav;
    }

    @PostMapping("/gettable")
    public JSONDatatable gettable(@RequestParam(name = "fpg_id", required = false) Long fpg_id) {
        JSONDatatable result = new JSONDatatable();
        if (fpg_id != null) {
            result.setData(Collections.singleton(fin_productGroupRepository.findById(fpg_id)));
        }else {
            result.setData(fin_productGroupRepository.findAll());
        }
        return result;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/add_productgroup")
    public ModelAndView add_productgroup(
            @RequestParam(name = "fpg_name", required = false) String fpg_name
    ) {
        try{
            StoredProcedureQuery AddProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_ADMIN.pr_AddProductGroup")
                    .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                    .setParameter(1, fpg_name)
                    ;
            AddProductQuery.execute();
        }
        catch (Exception e) {
            System.out.println("Error:> "+e);
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/edit_productgroup")
    public ModelAndView edit_productgroup(
            @RequestParam(name = "fpg_id", required = false) Long fpg_id,
            @RequestParam(name = "fpg_name", required = false) String fpg_name
    ) {
        try{
            StoredProcedureQuery EditProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_ADMIN.pr_EditProductGroup")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                    .setParameter(1, fpg_id)
                    .setParameter(2, fpg_name)
                    ;
            EditProductQuery.execute();
        }
        catch (Exception e) {
            System.out.println("Error:> "+e);
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/del_productgroup")
    public ModelAndView del_productgroup(
            @RequestParam(name = "fpg_id", required = false) Long fpg_id
    ) {
        try{
            StoredProcedureQuery DelProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_ADMIN.pr_DelProductGroup")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .setParameter(1, fpg_id)
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
