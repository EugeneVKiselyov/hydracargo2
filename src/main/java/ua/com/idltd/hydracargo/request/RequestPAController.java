package ua.com.idltd.hydracargo.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.business.entity.Business;
import ua.com.idltd.hydracargo.business.repository.BusinessRepository;
import ua.com.idltd.hydracargo.contragent.contragentdefault.entity.ContragentDefault;
import ua.com.idltd.hydracargo.contragent.contragentdefault.repository.ContragentDefaultRepository;
import ua.com.idltd.hydracargo.contragent.entity.Contragent;
import ua.com.idltd.hydracargo.contragent.repository.ContragentRepository;
import ua.com.idltd.hydracargo.dispatch.entity.Dispatch;
import ua.com.idltd.hydracargo.dispatch.repository.DispatchRepository;
import ua.com.idltd.hydracargo.entrepot.entity.Entrepot;
import ua.com.idltd.hydracargo.entrepot.repository.EntrepotRepository;
import ua.com.idltd.hydracargo.insurancetype.entity.Fin_Insurance_Type;
import ua.com.idltd.hydracargo.insurancetype.repository.Fin_Insurance_TypeRepository;
import ua.com.idltd.hydracargo.productgroup.entity.Fin_ProductGroup;
import ua.com.idltd.hydracargo.productgroup.repository.Fin_ProductGroupRepository;
import ua.com.idltd.hydracargo.request.entity.Request;
import ua.com.idltd.hydracargo.request.repository.VRequestRepository;
import ua.com.idltd.hydracargo.request_status.entity.Request_status;
import ua.com.idltd.hydracargo.request_status.repository.Request_statusRepository;
import ua.com.idltd.hydracargo.typepackagematerial.ratetype.entity.Fin_TypePackageMaterial;
import ua.com.idltd.hydracargo.typepackagematerial.ratetype.repository.Fin_TypePackageMaterialRepository;
import ua.com.idltd.hydracargo.user.repository.UsersRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;
import ua.com.idltd.hydracargo.utils.filehandler.FileUploadResult;
import ua.com.idltd.hydracargo.utils.filehandler.FileUploadService;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.Date;

import static ua.com.idltd.hydracargo.utils.StaticUtils.ConvertTraceExceptionToText;
import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;
import static ua.com.idltd.hydracargo.utils.filehandler.handler.FileTypeEnum.PACKING_LIST;
import static ua.com.idltd.hydracargo.utils.filehandler.handler.FileTypeEnum.PACKING_LIST_SMALL;


@RestController
@RequestMapping("/request_pa")
public class RequestPAController {

    @Autowired
    private FileUploadService fileUploadService;

    @PersistenceContext
    private EntityManager entityManager;
    private RequestFilter currentFilter;

    private final VRequestRepository requestRepository;
    private final ContragentRepository contragentRepository;
    private final BusinessRepository businessRepository;
    private final Request_statusRepository request_statusRepository;
    private final EntrepotRepository entrepotRepository;
    private final Fin_ProductGroupRepository fin_productGroupRepository;
    private final Fin_TypePackageMaterialRepository fin_typePackageMaterialRepository;
    private final Fin_Insurance_TypeRepository fin_insurance_typeRepository;
    private final DispatchRepository dispatchRepository;
    private final ContragentDefaultRepository contragentDefaultRepository;

    public RequestPAController(VRequestRepository requestRepository, ContragentRepository contragentRepository, BusinessRepository businessRepository, Request_statusRepository request_statusRepository, EntrepotRepository entrepotRepository, Fin_ProductGroupRepository fin_productGroupRepository, Fin_TypePackageMaterialRepository fin_typePackageMaterialRepository, Fin_Insurance_TypeRepository fin_insurance_typeRepository, DispatchRepository dispatchRepository, ContragentDefaultRepository contragentDefaultRepository) {
        this.requestRepository = requestRepository;
        this.contragentRepository = contragentRepository;
        this.businessRepository = businessRepository;
        this.request_statusRepository = request_statusRepository;
        this.entrepotRepository = entrepotRepository;
        this.fin_productGroupRepository = fin_productGroupRepository;
        this.fin_typePackageMaterialRepository = fin_typePackageMaterialRepository;
        this.fin_insurance_typeRepository = fin_insurance_typeRepository;
        this.dispatchRepository = dispatchRepository;
        this.contragentDefaultRepository = contragentDefaultRepository;
        this.currentFilter=new RequestFilter();
    }


    @PostMapping("/set_filter")
    public ResponseEntity<?> set_filter(final RequestFilter filter
    ) {
        ResponseEntity<?> result;
        try{
            currentFilter.setCnt_id(filter.getCnt_id());
            currentFilter.setEp_id(filter.getEp_id());
            result = ResponseEntity.ok(currentFilter);
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(ModelAndView mav, final RequestFilter filter
    ){
        mav.addObject("filter",filter);

        Iterable<Contragent> contragentList;
        contragentList =  contragentRepository.getAllByUser(GetUserName());
        mav.addObject("contragentList", contragentList);

        Iterable<Business> businessList;
        businessList =  businessRepository.findAll();
        mav.addObject("businessList", businessList);

        Iterable<Request_status> request_statusList;
        request_statusList =  request_statusRepository.findAll();
        mav.addObject("request_statusList", request_statusList);

        Iterable<Entrepot> entrepotList;
        entrepotList =  entrepotRepository.findAll();
        mav.addObject("entrepotList", entrepotList);

        //Для Box
        Iterable<Fin_ProductGroup> productGroupList;
        productGroupList =  fin_productGroupRepository.findAll();
        mav.addObject("productGroupList", productGroupList);

        Iterable<Fin_TypePackageMaterial> typePackageMaterialList;
        typePackageMaterialList =  fin_typePackageMaterialRepository.findAll();
        mav.addObject("typePackageMaterialList", typePackageMaterialList);

        Iterable<Fin_Insurance_Type> insurance_TypeList;
        insurance_TypeList =  fin_insurance_typeRepository.findAll();
        mav.addObject("insurance_TypeList", insurance_TypeList);

        Iterable<Dispatch> dispatchList;
        dispatchList =  dispatchRepository.findAll();
        mav.addObject("dispatchList", dispatchList);

        mav.setViewName("/request_pa/cover");
        return mav;
    }

    private int getfilter_num(Long req_id,Long cnt_id,Long ep_id){
       int result=0;
       if (req_id!=null) result=result+1;
       if (cnt_id!=null) result=result+10;
       if (ep_id!=null) result=result+100;
       return result;
    }
    @PostMapping("/gettable")
    public JSONDatatable gettable(@RequestParam(name = "req_id", required = false) Long req_id) {
        JSONDatatable result = new JSONDatatable();
        int i = getfilter_num(req_id,currentFilter.getCnt_id(),currentFilter.getEp_id());
        switch (i) {
            case 0 : result.setData(requestRepository.getAllByUser(GetUserName())); break;
            case 1 : result.setData(requestRepository.getByReq_idandUser(GetUserName(),req_id)); break;
            case 10 : result.setData(requestRepository.getByCnt_idandUser(GetUserName(),currentFilter.getCnt_id())); break;
            case 100 : result.setData(requestRepository.getByEp_idandUser(GetUserName(),currentFilter.getEp_id())); break;
            case 11 : result.setData(requestRepository.getByReq_idandCnt_idandUser(GetUserName(),req_id,currentFilter.getCnt_id())); break;
            case 101 : result.setData(requestRepository.getByReq_idandEp_idandUser(GetUserName(),req_id,currentFilter.getEp_id())); break;
            case 110 : result.setData(requestRepository.getByCnt_idandEp_idandUser(GetUserName(),currentFilter.getCnt_id(),currentFilter.getEp_id())); break;
            case 111 : result.setData(requestRepository.getByReq_idandCnt_idandEp_idandUser(GetUserName(),req_id,currentFilter.getCnt_id(),currentFilter.getEp_id())); break;
        }

        return result;
    }
    @PostMapping("/get_request_default")
    public ResponseEntity<?> get_box_default(
    ) {
        Request request = new Request();
        request.req_num = (String) entityManager
                .createNativeQuery(
                        "SELECT pkg_request.getNext_Request_num() FROM DUAL"
                )
                .getSingleResult();
        ContragentDefault contragentDefault = contragentDefaultRepository.getDefaultByUsername(GetUserName()).orElse(new ContragentDefault());
        request.cnt_id = contragentDefault.cnt_id;
        request.ep_id = contragentDefault.cntd_ep_source;
        request.ep_id_dest = contragentDefault.cntd_ep_dest;
        request.bs_id = contragentDefault.cntd_bs_id;
        request.rs_id = 0L;
        request.req_weight_p = new Double(0);
        request.req_weight_f = new Double(0);
        return ResponseEntity.ok(request);
    }

    @PostMapping("/import")
    public ResponseEntity<?> import_request(
            @RequestParam(name = "req_id") Long req_id,
            @RequestParam(name = "imp_type") Long imp_type,
            @RequestParam("file") MultipartFile file
    ) {
        ResponseEntity<?> result;
        try{
            FileUploadResult ufr;
            switch (imp_type.intValue()) {
                case 1:
                    ufr = fileUploadService.upload(req_id, PACKING_LIST, file); break;
                case 2:
                    ufr = fileUploadService.upload(req_id, PACKING_LIST_SMALL, file); break;
            }
            result = ResponseEntity.ok(req_id);
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
