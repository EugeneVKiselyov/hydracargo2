package ua.com.idltd.hydracargo.po_user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.po_user.repository.Po_UserRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import static ua.com.idltd.hydracargo.utils.StaticUtils.ConvertTraceExceptionToText;
import static ua.com.idltd.hydracargo.utils.StaticUtils.GetUserName;


@RestController
@RequestMapping("/po_user")
public class Po_UserController {

    @PersistenceContext
    private EntityManager entityManager;

    private final Po_UserRepository po_userRepository;

    public Po_UserController(EntityManager entityManager, Po_UserRepository po_userRepository) {
        this.entityManager = entityManager;
        this.po_userRepository = po_userRepository;
    }

    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(
    ){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/po_user/cover");
        return mav;
    }

    @PostMapping("/gettable")
    public JSONDatatable gettable() {
        JSONDatatable result = new JSONDatatable();

        result.setData(po_userRepository.findAll());

        return result;
    }

    @PostMapping("/edit_user")
    public ResponseEntity<?> edit_user(
            @RequestParam(name = "po_id") Long po_id,
            @RequestParam(name = "po_active") Long po_active,
            @RequestParam(name = "cnt_code") String cnt_code,
            @RequestParam(name = "cnt_code") String cnt_name
    ) {
        ResponseEntity<?> result;
        try{
            StoredProcedureQuery storageQuery = entityManager
                    .createStoredProcedureQuery("PKG_PO_USER.updateUser")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
                    .setParameter(1, po_id)
                    .setParameter(2, po_active)
                    .setParameter(3, cnt_code)
                    .setParameter(4, cnt_name)
                    ;
            storageQuery.execute();
            result = ResponseEntity.ok(po_id);
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    @PostMapping("/del_user")
    public ResponseEntity<?> del_user(
            @RequestParam(name = "po_id") Long po_id
    ) {
        ResponseEntity<?> result;
        try{
            StoredProcedureQuery storageQuery = entityManager
                    .createStoredProcedureQuery("PKG_PO_USER.delUser")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .setParameter(1, po_id)
                    ;
            storageQuery.execute();
            result = ResponseEntity.ok(po_id);
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
