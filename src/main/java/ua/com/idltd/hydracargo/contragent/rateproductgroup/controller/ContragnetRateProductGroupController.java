package ua.com.idltd.hydracargo.contragent.rateproductgroup.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.contragent.rateproductgroup.repository.ContragentRateProductGroupRepository;
import ua.com.idltd.hydracargo.contragent.rateproductgroup.repository.VContragentRateProductGroupRepository;
import ua.com.idltd.hydracargo.contragent.repository.ContragentRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.Collections;

import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;


@RestController
@RequestMapping("/rateproductgroup")
public class ContragnetRateProductGroupController {

    @PersistenceContext
    private EntityManager entityManager;

    private final VContragentRateProductGroupRepository vContragentRateProductGroupRepository;

    public ContragnetRateProductGroupController(VContragentRateProductGroupRepository vContragentRateProductGroupRepository) {
        this.vContragentRateProductGroupRepository = vContragentRateProductGroupRepository;
    }

    @PostMapping("/gettable")
    public JSONDatatable gettable(@RequestParam(name = "cnt_id", required = false) Long cnt_id) {
        JSONDatatable result = new JSONDatatable();
        if (cnt_id!=null) result.setData(vContragentRateProductGroupRepository.getRateByContragent(GetUserName(),cnt_id));
        return result;
    }

    @PostMapping("/add_contragentratepg")
    public ModelAndView add_contragentratepg(
            @RequestParam(name = "cnt_id") Long cnt_id,
            @RequestParam(name = "frt_id") Long frt_id,
            @RequestParam(name = "fpg_id") Long fpg_id,
            @RequestParam(name = "fcr_price") Double fcr_price,
            @RequestParam(name = "fcr_price_brand") Double fcr_price_brand
    ) {
        try{
            StoredProcedureQuery AddProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_CONTRAGENTPRODUCTGROUP.pr_add")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(5, Double.class, ParameterMode.IN)
                    .setParameter(1, cnt_id)
                    .setParameter(2, frt_id)
                    .setParameter(3, fpg_id)
                    .setParameter(4, fcr_price)
                    .setParameter(5, fcr_price_brand)
                    ;
            AddProductQuery.execute();
        }
        catch (Exception e) {
            System.out.println("Error:> "+e);
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/edit_contragentratepg")
    public ModelAndView edit_contragent(
            @RequestParam(name = "fcr_id") Long fcr_id,
            @RequestParam(name = "fcr_price") Double fcr_price,
            @RequestParam(name = "fcr_price_brand") Double fcr_price_brand
    ) {
        try{
            StoredProcedureQuery EditProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_CONTRAGENTPRODUCTGROUP.pr_edit")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Double.class, ParameterMode.IN)
                    .setParameter(1, fcr_id)
                    .setParameter(2, fcr_price)
                    .setParameter(3, fcr_price_brand)
                    ;
            EditProductQuery.execute();
        }
        catch (Exception e) {
            System.out.println("Error:> "+e);
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/del_contragentratepg")
    public ModelAndView del_contragent(
            @RequestParam(name = "fcr_id") Long fcr_id
    ) {
        try{
            StoredProcedureQuery DelProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_CONTRAGENTPRODUCTGROUP.pr_del")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .setParameter(1, fcr_id)
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
