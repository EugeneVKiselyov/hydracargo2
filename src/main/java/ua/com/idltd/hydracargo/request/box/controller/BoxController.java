package ua.com.idltd.hydracargo.request.box.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.request.box.repository.BoxRepository;
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

    private final BoxRepository boxRepository;

    public BoxController(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }


    @PostMapping("/gettable")
    public JSONDatatable gettable(@RequestParam(name = "req_id", required = false) Long req_id) {
        JSONDatatable result = new JSONDatatable();
        if (req_id!=null) result.setData(boxRepository.findByREQ_ID(req_id));
        return result;
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
            @RequestParam(name = "box_packing_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date box_packing_date,
            @RequestParam(name = "box_cost", required = false) Double box_cost
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
                    .registerStoredProcedureParameter(11, Long.class, ParameterMode.OUT)
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
                    ;
            AddProductQuery.execute();
            result = ResponseEntity.ok(AddProductQuery.getOutputParameterValue(11));
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
            @RequestParam(name = "box_packing_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date box_packing_date,
            @RequestParam(name = "box_cost", required = false) Double box_cost
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
