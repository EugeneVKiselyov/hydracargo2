package ua.com.idltd.hydracargo.dashboardrequest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.awb.entity.Awb;
import ua.com.idltd.hydracargo.awb.repository.AwbRepository;
import ua.com.idltd.hydracargo.business.entity.Business;
import ua.com.idltd.hydracargo.business.repository.BusinessRepository;
import ua.com.idltd.hydracargo.contragent.entity.Contragent;
import ua.com.idltd.hydracargo.contragent.repository.ContragentRepository;
import ua.com.idltd.hydracargo.country.entity.Country;
import ua.com.idltd.hydracargo.country.repository.CountryRepository;
import ua.com.idltd.hydracargo.dashboardrequest.dto.DashboardRequestDTO;
import ua.com.idltd.hydracargo.declaration_cache.repository.Declaration_cacheRepository;
import ua.com.idltd.hydracargo.dispatch.entity.Dispatch;
import ua.com.idltd.hydracargo.dispatch.repository.DispatchRepository;
import ua.com.idltd.hydracargo.dispatchrequestcontragentview.entity.DispatchRequestContragentView;
import ua.com.idltd.hydracargo.dispatchrequestcontragentview.repository.DispatchRequestContragentViewRepository;
import ua.com.idltd.hydracargo.entrepot.entity.Entrepot;
import ua.com.idltd.hydracargo.entrepot.repository.EntrepotRepository;
import ua.com.idltd.hydracargo.exception.DispatchIdNullException;
import ua.com.idltd.hydracargo.request.entity.Request;
import ua.com.idltd.hydracargo.request.repository.RequestRepository;
import ua.com.idltd.hydracargo.request_status.entity.Request_status;
import ua.com.idltd.hydracargo.request_status.repository.Request_statusRepository;
import ua.com.idltd.hydracargo.service.entity.Service;
import ua.com.idltd.hydracargo.service.repository.ServiceRepository;
import ua.com.idltd.hydracargo.type_export.entity.Type_export;
import ua.com.idltd.hydracargo.type_export.repository.Type_exportRepository;
import ua.com.idltd.hydracargo.user.entity.User;
import ua.com.idltd.hydracargo.user.repository.UsersRepository;
import ua.com.idltd.hydracargo.utils.StaticUtils;
import ua.com.idltd.hydracargo.utils.filehandler.FileUploadResult;
import ua.com.idltd.hydracargo.utils.filehandler.FileUploadService;
import ua.com.idltd.hydracargo.utils.filehandler.handler.FileTypeEnum;
import ua.com.idltd.hydracargo.utils.filehandler.repository.LoadAsosRepository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static ua.com.idltd.hydracargo.utils.StaticUtils.ConvertTraceExceptionToText;
import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;


@RestController
@RequestMapping("/dashboardrequest")
public class DashboardRequestController {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;
    @Autowired
    private FileUploadService fileUploadService;

    private final RequestRepository requestRepository;
    private final Request_statusRepository request_statusRepository;
    private final DispatchRepository dispatchRepository;
    private final DispatchRequestContragentViewRepository dispatchRequestContragentViewRepository;
    private final AwbRepository awbRepository;
    private final EntrepotRepository entrepotRepository;
    private final ContragentRepository contragentRepository;
    private final BusinessRepository businessRepository;
    private final CountryRepository countryRepository;
    private final ServiceRepository serviceRepository;
    private final Type_exportRepository type_exportRepository;
    private final Declaration_cacheRepository declaration_cacheRepository;
    private final UsersRepository usersRepository;
    private final LoadAsosRepository loadAsosRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public DashboardRequestController(RequestRepository requestRepository, Request_statusRepository request_statusRepository, DispatchRepository dispatchRepository, DispatchRequestContragentViewRepository dispatchRequestContragentViewRepository, AwbRepository awbRepository, EntrepotRepository entrepotRepository, ContragentRepository contragentRepository, BusinessRepository businessRepository, CountryRepository countryRepository, ServiceRepository serviceRepository, Type_exportRepository type_exportRepository, Declaration_cacheRepository declaration_cacheRepository, UsersRepository usersRepository, LoadAsosRepository loadAsosRepository) {
        this.requestRepository = requestRepository;
        this.request_statusRepository = request_statusRepository;
        this.dispatchRepository = dispatchRepository;
        this.dispatchRequestContragentViewRepository = dispatchRequestContragentViewRepository;
        this.awbRepository = awbRepository;
        this.entrepotRepository = entrepotRepository;
        this.contragentRepository = contragentRepository;
        this.businessRepository = businessRepository;
        this.countryRepository = countryRepository;
        this.serviceRepository = serviceRepository;
        this.type_exportRepository = type_exportRepository;
        this.declaration_cacheRepository = declaration_cacheRepository;
        this.usersRepository = usersRepository;
        this.loadAsosRepository = loadAsosRepository;
    }

    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(@RequestParam(value="cover_cnt_id", required = false) Long cover_cnt_id,
                              @RequestParam(value="cover_ep_id", required = false) Long cover_ep_id
    ){
        Long[] request_statusVisible={0L,1L,2L,3L}; //Закрытые = 4 не показываем
        Long[] dispatchVisible={0L,1L,2L,3L}; //Закрытые = 4 не показываем
        ModelAndView mav = new ModelAndView();

        mav.addObject("cover_cnt_id", cover_cnt_id); //сохраняем фильтр
        mav.addObject("cover_ep_id", cover_ep_id); //сохраняем фильтр

        List<Request_status> request_statusList = new ArrayList<>();
        request_statusRepository.queryByRs_idList(new ArrayList<>(Arrays.asList(request_statusVisible))).forEach(request_statusList :: add);
        mav.addObject("request_statusList", request_statusList);

        List<Entrepot> entrepotList = new ArrayList<>();
        entrepotRepository.findAll().forEach(entrepotList :: add);
        mav.addObject("entrepotList", entrepotList);

        List<DashboardRequestDTO> cardList = new ArrayList<>();
        for (DispatchRequestContragentView drcv : dispatchRequestContragentViewRepository.queryByRs_idList(new ArrayList<>(Arrays.asList(dispatchVisible)),GetUserName())) {
            if (cover_cnt_id == null || cover_cnt_id==-1) {
                if (cover_ep_id == null || cover_ep_id == -1) cardList.add(constructDashboardRequestDTO(drcv));
                else if (drcv.ep_id.longValue()==cover_ep_id.longValue()) cardList.add(constructDashboardRequestDTO(drcv));
            }
            else {
                if (drcv.cnt_id.longValue()==cover_cnt_id.longValue()) {
                    if (cover_ep_id == null || cover_ep_id == -1) cardList.add(constructDashboardRequestDTO(drcv));
                    else if (drcv.ep_id.longValue()==cover_ep_id.longValue()) cardList.add(constructDashboardRequestDTO(drcv));
                }
            }
        }
        mav.addObject("cardList", cardList);

        List<Contragent> contragentList;
        contragentList = (List<Contragent>) contragentRepository.getAllByUser(GetUserName());
        mav.addObject("contragentList", contragentList);

        List<Business> businessList;
        businessList = (List<Business>) businessRepository.findAll();
        mav.addObject("businessList", businessList);

        mav.addObject("scountry_iso2", "CN"); //CN
        mav.addObject("rcountry_iso2", "UA"); //CN
        List<Country> countryList;
        countryList = (List<Country>) countryRepository.findAll();
        mav.addObject("countryList", countryList);

        mav.addObject("type_id", 1); //Goods
        List<Type_export> typeExportList;
        typeExportList = (List<Type_export>) type_exportRepository.findAll();
        mav.addObject("typeExportList", typeExportList);

        mav.addObject("serv_id", 2); //Global Fast
        List<Service> serviceList;
        serviceList = (List<Service>) serviceRepository.findAll();
        mav.addObject("serviceList", serviceList);

        User user = usersRepository.findByUser_username(GetUserName());
        mav.addObject("user_cnt_id", user.getCnt_id());
        mav.addObject("user_ep_id", user.getEp_id());

        mav.setViewName("/dashboardrequest/cover");
        return mav;
    }

    private DashboardRequestDTO constructDashboardRequestDTO(DispatchRequestContragentView dispatchRequestContragentView) {
        DashboardRequestDTO dashboardRequestDTO = new DashboardRequestDTO();
        dashboardRequestDTO.setDis_id(dispatchRequestContragentView.getDis_id());
        dashboardRequestDTO.setDis_num(dispatchRequestContragentView.getDis_num());
        dashboardRequestDTO.setReq_id(dispatchRequestContragentView.getReq_id());
        dashboardRequestDTO.setEp_id(dispatchRequestContragentView.getEp_id());
        dashboardRequestDTO.setEp_code(dispatchRequestContragentView.getEp_code());
        dashboardRequestDTO.setAwb_id(dispatchRequestContragentView.getAwb_id());
        dashboardRequestDTO.setAwb_num(dispatchRequestContragentView.getAwb_num());
        dashboardRequestDTO.setReq_seatsnum_p(dispatchRequestContragentView.getReq_seatsnum_p());
        dashboardRequestDTO.setReq_weight_p(dispatchRequestContragentView.getReq_weight_p());
        dashboardRequestDTO.setDis_seatsnum(dispatchRequestContragentView.getDis_seatsnum());
        dashboardRequestDTO.setDis_agent_arrival_date(dispatchRequestContragentView.getDis_agent_arrival_date());
        dashboardRequestDTO.setDis_airport_arrival_date(dispatchRequestContragentView.getDis_airport_arrival_date());
        dashboardRequestDTO.setDis_transit_airport_date(dispatchRequestContragentView.getDis_transit_airport_date());
        dashboardRequestDTO.setDis_departure_airport_date_f(dispatchRequestContragentView.getDis_departure_airport_date_f());
        dashboardRequestDTO.setDis_arrival_date_kiev_f(dispatchRequestContragentView.getDis_arrival_date_kiev_f());
        dashboardRequestDTO.setDis_weight_f(dispatchRequestContragentView.getDis_weight_f());
        dashboardRequestDTO.setDis_volume_weight_f(dispatchRequestContragentView.getDis_volume_weight_f());
        dashboardRequestDTO.setDis_css_date(dispatchRequestContragentView.getDis_css_date());
        dashboardRequestDTO.setRoute_id(dispatchRequestContragentView.getRoute_id());
        dashboardRequestDTO.setDis_arrival_date_kiev_p(dispatchRequestContragentView.getDis_arrival_date_kiev_p());
        dashboardRequestDTO.setReq_num(dispatchRequestContragentView.getReq_num());
        dashboardRequestDTO.setCnt_id(dispatchRequestContragentView.getCnt_id());
        dashboardRequestDTO.setCnt_code(dispatchRequestContragentView.getCnt_code());
        dashboardRequestDTO.setCnt_name(dispatchRequestContragentView.getCnt_name());
        dashboardRequestDTO.setRs_id(dispatchRequestContragentView.getRs_id());
        dashboardRequestDTO.setBs_id(dispatchRequestContragentView.getBs_id());
        dashboardRequestDTO.setBs_name(dispatchRequestContragentView.getBs_name());
        dashboardRequestDTO.setDispatch_status(dispatchRequestContragentView.getDispatch_status());
        dashboardRequestDTO.setCountShipment(dispatchRequestContragentView.getCountshipment());
        dashboardRequestDTO.setDis_documentdate(dispatchRequestContragentView.getDis_documentdate());
        return dashboardRequestDTO;
    }

    @GetMapping("/getcard")
    public DashboardRequestDTO getcard(@RequestParam(value="dis_id") Long dis_id,
                                       @RequestParam(value="req_id") Long req_id) {
        DashboardRequestDTO result=constructDashboardRequestDTO(dispatchRequestContragentViewRepository.queryByDis_idandReq_id(dis_id,req_id,GetUserName()));
        return result;
    }

    @GetMapping("/getcardhtml")
    public ModelAndView getcardhtml(@RequestParam(value="dis_id") Long dis_id,
                                    @RequestParam(value="req_id") Long req_id) {
        ModelAndView mav = new ModelAndView();
        DashboardRequestDTO dispatch=constructDashboardRequestDTO(dispatchRequestContragentViewRepository.queryByDis_idandReq_id(dis_id,req_id,GetUserName()));
        mav.addObject("dispatch", dispatch);
        mav.setViewName("/dashboardrequest/cardview");
        return mav;
    }
    @PostMapping("/setcardstatus")
    public DashboardRequestDTO setcardstatus(@RequestParam(value="req_id") Long req_id,
                                          @RequestParam(value="dis_id") Long dis_id,
                                          @RequestParam(value="rs_id") Long rs_id
    ) {
        Request request = requestRepository.findById(req_id).orElse(null);
        Dispatch dispatch = dispatchRepository.findById(dis_id).orElse(null);
        request.rs_id=rs_id;
        dispatch.rs_id=rs_id;
        requestRepository.save(request);
        dispatchRepository.save(dispatch);
        DashboardRequestDTO result=constructDashboardRequestDTO(dispatchRequestContragentViewRepository.queryByDis_idandReq_id(dispatch.dis_id,request.req_id,GetUserName()));
        return result;
    }

    @PostMapping("/dispatchclose")
    public DashboardRequestDTO dispatchclose(@RequestParam(value="dis_id") Long dis_id
    ) {
        Long rs_id=4L; //Close
        Dispatch dispatch = dispatchRepository.findById(dis_id).orElse(null);
        Request request = requestRepository.findById(dispatch.req_id).orElse(null);
        request.rs_id=rs_id;
        dispatch.rs_id=rs_id;
        requestRepository.save(request);
        dispatchRepository.save(dispatch);
        DashboardRequestDTO result=constructDashboardRequestDTO(dispatchRequestContragentViewRepository.queryByDis_idandReq_id(dispatch.dis_id,request.req_id,GetUserName()));
        return result;
    }

    @PostMapping("/dispatchclear")
    public DashboardRequestDTO dispatchclear(@RequestParam(value="dis_id") Long dis_id
    ) {
        //удаляем посылки из депеши
        StoredProcedureQuery storageproc = entityManager
                .createStoredProcedureQuery("PKG_DISPATCH.DELETE_POST")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN) //username
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN) //dis_id
                .setParameter(1, GetUserName())
                .setParameter(2, dis_id);
        storageproc.execute();

        Dispatch dispatch = dispatchRepository.findById(dis_id).orElse(null);
        Request request = requestRepository.findById(dispatch.req_id).orElse(null);
        DashboardRequestDTO result=constructDashboardRequestDTO(dispatchRequestContragentViewRepository.queryByDis_idandReq_id(dispatch.dis_id,request.req_id,GetUserName()));
        return result;
    }

    @PostMapping("/dispatchdelete")
    public DashboardRequestDTO dispatchdelete(@RequestParam(value="dis_id") Long dis_id
    ) {
        //готовим возвращаемое DTO в котором то что мы удалим
        Dispatch dispatch = dispatchRepository.findById(dis_id).orElse(null);
        Request request = requestRepository.findById(dispatch.req_id).orElse(null);
        DashboardRequestDTO result=constructDashboardRequestDTO(dispatchRequestContragentViewRepository.queryByDis_idandReq_id(dispatch.dis_id,request.req_id,GetUserName()));

        //удаляем депешу
        StoredProcedureQuery storageproc = entityManager
                .createStoredProcedureQuery("PKG_DISPATCH.DELETE_DISPATCH")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN) //username
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN) //dis_id
                .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN) //mode
                .setParameter(1, GetUserName())
                .setParameter(2, dis_id)
                .setParameter(3, 1L); //удаляем со всеми запросами
        storageproc.execute();
        return result;
    }

    @PostMapping("/updatecard")
    public DashboardRequestDTO updatecard(@RequestParam(value="req_id", defaultValue = "-1")Long req_id,
                                        @RequestParam(value="dis_id", defaultValue = "-1") Long dis_id,
                                        @RequestParam(value="ep_id", defaultValue = "0") Long ep_id,
                                        @RequestParam(value="awb_num", defaultValue = "") String awb_num,
                                        @RequestParam(value="dis_num", defaultValue = "") String dis_num,
                                        @RequestParam(value="req_seatsnum_p") Long req_seatsnum_p,
                                        @RequestParam(value="req_weight_p") Double req_weight_p,
                                        @RequestParam(value="dis_seatsnum") Long dis_seatsnum,
                                        @RequestParam(value="dis_weight_f") Double dis_weight_f,
                                        @RequestParam(value="rs_id") Long rs_id,
                                        @RequestParam(value="cnt_id") Long cnt_id,
                                        @RequestParam(value="bs_id") Long bs_id,
                                        @RequestParam(value="dis_documentdate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dis_documentdate
    ) {
        Request request = requestRepository.findByREQ_ID( req_id).orElse(new Request());
        Dispatch dispatch = dispatchRepository.findByDis_ID(dis_id).orElse(new Dispatch());
        Entrepot entrepot = entrepotRepository.queryByEP_ID(ep_id).orElse(entrepotRepository.findById(0L).orElse(null));
        Awb awb = null;
        if (!awb_num.equalsIgnoreCase("")) {
            awb = awbRepository.findByAWB_NUM(awb_num).orElse(new Awb());
            awb.awb_num = awb_num;
            awbRepository.save(awb);
        }

        request.cnt_id=cnt_id;
        request.ep_id=entrepot.ep_id;
        request.req_seatsnum_p=req_seatsnum_p;
        request.req_weight_p=req_weight_p;
        request.rs_id=rs_id;
        request.bs_id=bs_id;
        requestRepository.save(request);

//  Запрашиваем dis_num если он null
        if (dis_num.equalsIgnoreCase("")) {
            dis_num = (String) entityManager
                    .createNativeQuery(
                            "select pkg_post.gen_dispatchforuser(:username, :ep_id) from dual"
                    )
                    .setParameter("username", GetUserName())
                    .setParameter("ep_id", ep_id)
                    .getSingleResult();
        }

        dispatch.ep_id=entrepot.ep_id;
        dispatch.req_id=request.req_id;
        dispatch.dis_num=dis_num;
        dispatch.dis_seatsnum=dis_seatsnum;
        dispatch.dis_weight_f=dis_weight_f;
        dispatch.rs_id=rs_id;
        dispatch.awb_id=(awb == null ? null : awb.awb_id);
        dispatch.dis_documentdate=dis_documentdate;
        dispatchRepository.save(dispatch);

        DashboardRequestDTO result=constructDashboardRequestDTO(dispatchRequestContragentViewRepository.queryByDis_idandReq_id(dispatch.dis_id,request.req_id,GetUserName()));
        return result;
    }

    class TUploadfileResult {
        public FileUploadResult fileUploadResult;
        public DashboardRequestDTO drdto;
    }
    @RequestMapping("/uploadXlsBootfile")
    public ResponseEntity<?> uploadfile(
            @RequestParam(name = "dis_id") Long dis_id,
            @RequestParam(name = "serv_id") Long serv_id,
            @RequestParam(name = "type_id") Long type_id,
            @RequestParam(name = "scountry_iso2") String scountry_iso2,
            @RequestParam(name = "rcountry_iso2") String rcountry_iso2,
            @RequestParam(name = "format_id") Long format_id,
            @RequestParam("file") MultipartFile file

    ) {
        ResponseEntity result = null;
        TUploadfileResult ufr = new TUploadfileResult();
        Dispatch dispatch;
        dispatch = dispatchRepository.findById(dis_id).orElse(null);
        if (dispatch!=null) {
            try {
                switch(format_id.intValue()) {
                    case 1 :
                        ufr.fileUploadResult=fileUploadService.upload(FileTypeEnum.VEX,dis_id,serv_id, type_id, scountry_iso2, rcountry_iso2,file);
                        break;
                    case 2 :
                        ufr.fileUploadResult=fileUploadService.upload(FileTypeEnum.VEX_SCAN,dis_id,null, null, null, null,file);
                        break;
                    case 3 :
                        ufr.fileUploadResult=fileUploadService.upload(FileTypeEnum.ASOS,dis_id,serv_id, type_id, scountry_iso2, rcountry_iso2,file);
                        break;
                    case 4 :
                        ufr.fileUploadResult=fileUploadService.upload(FileTypeEnum.VEX_DIG,dis_id,serv_id, type_id, scountry_iso2, rcountry_iso2,file);
                        break;
                    default :
                        throw new Exception("I do not know format_id ="+format_id);
                }

                ufr.drdto=constructDashboardRequestDTO(dispatchRequestContragentViewRepository.queryByDis_idandReq_id(dispatch.dis_id, dispatch.req_id,GetUserName()));
                result = ResponseEntity.ok(ufr);
            } catch (IOException e) {
//                e.printStackTrace();
                result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (DispatchIdNullException e) {
//                e.printStackTrace();
                result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
//                e.printStackTrace();
                result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return result;
    }

    @PostMapping("/scanclear")
    public DashboardRequestDTO scanclear(@RequestParam(value="dis_id") Long dis_id
    ) {
        //удаляем сканирование из депеши
        StoredProcedureQuery storageproc = entityManager
                .createStoredProcedureQuery("PKG_DISPATCH.DELETE_SCAN")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN) //username
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN) //dis_id
                .setParameter(1, GetUserName())
                .setParameter(2, dis_id);
        storageproc.execute();

        Dispatch dispatch = dispatchRepository.findById(dis_id).orElse(null);
        Request request = requestRepository.findById(dispatch.req_id).orElse(null);
        DashboardRequestDTO result=constructDashboardRequestDTO(dispatchRequestContragentViewRepository.queryByDis_idandReq_id(dispatch.dis_id,request.req_id,GetUserName()));
        return result;
    }
}
