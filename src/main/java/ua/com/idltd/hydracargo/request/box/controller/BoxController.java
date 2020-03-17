package ua.com.idltd.hydracargo.request.box.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.idltd.hydracargo.contragent.contragentdefault.entity.ContragentDefault;
import ua.com.idltd.hydracargo.contragent.contragentdefault.repository.ContragentDefaultRepository;
import ua.com.idltd.hydracargo.request.box.entity.Box;
import ua.com.idltd.hydracargo.request.box.repository.VBoxRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import java.util.Date;

import static ua.com.idltd.hydracargo.utils.StaticUtils.ConvertTraceExceptionToText;
import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;


@RestController
@RequestMapping("/box")
public class BoxController {

    @PersistenceContext
    private EntityManager entityManager;

    private final VBoxRepository boxRepository;
    private final ContragentDefaultRepository contragentDefaultRepository;

    public BoxController(VBoxRepository boxRepository, ContragentDefaultRepository contragentDefaultRepository) {
        this.boxRepository = boxRepository;
        this.contragentDefaultRepository = contragentDefaultRepository;
    }


    @PostMapping("/gettable")
    public JSONDatatable gettable(@RequestParam(name = "req_id", required = false) Long req_id) {
        JSONDatatable result = new JSONDatatable();
        if (req_id!=null) result.setData(boxRepository.findByREQ_ID(req_id));
        return result;
    }
    @PostMapping("/get_box_default")
    public ResponseEntity<?> get_box_default(
            @RequestParam(name = "req_id") Long req_id
    ) {
        Box box = new Box();
        box.req_id = req_id;
        box.fit_id = 0L;
        box.box_num = (String) entityManager
                .createNativeQuery(
                        "SELECT pkg_box.getNext_Box_num(:vREQ_ID) FROM DUAL"
                )
                .setParameter("vREQ_ID", req_id)
                .getSingleResult();
        ContragentDefault contragentDefault = contragentDefaultRepository.getDefaultByUsername(GetUserName());
        if (contragentDefault != null) {
            box.box_lenght_p = contragentDefault.cntd_box_lenght;
            box.box_width_p = contragentDefault.cntd_box_width;
            box.box_height_p = contragentDefault.cntd_box_height;
            box.fpg_id = contragentDefault.cntd_fpg_id;
            box.ftpm_id = contragentDefault.cntd_ftpm_id;
            box.fit_id = contragentDefault.cntd_fit_id;
        }
        return ResponseEntity.ok(box);
    }
    @PostMapping("/add_box")
    public ResponseEntity<?> add_box(
            @RequestParam(name = "req_id", required = false) Long req_id,
            @RequestParam(name = "dis_id", required = false) Long dis_id,
            @RequestParam(name = "box_num") String box_num,
            @RequestParam(name = "box_weight_p") Double box_weight_p,
            @RequestParam(name = "box_lenght_p") Double box_lenght_p,
            @RequestParam(name = "box_width_p") Double box_width_p,
            @RequestParam(name = "box_height_p") Double box_height_p,
            @RequestParam(name = "box_volume_weight_p", required = false) Double box_volume_weight_p,
            @RequestParam(name = "box_packing_date", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date box_packing_date,
            @RequestParam(name = "box_cost", required = false) Double box_cost,
            @RequestParam(name = "box_brand", required = false) Long box_brand,
            @RequestParam(name = "box_fee", required = false) Double box_fee,
            @RequestParam(name = "fpg_id", required = false) Long fpg_id,
            @RequestParam(name = "ftpm_id", required = false) Long ftpm_id,
            @RequestParam(name = "fit_id", required = false) Long fit_id,
            @RequestParam(name = "box_description", required = false) String box_description,
            @RequestParam(name = "box_inshipment", required = false) String box_inshipment,
            @RequestParam(name = "box_shipment", required = false) String box_shipment,
            @RequestParam(name = "box_outshipment", required = false) String box_outshipment,
            @RequestParam(name = "box_carplate", required = false) String box_carplate
    ) {
        ResponseEntity<?> result;
        try{
            StoredProcedureQuery AddProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_Box.addBox")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(5, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(6, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(7, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(8, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(9, Date.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(10, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(11, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(12, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(13, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(14, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(15, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(16, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(17, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(18, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(19, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(20, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(21, Long.class, ParameterMode.OUT)
                    .setParameter(1, req_id)
                    .setParameter(2, dis_id)
                    .setParameter(3, box_num)
                    .setParameter(4, box_weight_p)
                    .setParameter(5, box_lenght_p)
                    .setParameter(6, box_width_p)
                    .setParameter(7, box_height_p)
                    .setParameter(8, box_volume_weight_p)
                    .setParameter(9, box_packing_date)
                    .setParameter(10, box_cost)
                    .setParameter(11, box_brand)
                    .setParameter(12, box_fee)
                    .setParameter(13, fpg_id)
                    .setParameter(14, ftpm_id)
                    .setParameter(15, fit_id)
                    .setParameter(16, box_description)
                    .setParameter(17, box_inshipment)
                    .setParameter(18, box_shipment)
                    .setParameter(19, box_outshipment)
                    .setParameter(20, box_carplate)
                    ;
            AddProductQuery.execute();
            result = ResponseEntity.ok(AddProductQuery.getOutputParameterValue(21));
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    @PostMapping("/edit_box")
    public ResponseEntity<?> edit_box(
            @RequestParam(name = "box_id", required = false) Long box_id,
            @RequestParam(name = "req_id", required = false) Long req_id,
            @RequestParam(name = "dis_id", required = false) Long dis_id,
            @RequestParam(name = "box_num") String box_num,
            @RequestParam(name = "box_weight_p") Double box_weight_p,
            @RequestParam(name = "box_lenght_p") Double box_lenght_p,
            @RequestParam(name = "box_width_p") Double box_width_p,
            @RequestParam(name = "box_height_p") Double box_height_p,
            @RequestParam(name = "box_volume_weight_p", required = false) Double box_volume_weight_p,
            @RequestParam(name = "box_packing_date", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date box_packing_date,
            @RequestParam(name = "box_cost", required = false) Double box_cost,
            @RequestParam(name = "box_brand", required = false) Long box_brand,
            @RequestParam(name = "box_fee", required = false) Double box_fee,
            @RequestParam(name = "fpg_id", required = false) Long fpg_id,
            @RequestParam(name = "ftpm_id", required = false) Long ftpm_id,
            @RequestParam(name = "fit_id", required = false) Long fit_id,
            @RequestParam(name = "box_description", required = false) String box_description,
            @RequestParam(name = "box_inshipment", required = false) String box_inshipment,
            @RequestParam(name = "box_shipment", required = false) String box_shipment,
            @RequestParam(name = "box_outshipment", required = false) String box_outshipment,
            @RequestParam(name = "box_carplate", required = false) String box_carplate
    ) {
        ResponseEntity<?> result;
        try{
            StoredProcedureQuery AddProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_Box.updateBox")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(5, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(6, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(7, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(8, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(9, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(10, Date.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(11, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(12, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(13, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(14, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(15, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(16, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(17, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(18, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(19, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(20, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(21, String.class, ParameterMode.IN)
                    .setParameter(1, box_id)
                    .setParameter(2, req_id)
                    .setParameter(3, dis_id)
                    .setParameter(4, box_num)
                    .setParameter(5, box_weight_p)
                    .setParameter(6, box_lenght_p)
                    .setParameter(7, box_width_p)
                    .setParameter(8, box_height_p)
                    .setParameter(9, box_volume_weight_p)
                    .setParameter(10, box_packing_date)
                    .setParameter(11, box_cost)
                    .setParameter(12, box_brand)
                    .setParameter(13, box_fee)
                    .setParameter(14, fpg_id)
                    .setParameter(15, ftpm_id)
                    .setParameter(16, fit_id)
                    .setParameter(17, box_description)
                    .setParameter(18, box_inshipment)
                    .setParameter(19, box_shipment)
                    .setParameter(20, box_outshipment)
                    .setParameter(21, box_carplate)
                    ;
            AddProductQuery.execute();
            result = ResponseEntity.ok(box_id);
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    @PostMapping("/del_box")
    public ResponseEntity<?> delete_box(
            @RequestParam(name = "box_id", required = false) Long box_id
    ) {
        ResponseEntity<?> result;
        try{
            StoredProcedureQuery AddProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_Box.deleteBox")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .setParameter(1, box_id)
                    ;
            AddProductQuery.execute();
            result = ResponseEntity.ok(box_id);
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
