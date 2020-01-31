package ua.com.idltd.hydracargo.contragent.ratepackagematerial.rateproductgroup.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.contragent.ratepackagematerial.rateproductgroup.repository.ContragentRatePackageMaterialRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;


@RestController
@RequestMapping("/ratepackagematerial")
public class ContragnetRatePackageMaterialController {

    @PersistenceContext
    private EntityManager entityManager;

    private final ContragentRatePackageMaterialRepository contragentRatePackageMaterialRepository;

    public ContragnetRatePackageMaterialController(ContragentRatePackageMaterialRepository contragentRatePackageMaterialRepository) {
        this.contragentRatePackageMaterialRepository = contragentRatePackageMaterialRepository;
    }

    @PostMapping("/gettable")
    public JSONDatatable gettable(@RequestParam(name = "cnt_id", required = false) Long cnt_id) {
        JSONDatatable result = new JSONDatatable();
        if (cnt_id!=null) result.setData(contragentRatePackageMaterialRepository.getRateByContragent(GetUserName(),cnt_id));
        return result;
    }

    @PostMapping("/add_contragentratepm")
    public ModelAndView add_contragentratepm(
            @RequestParam(name = "cnt_id") Long cnt_id,
            @RequestParam(name = "frt_id") Long frt_id,
            @RequestParam(name = "ftpm_id") Long ftpm_id,
            @RequestParam(name = "ftpm_price") Double ftpm_price,
            @RequestParam(name = "ftpm_price_brand") Double ftpm_price_brand
    ) {
        try{
            StoredProcedureQuery AddProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_CONTRAGENTRATEPACKAGEMATERIAL.pr_add")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(5, Double.class, ParameterMode.IN)
                    .setParameter(1, cnt_id)
                    .setParameter(2, frt_id)
                    .setParameter(3, ftpm_id)
                    .setParameter(4, ftpm_price)
                    .setParameter(5, ftpm_price_brand)
                    ;
            AddProductQuery.execute();
        }
        catch (Exception e) {
            System.out.println("Error:> "+e);
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/edit_contragentratepm")
    public ModelAndView edit_contragentratepm(
            @RequestParam(name = "fcrm_id") Long fcrm_id,
            @RequestParam(name = "ftpm_price") Double ftpm_price,
            @RequestParam(name = "ftpm_price_brand") Double ftpm_price_brand
    ) {
        try{
            StoredProcedureQuery EditProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_CONTRAGENTRATEPACKAGEMATERIAL.pr_edit")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Double.class, ParameterMode.IN)
                    .setParameter(1, fcrm_id)
                    .setParameter(2, ftpm_price)
                    .setParameter(3, ftpm_price_brand)
                    ;
            EditProductQuery.execute();
        }
        catch (Exception e) {
            System.out.println("Error:> "+e);
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/del_contragentratepm")
    public ModelAndView del_contragentratepm(
            @RequestParam(name = "fcrm_id") Long fcrm_id
    ) {
        try{
            StoredProcedureQuery DelProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_CONTRAGENTRATEPACKAGEMATERIAL.pr_del")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .setParameter(1, fcrm_id)
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
