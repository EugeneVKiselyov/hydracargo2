package ua.com.idltd.hydracargo.client_area.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.contragent.repository.ContragentRepository;
import ua.com.idltd.hydracargo.productgroup.entity.Fin_ProductGroup;
import ua.com.idltd.hydracargo.productgroup.repository.Fin_ProductGroupRepository;
import ua.com.idltd.hydracargo.typepackagematerial.ratetype.entity.Fin_TypePackageMaterial;
import ua.com.idltd.hydracargo.typepackagematerial.ratetype.repository.Fin_TypePackageMaterialRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;


@RestController
@RequestMapping("/client_area")
public class ClientAreaController {

    @PersistenceContext
    private EntityManager entityManager;

    private final ContragentRepository contragentRepository;
    private final Fin_ProductGroupRepository fin_productGroupRepository;
    private final Fin_TypePackageMaterialRepository fin_typePackageMaterialRepository;

    public ClientAreaController(
            ContragentRepository contragentRepository,
            Fin_ProductGroupRepository fin_productGroupRepository,
            Fin_TypePackageMaterialRepository fin_typePackageMaterialRepository
    ) {
        this.contragentRepository = contragentRepository;
        this.fin_productGroupRepository = fin_productGroupRepository;
        this.fin_typePackageMaterialRepository = fin_typePackageMaterialRepository;
    }

    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(
    ){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("/client_area/cover");
        return mav;
    }
}
