package ua.com.idltd.hydracargo.contragent.rateproductgroup.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.contragent.rateproductgroup.repository.ContragentRateProductGroupRepository;
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

    private final ContragentRateProductGroupRepository contragentRateProductGroupRepository;

    public ContragnetRateProductGroupController(ContragentRateProductGroupRepository contragentRateProductGroupRepository) {
        this.contragentRateProductGroupRepository = contragentRateProductGroupRepository;
    }

    @PostMapping("/gettable")
    public JSONDatatable gettable(@RequestParam(name = "cnt_id", required = false) Long cnt_id) {
        JSONDatatable result = new JSONDatatable();
        if (cnt_id!=null) result.setData(contragentRateProductGroupRepository.getRateByContragent(GetUserName(),cnt_id));
        return result;
    }

}
