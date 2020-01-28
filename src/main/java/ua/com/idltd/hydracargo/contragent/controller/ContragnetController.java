package ua.com.idltd.hydracargo.contragent.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.contragent.repository.ContragentRepository;
import ua.com.idltd.hydracargo.ratetype.repository.Fin_RateTypeRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.Collections;

import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;


@RestController
@RequestMapping("/contragent")
public class ContragnetController {

    private final ContragentRepository contragentRepository;

    public ContragnetController(ContragentRepository contragentRepository) {
        this.contragentRepository = contragentRepository;
    }

    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(
    ){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/contragent/cover");
        return mav;
    }

    @PostMapping("/gettable")
    public JSONDatatable gettable(@RequestParam(name = "cnt_id", required = false) Long cnt_id) {
        JSONDatatable result = new JSONDatatable();
        if (cnt_id != null) {
            result.setData(contragentRepository.getByCnt_idandUser(GetUserName(),cnt_id));
        }else {
            result.setData(contragentRepository.getAllByUser(GetUserName()));
        }
        return result;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/add_contragent")
    public ModelAndView add_contragent(
            @RequestParam(name = "cnt_name", required = false) String cnt_name
    ) {
        try{
            StoredProcedureQuery AddProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_ADMIN.pr_AddContragent")
                    .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                    .setParameter(1, cnt_name)
                    ;
            AddProductQuery.execute();
        }
        catch (Exception e) {
            System.out.println("Error:> "+e);
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/edit_contragent")
    public ModelAndView edit_contragent(
            @RequestParam(name = "cnt_id", required = false) Long cnt_id,
            @RequestParam(name = "cnt_name", required = false) String cnt_name
    ) {
        try{
            StoredProcedureQuery EditProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_ADMIN.pr_EditContragent")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                    .setParameter(1, cnt_id)
                    .setParameter(2, cnt_name)
                    ;
            EditProductQuery.execute();
        }
        catch (Exception e) {
            System.out.println("Error:> "+e);
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/del_contragent")
    public ModelAndView del_contragent(
            @RequestParam(name = "cnt_id", required = false) Long cnt_id
    ) {
        try{
            StoredProcedureQuery DelProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_ADMIN.pr_DelContragent")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .setParameter(1, cnt_id)
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
