package ua.com.idltd.hydracargo.ratetype.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.ratetype.repository.Fin_RateTypeRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.Collections;


@RestController
@RequestMapping("/ratetype")
public class RateTypeController {

    private final Fin_RateTypeRepository fin_rateTypeRepository;

    public RateTypeController(Fin_RateTypeRepository fin_rateTypeRepository) {
        this.fin_rateTypeRepository = fin_rateTypeRepository;
    }

    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(
    ){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/ratetype/cover");
        return mav;
    }

    @PostMapping("/gettable")
    public JSONDatatable gettable(@RequestParam(name = "frt_id", required = false) Long frt_id) {
        JSONDatatable result = new JSONDatatable();
        if (frt_id != null) {
            result.setData(Collections.singleton(fin_rateTypeRepository.findById(frt_id)));
        }else {
            result.setData(fin_rateTypeRepository.findAll());
        }
        return result;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/add_ratetype")
    public ModelAndView add_ratetype(
            @RequestParam(name = "frt_name", required = false) String frt_name
    ) {
        try{
            StoredProcedureQuery AddProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_ADMIN.pr_AddRatetype")
                    .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                    .setParameter(1, frt_name)
                    ;
            AddProductQuery.execute();
        }
        catch (Exception e) {
            System.out.println("Error:> "+e);
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/edit_ratetype")
    public ModelAndView edit_ratetype(
            @RequestParam(name = "frt_id", required = false) Long frt_id,
            @RequestParam(name = "frt_name", required = false) String frt_name
    ) {
        try{
            StoredProcedureQuery EditProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_ADMIN.pr_EditRateType")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                    .setParameter(1, frt_id)
                    .setParameter(2, frt_name)
                    ;
            EditProductQuery.execute();
        }
        catch (Exception e) {
            System.out.println("Error:> "+e);
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/del_ratetype")
    public ModelAndView del_ratetype(
            @RequestParam(name = "frt_id", required = false) Long frt_id
    ) {
        try{
            StoredProcedureQuery DelProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_ADMIN.pr_DelRateType")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .setParameter(1, frt_id)
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
